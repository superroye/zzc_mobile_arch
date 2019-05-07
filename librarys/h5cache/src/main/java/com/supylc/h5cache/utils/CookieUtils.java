package com.supylc.h5cache.utils;

import android.os.Build;
import android.webkit.CookieSyncManager;

import com.supylc.h5cache.H5CacheClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * @author Roye
 * @date 2019/4/10
 */
public class CookieUtils {

    public static String getCookies(String url) {
        try {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(H5CacheClient.getInstance().context());
            cookieSyncManager.startSync();
            android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
            return cookieManager.getCookie(url);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Cookie> getCookieList(String url) {
        String cookieStr = getCookies(url);
        HttpUrl httpUrl = HttpUrl.parse(url);
        List<Cookie> cookies = new LinkedList<>();
        if (cookieStr != null && cookieStr.length() > 1) {
            int index;
            int fromIndex = 0;
            String cookie;
            Cookie cookieItem;
            while ((index = cookieStr.indexOf(";", fromIndex + 1)) > -1) {
                cookie = cookieStr.substring(fromIndex, index).trim();
                fromIndex = index + 1;
                cookieItem = Cookie.parse(httpUrl, cookie);
                if (cookieItem != null) {
                    cookies.add(cookieItem);
                }
            }
        }
        return cookies;
    }

    public static void setCookies(String url, Map<String, List<String>> responseHeaders) {
        List<Cookie> cookies = new ArrayList<>();
        List<String> list = responseHeaders.get("Set-Cookie");
        if (list != null) {
            Cookie cookie;
            for (int i = 0; i < list.size(); i++) {
                cookie = Cookie.parse(HttpUrl.parse(url), list.get(i));
                if (cookie != null) {
                    cookies.add(cookie);
                }
            }
        }
        syncCookies(url, cookies);
    }

    public static void syncCookies(String url, List<Cookie> cookies) {
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(H5CacheClient.getInstance().context());
        cookieSyncManager.startSync();
        android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String key = cookie.name();
            String value = cookie.value();
            cookieManager.setCookie(url, key + "=" + value);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            cookieSyncManager.sync();
        } else {
            cookieManager.flush();
        }
    }
}
