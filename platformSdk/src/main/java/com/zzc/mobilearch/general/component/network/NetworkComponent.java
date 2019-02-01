package com.zzc.mobilearch.general.component.network;

import com.zzc.network.internal.GlobalRequestAdapter;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CookieJar;

/**
 * @author Roye
 * @date 2018/11/12
 */
interface NetworkComponent {

    public <API> INetworkBuilder builder(Class<API> apiClass);

    public <API> API api(Class<API> apiClass);

    public interface INetworkBuilder {

        public NetworkComponent build();

        public INetworkBuilder baseUrl(String baseUrl);

        public INetworkBuilder requestAdapter(GlobalRequestAdapter globalRequestAdapter);

        public INetworkBuilder connectTimeout(long timeout, TimeUnit unit);

        public INetworkBuilder readTimeout(long timeout, TimeUnit unit);

        public INetworkBuilder cookieJar(CookieJar cookieJar);

        public INetworkBuilder cache(Cache cache);
    }
}
