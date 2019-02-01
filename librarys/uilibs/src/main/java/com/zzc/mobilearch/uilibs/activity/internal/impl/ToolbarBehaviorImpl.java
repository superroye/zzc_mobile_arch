package com.zzc.mobilearch.uilibs.activity.internal.impl;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.uber.autodispose.android.ext.LifecycleUtils;
import com.zzc.mobilearch.uilibs.R;
import com.zzc.mobilearch.uilibs.activity.internal.ToolbarBehavior;

/**
 * @author Roye
 * @date 2019/1/31
 */
public class ToolbarBehaviorImpl implements ToolbarBehavior {

    protected Toolbar toolbar;
    protected TextView titleView;

    @Override
    public void setTitle(String title) {
        titleView.setText(title);
    }

    @Override
    public void hideNavigationIcon() {
        toolbar.setNavigationIcon(null);
    }

    @Override
    public void setNavigationOnClickListener(View.OnClickListener listener) {
        toolbar.setNavigationOnClickListener(listener);
    }

    @Override
    public void inflateMenu(int menu) {
        toolbar.inflateMenu(menu);
    }

    @Override
    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener listener) {
        toolbar.setOnMenuItemClickListener(listener);
    }

    @Override
    public void setup(FragmentActivity activity) {
        final FragmentActivity mActivity = activity;
        toolbar = activity.findViewById(R.id.toolbar);
        titleView = activity.findViewById(R.id.custom_title_name);
        if (toolbar == null) {
            throw new NullPointerException("make sure the Activity layout has Toolbar which its id is 'toolbar'.");
        }
        if(titleView ==null){
            throw new NullPointerException("make sure the Activity layout has TextView which its id is 'custom_title_name'.");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LifecycleUtils.isAvailable(mActivity)) {
                    mActivity.onBackPressed();
                }
            }
        });
    }

    @Override
    public void release() {

    }
}
