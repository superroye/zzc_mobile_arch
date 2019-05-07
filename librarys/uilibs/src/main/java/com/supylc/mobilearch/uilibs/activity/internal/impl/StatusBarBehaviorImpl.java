package com.supylc.mobilearch.uilibs.activity.internal.impl;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.supylc.mobilearch.uilibs.R;
import com.supylc.mobilearch.uilibs.activity.internal.StatusBarBehavior;
import com.supylc.mobilearch.uilibs.widget.statusbar.AppCompatStatusBar;
import com.supylc.mobilearch.uilibs.widget.statusbar.StatusBarUtils;

/**
 * @author Roye
 * @date 2019/1/31
 */
public class StatusBarBehaviorImpl implements StatusBarBehavior {

    private AppCompatStatusBar appCompatStatusBar;
    private FragmentActivity mActivity;

    @Override
    public void setStatusBar(int color) {
        StatusBarUtils.setColor(mActivity, color, 0);
    }

    @Override
    public void setStatusBar() {
        setStatusBar(ContextCompat.getColor(mActivity, R.color.colorStatusbar));
    }

    @Override
    public void fullScreen() {
        if (appCompatStatusBar == null) {
            appCompatStatusBar = new AppCompatStatusBar(mActivity);
        }
        appCompatStatusBar.fullScreen();
        appCompatStatusBar.clearActionBarShadow();
    }

    @Override
    public void setup(FragmentActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public void release() {
        mActivity = null;
    }
}
