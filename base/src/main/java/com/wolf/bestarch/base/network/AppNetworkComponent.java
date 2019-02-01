package com.wolf.bestarch.base.network;

import com.zzc.mobilearch.general.component.network.ApiManager;
import com.zzc.network.internal.GlobalRequestAdapter;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author Roye
 * @date 2019/2/1
 */
public class AppNetworkComponent {

    public static void init() {
        ApiManager.builder(CoolAPi.class)
                .baseUrl("https://suggest.taobao.com/")
                .requestAdapter(new GlobalRequestAdapter() {
                    @Override
                    public void addHeader(Request.Builder builder) {

                    }

                    @Override
                    public void addQueryParams(Request originalRequest, HttpUrl.Builder httpUrlBuilder) {

                    }

                    @Override
                    public void addPostParams(Request originalRequest, Request.Builder requestBuilder) {

                    }
                }).build();

    }
}
