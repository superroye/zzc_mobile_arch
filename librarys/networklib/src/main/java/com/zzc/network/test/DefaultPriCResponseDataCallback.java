package com.zzc.network.test;

import com.zzc.network.support.response.PriorityCacheResponseDataCallback;

/**
 * @author Roye
 * @date 2018/9/28
 */
public abstract class DefaultPriCResponseDataCallback<Data> extends PriorityCacheResponseDataCallback<DefaultHttpResponse<Data>, Data> {

    @Override
    public void onFailed(DefaultHttpResponse<Data> result) {
        super.onFailed(result);
    }
}
