package com.zzc.network.support.response;

/**
 * @author Roye
 * @date 2018/9/11
 */
public interface SupportResponseLifecycle<Result, Data> {

    public void onStart();

    public void onResponse(Data result);

    public void onFailed(Result result);

    public void onFinish();

}
