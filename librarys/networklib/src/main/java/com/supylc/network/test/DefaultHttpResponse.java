package com.supylc.network.test;

import com.supylc.network.support.response.IHttpResponse;

/**
 * Created by Roye on 2016/12/8.
 */

public class DefaultHttpResponse<Data> implements IHttpResponse<Data> {
    public Data data;

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public String getMsg() {
        return "";
    }

    @Override
    public Data getData() {
        return data;
    }


}