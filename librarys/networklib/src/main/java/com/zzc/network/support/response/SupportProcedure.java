package com.zzc.network.support.response;

import android.text.TextUtils;
import android.util.Log;

import com.zzc.mobilearch.core.ui.ILoadingDialog;
import com.zzc.mobilearch.core.util.T;
import com.zzc.mobilearch.rxjava2.RxSimple;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.HttpException;

/**
 * @author Roye
 * @date 2018/9/11
 */
public class SupportProcedure<Result extends IHttpResponse, Data> {

    WeakReference<ILoadingDialog> mProgressDialog;
    private String loadingText;
    private SupportResponseLifecycle<Result, Data> responseLifecycle;

    public SupportProcedure() {

    }

    public void setResponseLifecycle(SupportResponseLifecycle responseLifecycle) {
        this.responseLifecycle = responseLifecycle;
    }

    public void setProgressDialog(ILoadingDialog progressDialog) {
        setProgressDialog(progressDialog, "请求中...");
    }

    public void setProgressDialog(ILoadingDialog progressDialog, String loadingText) {
        if (progressDialog != null) {
            this.mProgressDialog = new WeakReference<>(progressDialog);
            this.loadingText = loadingText;
        }
    }

    public void handle(Throwable e) {
        if (e != null) {
            String message;
            if (!T.Network.isAvailable()) {
                message = "网络连接不上，请检查网络设置";
            } else {
                message = e.getMessage();
                if (e instanceof IOException) {
                    message = "网络超时";
                } else if (e instanceof HttpException) {
                    message = "连接服务器出错：" + ((HttpException) e).code() + ", 请稍候重试";
                }
                if (TextUtils.isEmpty(message)) {
                    message = "网络连接错误";
                }
            }
            final String showMessage = message;
            //ToastUtils.showToast(showMessage);
        }
        Log.e("okhttp", "error", e);
    }

    public void handleResponse(Result result) {
        if (result != null) {
            if (result.isOk())
                responseLifecycle.onResponse((Data) result.getData());
            else
                responseLifecycle.onFailed(result);
        } else {
            responseLifecycle.onFailed(null);
        }
    }

    public void showLoading() {
        if (!TextUtils.isEmpty(loadingText) && mProgressDialog != null) {
            RxSimple.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    ILoadingDialog progress = mProgressDialog.get();
                    if (progress != null) {
                        progress.showLoading(loadingText);
                    }
                }
            });
        }
    }

    public void hideLoading() {
        if (!TextUtils.isEmpty(loadingText) && mProgressDialog != null) {
            RxSimple.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    ILoadingDialog progress = mProgressDialog.get();
                    if (progress != null) {
                        progress.hideLoading();
                    }
                }
            });
        }
    }

}
