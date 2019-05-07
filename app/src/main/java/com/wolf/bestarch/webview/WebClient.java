package com.wolf.bestarch.webview;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Locale;

/**
 * @author Roye
 * @date 2019/3/28
 */
public class WebClient extends WebViewClient {

    private WebViewCacheSupport webViewCacheSupport;

    public void setWebViewCacheSupport(WebViewCacheSupport webViewCacheSupport) {
        this.webViewCacheSupport = webViewCacheSupport;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

        StringBuilder sb = new StringBuilder(request.getUrl().toString());
        if (request.getUrl().toString().startsWith("http")) {
            try {

                CacheResourceResponse cacheResourceResponse = webViewCacheSupport.readHttpUrl(view, request.getUrl().toString());
                sb.append(" ").append(cacheResourceResponse.getMimeType());
                android.util.Log.d("cor", "==================1 " + sb.toString());

                return new WebResourceResponse(cacheResourceResponse.getMimeType(), cacheResourceResponse.getEncoding(), cacheResourceResponse.getData());
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            android.util.Log.d("cor", "==================2 " + sb.toString());
            return null;
        }
        android.util.Log.d("cor", "==================3 " + sb.toString());
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return super.shouldOverrideKeyEvent(view, event);
    }


    private String getMimeTypeFromPath(String path) {
        String extension = path;
        int lastDot = extension.lastIndexOf('.');
        if (lastDot != -1) {
            extension = extension.substring(lastDot + 1);
        }
        // Convert the URI string to lower case to ensure compatibility with MimeTypeMap (see CB-2185).
        extension = extension.toLowerCase(Locale.getDefault());
        if ("3ga".equals(extension)) {
            return "audio/3gpp";
        } else if ("js".equals(extension)) {
            // Missing from the map :(.
            return "text/javascript";
        }
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}
