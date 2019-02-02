package com.wolf.bestarch.base.network.support;

import com.zzc.mobilearch.rxjava2.IRxObserveDisposer;
import com.zzc.network.support.response.BaseResponseObserver;

/**
 * @author Roye
 * @date 2019/2/1
 */
public abstract class ResponseDataObserver<Data> extends BaseResponseObserver<HttpResponse<Data>, Data> {

    public ResponseDataObserver() {
        this(null);
    }

    /**
     * 非必传
     *
     * @param observeDisposer observeDisposer在fragment or activity实现，建议传此参数
     */
    public ResponseDataObserver(IRxObserveDisposer observeDisposer) {
        super(observeDisposer);
    }

    @Override
    public void onFailed(HttpResponse<Data> result) {
        if (result.getMsg() != null) {
        }
    }
}