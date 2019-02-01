package com.zzc.mobilearch.uilibs.activity.internal;

import android.support.v4.app.FragmentActivity;

/**
 * @author Roye
 * @date 2019/1/31
 */
public interface UIBehavior {

    void setup(FragmentActivity activity);
    void release();
}
