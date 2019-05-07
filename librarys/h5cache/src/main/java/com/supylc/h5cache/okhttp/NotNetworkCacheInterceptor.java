package com.supylc.h5cache.okhttp;

import com.supylc.h5cache.H5CacheClient;
import com.supylc.h5cache.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Roye
 * @date 2019/4/12
 */
public class NotNetworkCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        return doByNetwork(chain, request, NetworkUtils.isAvailable(H5CacheClient.getInstance().context()));
    }

    public Response doByNetwork(Interceptor.Chain chain, Request request, boolean remote) throws IOException {
        Response response;
        if (!remote) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            response = chain.proceed(request);
        } else {
            response = chain.proceed(request);
            if (!response.isSuccessful()) {
                return doByNetwork(chain, request, false);
            }
        }
        return response;
    }
}
