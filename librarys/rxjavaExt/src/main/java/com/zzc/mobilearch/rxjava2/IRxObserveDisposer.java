package com.zzc.mobilearch.rxjava2;

import io.reactivex.disposables.Disposable;

/**
 * Created by Roye on 2018/5/18.
 */
public interface IRxObserveDisposer {

    public void addDisposable(Disposable disposable);
}
