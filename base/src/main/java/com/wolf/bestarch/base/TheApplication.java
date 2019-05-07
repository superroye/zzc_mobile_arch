package com.wolf.bestarch.base;

import android.support.multidex.MultiDexApplication;

import com.supylc.mobilearch.core.app.BaseApp;
import com.wolf.bestarch.base.network.NetworkComponent;

/**
 * @author Roye
 * @date 2019/1/30
 */
public class TheApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        BaseApp.getInstance().onCreate(this, true);

        NetworkComponent.init();
    }
}
