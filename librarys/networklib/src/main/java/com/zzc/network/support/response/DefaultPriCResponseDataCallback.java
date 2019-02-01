package com.zzc.network.support.response;

/**
 * @author Roye
 * @date 2018/9/28
 */
public abstract class DefaultPriCResponseDataCallback<Data> extends PriorityCacheResponseDataCallback<HttpResponse<Data>, Data> {

    @Override
    public void onFailed(HttpResponse<Data> result) {
        super.onFailed(result);
    }
}
