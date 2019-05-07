package com.supylc.mobilearch.core.app;

import android.app.Application;
import android.os.Handler;

/**
 * create by elileo on 2019/2/18
 */
public class BaseApp {
    private static BaseApp sInstance;
    private BaseApp(){
    }
    public static BaseApp getInstance(){
        if(sInstance == null){
            synchronized (BaseApp.class){
                if(sInstance == null){
                    sInstance = new BaseApp();
                }
            }
        }
        return sInstance;
    }

    public static Handler gMainHandler;
    public static Application gContext;

    public static boolean DEBUG;

    private boolean mMainProcess = true;

    public void isMainProcess(boolean mainProcess){
        mMainProcess = mainProcess;
    }

    public void onCreate(Application context, boolean isDebug) {
        if(mMainProcess){
            gMainHandler = new Handler();
            gContext = context;
            DEBUG = isDebug;
        }
    }

    public static void runAsync(Runnable r) {
        gMainHandler.post(r);
    }

    public static void runAsyncDelay(Runnable r, long delayMillis) {
        gMainHandler.postDelayed(r, delayMillis);
    }
}
