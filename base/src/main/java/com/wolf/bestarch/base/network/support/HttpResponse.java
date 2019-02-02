package com.wolf.bestarch.base.network.support;

import com.zzc.network.support.response.IHttpResponse;

/**
 * @author Roye
 * @date 2019/2/1
 */
public class HttpResponse<Data> implements IHttpResponse<Data> {

    public Data result;

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
        return result;
    }
}
