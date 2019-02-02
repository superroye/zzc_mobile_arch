package com.zzc.network.test;

import com.zzc.mobilearch.rxjava2.IRxObserveDisposer;
import com.zzc.network.support.response.BaseResponseObserver;

/**
 * Created by Roye on 2016/12/8.
 */

public abstract class DefaultResponseDataObserver<Data> extends BaseResponseObserver<DefaultHttpResponse<Data>, Data> {

    public DefaultResponseDataObserver() {
        this(null);
    }

    /**
     * 非必传
     *
     * @param observeDisposer observeDisposer在fragment or activity实现，建议传此参数
     */
    public DefaultResponseDataObserver(IRxObserveDisposer observeDisposer) {
        super(observeDisposer);
    }

    @Override
    public void onFailed(DefaultHttpResponse<Data> result) {
        if (result.getMsg() != null) {
        }
    }
}
