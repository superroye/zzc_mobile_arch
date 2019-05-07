package com.supylc.network.test;

import com.supylc.network.ApiManager;
import com.supylc.network.internal.CommonParamsAdapter;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author Roye
 * @date 2019/2/2
 */
public class MainTest {

    public static void init() {
        ApiManager.builder(CoolAPi.class)
                .baseUrl("https://suggest.taobao.com/")
                .requestAdapter(new CommonParamsAdapter() {
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

    public static void testGet() {
        ApiManager.api(CoolAPi.class).testSearchNetwork("高级").subscribe(new DefaultResponseDataObserver<TaobaoTest>() {
            @Override
            public void onResponse(TaobaoTest result) {

            }
        });
    }
}
