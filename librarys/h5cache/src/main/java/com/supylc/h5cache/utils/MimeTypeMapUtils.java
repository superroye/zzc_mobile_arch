package com.supylc.h5cache.utils;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

public class MimeTypeMapUtils {

    public static final int TYPE_MEDIA = 1;
    public static final int TYPE_STATIC_TEXT = 2;
    public static final int TYPE_VARIABLE = 3;
    public static final int TYPE_VARIABLE_TEXT = 4;

    public static String getFileExtensionFromUrl(String url) {
        url = url.toLowerCase();
        if (!TextUtils.isEmpty(url)) {
            int fragment = url.lastIndexOf('#');
            if (fragment > 0) {
                url = url.substring(0, fragment);
            }

            int query = url.lastIndexOf('?');
            if (query > 0) {
                url = url.substring(0, query);
            }

            int filenamePos = url.lastIndexOf('/');
            String filename =
                    0 <= filenamePos ? url.substring(filenamePos + 1) : url;

            // if the filename contains special characters, we don't
            // consider it valid for our matching purposes:
            if (!filename.isEmpty()) {
                int dotPos = filename.lastIndexOf('.');
                if (0 <= dotPos) {
                    return filename.substring(dotPos + 1);
                }
            }
        }

        return "";
    }

    public static String getMimeTypeFromExtension(String extension) {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    public static int checkResourceType(String mimeType) {
        if (TextUtils.isEmpty(mimeType)) {
            return TYPE_VARIABLE_TEXT;
        }
        if (mimeType.startsWith("text")) {
            if (mimeType.endsWith("css")) {
                return TYPE_STATIC_TEXT;
            }
        } else if (mimeType.startsWith("video")) {
            return TYPE_MEDIA;
        } else if (mimeType.startsWith("image")) {
            if (mimeType.endsWith("gif")) {
                return TYPE_VARIABLE;
            }
            return TYPE_MEDIA;
        } else if (mimeType.startsWith("audio")) {
            return TYPE_MEDIA;
        } else if (mimeType.startsWith("application")) {
            if (mimeType.endsWith("javascript")) {
                return TYPE_STATIC_TEXT;
            }
        }
        return TYPE_VARIABLE_TEXT;
    }


    public static String getMimeTypeFromContentType(String contentType) {
        if (TextUtils.isEmpty(contentType)) {
            return "";
        }
        int index = contentType.indexOf(";");
        if (index < 0) {
            return contentType;
        } else {
            return contentType.substring(0, index);
        }
    }


}
