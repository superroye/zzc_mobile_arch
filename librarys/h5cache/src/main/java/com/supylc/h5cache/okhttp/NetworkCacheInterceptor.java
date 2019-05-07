package com.supylc.h5cache.okhttp;

import com.supylc.h5cache.utils.H5CacheLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Roye
 * @date 2019/4/12
 */
public class NetworkCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        H5CacheLog.d("h5", "url connect start=====" + request.url().toString());

        Response response = chain.proceed(request);

        if ("GET".equals(request.method())) {

            if (response.cacheControl().noStore() || response.cacheControl().noCache()) {
                Response.Builder responseBuilder = response.newBuilder()
                        .removeHeader("Cache-Control")
                        .removeHeader("Pragma");
                responseBuilder.header("Cache-Control", "max-age=10");
                response = responseBuilder.build();
            }
        } else if ("POST".equals(request.method())) {
            if (response.cacheControl().noStore() || response.cacheControl().noCache()) {
                Response.Builder responseBuilder = response.newBuilder()
                        .removeHeader("Cache-Control")
                        .removeHeader("Pragma");
                responseBuilder.header("Cache-Control", "max-age=10");
                response = responseBuilder.build();
            }
        }

        H5CacheLog.d("h5", "url connect end =====" + request.url().toString() + " " + request.method() + " " + response.code());
        return response;
    }
}
