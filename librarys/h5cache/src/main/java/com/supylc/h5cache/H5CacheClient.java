package com.supylc.h5cache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebView;

import com.supylc.h5cache.okhttp.NetworkCacheInterceptor;
import com.supylc.h5cache.okhttp.NotNetworkCacheInterceptor;
import com.supylc.h5cache.okhttp.UrlInterceptor;
import com.supylc.h5cache.utils.CookieUtils;
import com.supylc.h5cache.utils.H5CacheLog;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.PostCacheInterceptor;

/**
 * @author Roye
 * @date 2019/4/11
 */
public class H5CacheClient {

    private CacheFilter filter;
    private static H5CacheClient mInstance;
    private Context mContext;
    private OkHttpClient mOkClient;
    private String cacheDir;

    private H5CacheClient() {
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static H5CacheClient getInstance() {
        return mInstance;
    }

    public static void setDebug(boolean isDebug) {
        H5CacheLog.setDebug(isDebug);
    }

    public static WebViewRequestInterceptor getWebViewInterceptor(WebView webView) {
        return new WebViewRequestInterceptorImpl(webView);
    }

    public OkHttpClient client() {
        return getInstance().mOkClient;
    }


    public CacheFilter filter() {
        return filter;
    }

    public Context context() {
        return mContext;
    }

    public String cacheDir() {
        return cacheDir;
    }

    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }

    private static class Builder {

        public Builder(Context context) {
            this.context = context;
        }

        private Context context;
        private int cacheSize;
        private String cacheDir;
        private CookieJar cookieJar;
        private int connectTimeoutSecond;
        private int readTimeoutSecond;
        private CacheFilter filter;

        public Builder cacheDir(String cacheDir) {
            this.cacheDir = cacheDir;
            return this;
        }

        public Builder cacheSize(int cacheSize) {
            this.cacheSize = cacheSize;
            return this;
        }

        public Builder cookieJar(CookieJar cookieJar) {
            this.cookieJar = cookieJar;
            return this;
        }

        public Builder connectTimeoutSecond(int connectTimeoutSecond) {
            this.connectTimeoutSecond = connectTimeoutSecond;
            return this;
        }

        public Builder readTimeoutSecond(int readTimeoutSecond) {
            this.readTimeoutSecond = readTimeoutSecond;
            return this;
        }

        public Builder filter(CacheFilter filter) {
            this.filter = filter;
            return this;
        }

        public H5CacheClient build() {
            H5CacheClient h5CacheClient = new H5CacheClient();

            h5CacheClient.mContext = context;
            h5CacheClient.filter = filter;
            h5CacheClient.cacheDir = cacheDir;
            h5CacheClient.mOkClient = buildOkhttp();

            mInstance = h5CacheClient;
            return h5CacheClient;
        }

        private OkHttpClient buildOkhttp() {
            Cache cache = new Cache(new File(cacheDir, "okhttp_h5"), cacheSize);
            okhttp3.PostCache postCache = new okhttp3.PostCache(new File(cacheDir, "okhttp_post_h5"), cacheSize / 2);

            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                    .cache(cache)
                    .sslSocketFactory(createSSLSocketFactory(), new TrustAllManager())
                    .hostnameVerifier(new TrustAllHostnameVerifier())
                    .connectTimeout(connectTimeoutSecond, TimeUnit.SECONDS).readTimeout(readTimeoutSecond, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false);

            okBuilder.cookieJar(cookieJar != null ? cookieJar : new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    CookieUtils.syncCookies(url.toString(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    return CookieUtils.getCookieList(url.toString());
                }
            });


            okBuilder.addInterceptor(new UrlInterceptor());

            okBuilder.addInterceptor(new NotNetworkCacheInterceptor());

            okBuilder.addInterceptor(new PostCacheInterceptor(postCache.internalCache));

            okBuilder.addNetworkInterceptor(new NetworkCacheInterceptor());

            return okBuilder.build();
        }
    }
}
