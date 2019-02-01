package com.zzc.mobilearch.core.util;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.zzc.mobilearch.core.app.AppBase;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;

/**
 * Created by liqi on 2016-2-23.
 */
class ToastUtils {

    private static long time = 0;
    private static String last = "";

    public static boolean isTooFast() {
        return isTooFast(600);
    }

    public static boolean isTooFast(int delay) {
        long curTime = System.currentTimeMillis();
        long span = curTime - time;
        time = curTime;
        if (span < delay) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSame(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            if (msg.equals(last))
                return true;
        }
        last = msg;
        return false;
    }

    public static void showToast(final String msg) {
        if (!isTooFast() || !isSame(msg)) {
            Observable.empty().subscribeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<Object>() {
                @Override
                public void onNext(Object s) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    Toast.makeText(AppBase.app, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void showToast(int resId) {
        String msg = AppBase.app.getString(resId);
        showToast(msg);
    }

    public static void showToastAtCenter(String msg) {
        if (isTooFast())
            return;
        if (AppBase.app == null)
            return;

        Toast toast = Toast.makeText(AppBase.app, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastAtCenter(int resId) {
        if (isTooFast())
            return;
        if (AppBase.app == null)
            return;

        Toast toast = Toast.makeText(AppBase.app, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastAtTop(String msg) {
        if (isTooFast())
            return;
        if (AppBase.app == null)
            return;
        Toast toast = Toast.makeText(AppBase.app, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 200);
        toast.show();
    }

    public static void showToastAtTop(int resId) {
        if (isTooFast())
            return;
        if (AppBase.app == null)
            return;
        Toast toast = Toast.makeText(AppBase.app, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 200);
        toast.show();
    }
}
