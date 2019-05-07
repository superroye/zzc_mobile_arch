package com.supylc.h5cache.okhttp;

import com.supylc.h5cache.CacheFilter;
import com.supylc.h5cache.H5CacheClient;
import com.supylc.h5cache.utils.MimeTypeMapUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * @author Roye
 * @date 2019/4/12
 */
public class UrlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String h5OriginalUrl = request.tag().toString();
        CacheFilter cacheFilter = H5CacheClient.getInstance().filter();
        if (!cacheFilter.isNeedCache(h5OriginalUrl)) {
            return new Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .code(504)
                    .message("Unsatisfiable Request (only-if-cached)")
                    .body(Util.EMPTY_RESPONSE)
                    .sentRequestAtMillis(-1L)
                    .receivedResponseAtMillis(System.currentTimeMillis())
                    .build();
        }

        String url = request.url().toString();
        String extension = MimeTypeMapUtils.getFileExtensionFromUrl(url);

        if (!url.equals(h5OriginalUrl)) {
            if (cacheFilter.isFilterExtension(extension)) {
                return new Response.Builder()
                        .request(chain.request())
                        .protocol(Protocol.HTTP_1_1)
                        .code(504)
                        .message("Unsatisfiable Request (only-if-cached)")
                        .body(Util.EMPTY_RESPONSE)
                        .sentRequestAtMillis(-1L)
                        .receivedResponseAtMillis(System.currentTimeMillis())
                        .build();
            }
        }

        return chain.proceed(request);
    }
}
