package com.supylc.network;

import android.app.Application;

import com.supylc.mobilearch.core.app.BaseApp;

/**
 * @author Roye
 * @date 2019/2/11
 */
public class Utils {

    public static Application getApp() {
        return BaseApp.gContext;
    }

    public static boolean isDebug() {
        return BaseApp.DEBUG;
    }
}
