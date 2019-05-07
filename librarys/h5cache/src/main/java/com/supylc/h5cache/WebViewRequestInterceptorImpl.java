package com.supylc.h5cache;

import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import java.util.Map;

/**
 * @author Roye
 * @date 2019/4/12
 */
public class WebViewRequestInterceptorImpl implements WebViewRequestInterceptor {


    private WebViewCacheSupport webViewCacheSupport;

    public WebViewRequestInterceptorImpl(WebView webView){
        webViewCacheSupport = new WebViewCacheSupport(webView);
    }

    @Override
    public WebResourceResponse interceptRequest(WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return webViewCacheSupport.getResource(request.getUrl().toString(), request.getMethod(), request.getRequestHeaders());
        }
        return null;
    }

    @Override
    public WebResourceResponse interceptRequest(String url) {
        return null;
    }

    @Override
    public void loadUrl(WebView webView, String url) {

    }

    @Override
    public void loadUrl(String url, String userAgent) {

    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders, String userAgent) {

    }

    @Override
    public void loadUrl(WebView webView, String url, Map<String, String> additionalHttpHeaders) {

    }

    @Override
    public void setOriginalUrl(String originalUrl) {
        webViewCacheSupport.setOriginalUrl(originalUrl);
    }

    @Override
    public boolean needCache(String url) {
        return webViewCacheSupport.needCache(url);
    }
}
