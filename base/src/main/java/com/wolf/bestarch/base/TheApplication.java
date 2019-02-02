package com.wolf.bestarch.base;

import android.app.Application;

import com.wolf.bestarch.base.network.NetworkComponent;
import com.zzc.mobilearch.core.app.AppBase;

/**
 * @author Roye
 * @date 2019/1/30
 */
public class TheApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppBase.init(this, true);

        NetworkComponent.init();
    }
}
