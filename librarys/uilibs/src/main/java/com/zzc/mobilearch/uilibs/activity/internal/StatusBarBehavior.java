package com.zzc.mobilearch.uilibs.activity.internal;

import android.support.annotation.ColorInt;

/**
 * @author Roye
 * @date 2019/1/31
 */
public interface StatusBarBehavior extends UIBehavior {

    void setStatusBar(@ColorInt int color);
    void setStatusBar();
    void fullScreen();
}
