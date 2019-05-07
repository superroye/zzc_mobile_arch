package com.wolf.bestarch;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.supylc.h5cache.CacheFilter;
import com.supylc.mobilearch.uilibs.activity.IntelligentActivityImpl;
import com.wolf.bestarch.webview.WebClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Roye
 * @date 2019/3/28
 */
public class WebActivity extends IntelligentActivityImpl {

    @Override
    public int layoutId() {
        return R.layout.activity_web;
    }

    WebView mWebView;

    String url = "https://www.baidu.com";

    Button submit;
    EditText input;

    @Override
    public void initView() {
        mWebView = findViewById(R.id.webview);
        submit = findViewById(R.id.submit);
        input = findViewById(R.id.input);

        input.setText("http://m.zuzuche.net/");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(input.getText().toString());
            }
        });


        initWebView();

        mWebView.loadUrl(url);
    }

    private void initWebView() {
        WebViewCacheClient.builder().cacheFilter(new CacheFilter() {
            @Override
            public int getURLCacheMode(String url) {
                return CacheFilter.CACHE_WEBVIEW_DEFAULT;
            }
        }).userAgent(getUserAgentForWebView(this)).start();

        WebClient webClient = new WebClient();

        webClient.setWebViewCacheSupport(WebViewCacheClient.getCacheSupport(mWebView, url));
        mWebView.setWebViewClient(webClient);

        WebView.setWebContentsDebuggingEnabled(true);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDefaultTextEncodingName("UTF-8");

        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getCacheDir().getPath() + "/appcache");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

       // webSettings.setUserAgentString(getUserAgentForWebView(this));
    }

    public static String getUserAgentForWebView(Context context) {
        String defaultUserAgent;
        try {
            // Application package com.android.webview not found com.zuzuChe.network.i.IM(NetConfig.java)
            defaultUserAgent = WebSettings.getDefaultUserAgent(context);
        } catch (Exception e) {
            defaultUserAgent = null;
        }
        if (TextUtils.isEmpty(defaultUserAgent)) {
            StringBuilder builder = new StringBuilder();
            defaultUserAgent = builder
                    .append("Mozilla/5.0 (Linux; Android ")
                    .append(Build.VERSION.RELEASE)
                    .append("; ")
                    .append(Build.MODEL)
                    .append(" Build/")
                    .append(Build.ID)
                    .append("; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Mobile Safari/537.36")
                    .toString();
        }
        return genZZCUserAgent(defaultUserAgent);
    }

    private static String genZZCUserAgent(String defaultUserAgent) {
        if (defaultUserAgent.startsWith("ZZCAndroid")) {
            return defaultUserAgent;
        }
        String versionName = "5.2";
        Pattern pattern = Pattern.compile("^\\d+\\.\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(versionName);
        if (matcher.find()) {
            versionName = matcher.group();
        }
        StringBuilder ua = new StringBuilder("ZZCAndroid/")
                .append(versionName)
                .append("/")
                .append("app")
                .append(" ")
                .append(defaultUserAgent)
                .append(" MUID/")
                .append("ssfff");
        return ua.toString();

    }

    @Override
    public void initData() {

    }
}
