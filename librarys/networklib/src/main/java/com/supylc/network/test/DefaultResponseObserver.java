package com.supylc.network.test;

import com.supylc.mobilearch.rxjava2.IRxObserveDisposer;
import com.supylc.network.support.response.BaseResponseObserver;

public abstract class DefaultResponseObserver<Result> extends BaseResponseObserver<Result, Result> {

    public DefaultResponseObserver() {
        this(null);
    }

    public DefaultResponseObserver(IRxObserveDisposer rxDisposer) {
        super(rxDisposer);
    }

    @Override
    public void onFailed(Result t) {

    }
}
