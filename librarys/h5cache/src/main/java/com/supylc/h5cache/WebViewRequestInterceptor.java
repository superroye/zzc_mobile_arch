package com.supylc.h5cache;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import java.util.Map;

/**
 * Created by yale on 2018/7/13.
 */
public interface WebViewRequestInterceptor {

    WebResourceResponse interceptRequest(WebResourceRequest request);

    WebResourceResponse interceptRequest(String url);

    void loadUrl(WebView webView, String url);

    void loadUrl(String url, String userAgent);

    void loadUrl(String url, Map<String, String> additionalHttpHeaders, String userAgent);

    void loadUrl(WebView webView, String url, Map<String, String> additionalHttpHeaders);

    void setOriginalUrl(String originalUrl);

    boolean needCache(String url);
}
