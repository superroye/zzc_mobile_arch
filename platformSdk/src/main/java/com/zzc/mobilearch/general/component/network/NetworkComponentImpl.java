package com.zzc.mobilearch.general.component.network;

import com.zzc.network.ApiBuilder;
import com.zzc.network.internal.GlobalRequestAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CookieJar;

/**
 * @author Roye
 * @date 2018/11/13
 */
class NetworkComponentImpl implements NetworkComponent {

    private List apis;

    public NetworkComponentImpl() {
        apis = new ArrayList();
    }

    @Override
    public <T> INetworkBuilder builder(final Class<T> apiClass) {
        INetworkBuilder builder = new INetworkBuilder() {
            ApiBuilder<T> apiBuilder = new ApiBuilder();

            @Override
            public NetworkComponent build() {
                T api = null;
                for (int i = 0; i < apis.size(); i++) {
                    if (apiClass == apis.get(i).getClass()) {
                        api = (T) apis.get(i);
                        break;
                    }
                }
                if (api == null) {
                    apiBuilder.forType(apiClass);
                    api = apiBuilder.build();
                    NetworkComponentImpl.this.apis.add(api);
                }

                return NetworkComponentImpl.this;
            }

            @Override
            public INetworkBuilder baseUrl(String baseUrl) {
                apiBuilder.baseUrl(baseUrl);
                return this;
            }

            @Override
            public INetworkBuilder requestAdapter(GlobalRequestAdapter globalRequestAdapter) {
                apiBuilder.setGlobalRequestAdapter(globalRequestAdapter);
                return this;
            }

            @Override
            public INetworkBuilder connectTimeout(long timeout, TimeUnit unit) {
                apiBuilder.getOkHttpBuilder().connectTimeout(timeout, unit);
                return this;
            }

            @Override
            public INetworkBuilder readTimeout(long timeout, TimeUnit unit) {
                apiBuilder.getOkHttpBuilder().readTimeout(timeout, unit);
                return this;
            }

            @Override
            public INetworkBuilder cookieJar(CookieJar cookieJar) {
                apiBuilder.getOkHttpBuilder().cookieJar(cookieJar);
                return this;
            }

            @Override
            public INetworkBuilder cache(Cache cache) {
                apiBuilder.getOkHttpBuilder().cache(cache);
                return this;
            }
        };
        return builder;
    }

    @Override
    public <T> T api(Class<T> apiClass) {
        for (int i = 0; i < apis.size(); i++) {
            if (apiClass.isAssignableFrom(apis.get(i).getClass())) {
                return (T) apis.get(i);
            }
        }
        throw new RuntimeException("this api is not inited");
    }
}
