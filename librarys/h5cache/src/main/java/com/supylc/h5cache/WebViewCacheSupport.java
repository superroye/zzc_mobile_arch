package com.supylc.h5cache;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.supylc.h5cache.post.IOUtils;
import com.supylc.h5cache.post.PostInterceptJSIntf;
import com.supylc.h5cache.utils.H5CacheLog;
import com.supylc.h5cache.utils.HeadersUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Roye
 * @date 2019/4/10
 */
public class WebViewCacheSupport {

    private Context mContext;
    private CacheFilter mCacheFilter;
    private String mOriginalUrl;

    private PostInterceptJSIntf mJSSubmitIntercept;
    private OkHttpClient okHttpClient;

    public WebViewCacheSupport(WebView webView) {
        mContext = H5CacheClient.getInstance().context();
        mCacheFilter = H5CacheClient.getInstance().filter();

        okHttpClient = H5CacheClient.getInstance().client();

        mJSSubmitIntercept = new PostInterceptJSIntf();
        webView.addJavascriptInterface(mJSSubmitIntercept, "interception");
    }

    public WebResourceResponse getResource(String url, String method, Map<String, String> requestHeaders) {
        if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
            return null;
        }

        Request request;
        Request.Builder requestBuilder = new Request.Builder().tag(mOriginalUrl).url(url);

        addHeader(requestBuilder, requestHeaders);
        H5CacheLog.d("h5", "2 getResource=====" + url + " " + method);
        if ("POST".equals(method)) {
            mJSSubmitIntercept.setPost(requestBuilder);

            resetRequestContents();
            request = requestBuilder.build();
        } else {
            request = requestBuilder.get().build();
        }

        try {
            Response response = okHttpClient.newCall(request).execute();
            Response cacheRes = response.cacheResponse();

            Map<String, String> responseHeaders = HeadersUtils.getSimpleHeaders(response.headers().toMultimap());
            String mimeType = HeadersUtils.getResponseMimeType(responseHeaders);

            if (cacheRes != null) {
                H5CacheLog.d(String.format("=======from cache: %s", url) + " " + mimeType + " " + response.code());
            } else {
                H5CacheLog.d(String.format("=======from server: %s", url) + " " + mimeType + " " + response);
            }

            WebResourceResponse webResourceResponse = new WebResourceResponse(mimeType, "UTF-8", response.body().byteStream());
            if (response.code() == 504 || response.code() == 302) {
                return null;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String message = response.message();
                if (TextUtils.isEmpty(message)) {
                    message = "OK";
                }
                try {
                    webResourceResponse.setStatusCodeAndReasonPhrase(response.code(), message);
                } catch (Exception e) {
                    return null;
                }

                webResourceResponse.setResponseHeaders(responseHeaders);
            }
            addPostInterceptJs(url, webResourceResponse);
            return webResourceResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void addHeader(Request.Builder reqBuilder, Map<String, String> headers) {

        if (headers == null) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            reqBuilder.addHeader(entry.getKey(), entry.getValue());
        }
    }

    void addPostInterceptJs(String url, WebResourceResponse webResourceResponse) {
        if (!url.equals(mOriginalUrl)) {
            return;
        }
        byte[] pageContents;
        try {
            String mime = webResourceResponse.getMimeType();
            if (mime.equals("text/html")) {
                pageContents = IOUtils.readFully(webResourceResponse.getData());
                pageContents = PostInterceptJSIntf
                        .enableIntercept(mContext, pageContents)
                        .getBytes("UTF-8");

                InputStream isContents = new ByteArrayInputStream(pageContents);
                webResourceResponse.setData(isContents);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean needCache(String url) {
        return mCacheFilter.isNeedCache(url);
    }

    private void resetRequestContents() {
        mJSSubmitIntercept.resetRequestContents();
    }

    public void setOriginalUrl(String url) {
        mOriginalUrl = url;
    }
}
