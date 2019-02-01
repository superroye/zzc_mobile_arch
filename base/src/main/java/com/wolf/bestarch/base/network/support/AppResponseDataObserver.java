package com.wolf.bestarch.base.network.support;

import com.zzc.mobilearch.rxjava2.IRxObserveDisposer;
import com.zzc.network.support.response.BaseResponseObserver;

/**
 * @author Roye
 * @date 2019/2/1
 */
public abstract class AppResponseDataObserver<Data> extends BaseResponseObserver<AppHttpResponse<Data>, Data> {

    public AppResponseDataObserver() {
        this(null);
    }

    /**
     * 非必传
     *
     * @param observeDisposer observeDisposer在fragment or activity实现，建议传此参数
     */
    public AppResponseDataObserver(IRxObserveDisposer observeDisposer) {
        super(observeDisposer);
    }

    @Override
    public void onFailed(AppHttpResponse<Data> result) {
        if (result.getMsg() != null) {
        }
    }
}