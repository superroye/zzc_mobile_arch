package com.supylc.h5cache.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Roye
 * @date 2019/4/3
 */
public class HeadersUtils {

    public static final String RESP_CACHE_CONTROL = "Cache-Control";
    public static final String RESP_AGE = "Age";
    public static final String RESP_ETAG = "ETag";
    public static final String RESP_EXPIRES = "Expires";
    public static final String RESP_LAST_MODIFIED = "Last-Modified";
    public static final String RESP_DATE = "Date";

    public static final String REQ_IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String REQ_IF_NONE_MATCH = "If-None-Match";

    public static Map<String, String> getRequestHeaders(Map<String, String> browsers, Map<String, String> cacheResponse) {
        Map<String, String> headers = new HashMap<>(browsers);
        if (cacheResponse != null) {
            for (Map.Entry<String, String> entry : cacheResponse.entrySet()) {
                switch (entry.getKey()) {
                    case RESP_CACHE_CONTROL:
                    case RESP_AGE:
                    case RESP_EXPIRES:
                    case RESP_DATE:
                        headers.put(entry.getKey(), entry.getValue());
                        break;
                    case RESP_ETAG:
                        headers.put(REQ_IF_NONE_MATCH, entry.getValue());
                        break;
                    case RESP_LAST_MODIFIED:
                        headers.put(REQ_IF_MODIFIED_SINCE, entry.getValue());
                        break;
                    default:
                        break;
                }
            }
        }

        return headers;
    }

    public static Map<String, String> getSimpleHeaders(Map<String, List<String>> oriHeaders) {
        if (oriHeaders == null) {
            return null;
        }
        Map<String, String> headers = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : oriHeaders.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && entry.getValue() != null & !entry.getValue().isEmpty()) {
                headers.put(entry.getKey(), entry.getValue().get(entry.getValue().size() - 1));
            }
        }
        return headers;
    }

    public static String getMethod(Map<String, String> requestHeaders) {
        String mimeType = getRequestMimeType(requestHeaders);
        if (String.valueOf(mimeType).endsWith("x-www-form-urlencoded")) {
            return "POST";
        } else {
            return "GET";
        }
    }

    public static String getRequestMimeType(Map<String, String> requestHeaders) {
        String contentType = requestHeaders.get("Content-Type");
        String mimeType = contentType;
        int split = String.valueOf(contentType).indexOf(";");
        if (split > -1) {
            mimeType = contentType.substring(0, split);
        }
        return String.valueOf(mimeType);
    }

    public static String getResponseMimeType(Map<String, String> requestHeaders) {
        String contentType = requestHeaders.get("Content-Type");
        if (contentType == null) {
            contentType = requestHeaders.get("content-type");
        }
        String mimeType = contentType;
        int split = String.valueOf(contentType).indexOf(";");
        if (split > -1) {
            mimeType = contentType.substring(0, split);
        }
        return String.valueOf(mimeType);
    }
}
