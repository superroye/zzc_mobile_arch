package com.zzc.network.support.response;

import com.zzc.mobilearch.rxjava2.IRxObserveDisposer;

public abstract class ResponseObserver<Result> extends BaseResponseObserver<Result, Result> {

    public ResponseObserver() {
        this(null);
    }

    public ResponseObserver(IRxObserveDisposer rxDisposer) {
        super(rxDisposer);
    }

    @Override
    public void onFailed(Result t) {

    }
}
