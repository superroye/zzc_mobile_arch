package com.zzc.mobilearch.uilibs;

import android.app.Application;

import com.zzc.mobilearch.core.app.AppBase;

/**
 * @author Roye
 * @date 2019/2/11
 */
public class Utils {

    public static Application getApp() {
        return AppBase.app;
    }

    public static boolean isDebug() {
        return AppBase.DEBUG;
    }
}
