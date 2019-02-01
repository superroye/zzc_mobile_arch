package com.zzc.network.support.response;

import com.zzc.mobilearch.core.ui.ILoadingDialog;
import com.zzc.mobilearch.core.util.T;
import com.zzc.network.support.cache.CacheStrategyUtil;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Roye
 * @date 2018/9/12
 */
public abstract class PriorityCacheResponseCallback<Result> implements Callback<Result>, SupportResponseLifecycle<Result, Result> {

    private SupportProcedure procedure;
    private int requestCount;
    private Result lastCacheResult;

    public PriorityCacheResponseCallback() {
        procedure = new SupportProcedure();
        procedure.setResponseLifecycle(this);
    }

    public void setProgressDialog(ILoadingDialog progressDialog) {
        procedure.setProgressDialog(progressDialog);
    }

    public void setProgressDialog(ILoadingDialog progressDialog, String loadingText) {
        procedure.setProgressDialog(progressDialog, loadingText);
    }

    @Override
    public void onStart() {
        procedure.showLoading();
    }

    @Override
    public void onResponse(Call<Result> call, final Response<Result> response) {
        if (response.code() >= 400 || response.body() == null) {
            onFinish();
            return;
        }
        okhttp3.Response networkResopnse = response.raw().networkResponse();
        if (networkResopnse != null) {
            if (lastCacheResult == null) {
                onResponse(response.body());
                onFinish();
            } else {
                CacheStrategyUtil.checkSame(lastCacheResult, response.body(), new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if ("n".equals(s)) {
                            onResponse(response.body());
                        }
                        onFinish();
                    }
                });
            }
        } else {
            lastCacheResult = response.body();
            onResponse(response.body());
            onFinish();

            if (requestCount == 0) {
                requestCount++;
                if (T.Network.isAvailable()) {
                    call.clone().enqueue(this);
                }
            }
        }
    }

    @Override
    public void onFinish() {
        procedure.hideLoading();
    }

    @Override
    public void onFailure(Call<Result> call, Throwable t) {
        onError(t);
    }

    public void onError(Throwable e) {
        procedure.handle(e);
        onFinish();
    }

    @Override
    public void onFailed(Result result) {

    }
}