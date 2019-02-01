package com.zzc.mobilearch.uilibs.activity.internal;

import android.support.v4.app.FragmentActivity;

import com.zzc.mobilearch.uilibs.activity.internal.impl.LoadingBehaviorImpl;
import com.zzc.mobilearch.uilibs.activity.internal.impl.StatusBarBehaviorImpl;
import com.zzc.mobilearch.uilibs.activity.internal.impl.ToolbarBehaviorImpl;

/**
 * @author Roye
 * @date 2019/1/31
 */
public class UIBehaviorHelper {

    private ToolbarBehavior toolbarBehavior;
    private LoadingBehavior loadingBehavior;
    private StatusBarBehavior statusBarBehavior;
    private FragmentActivity mActivity;

    public UIBehaviorHelper(FragmentActivity activity) {
        this.mActivity = activity;
    }

    /**
     * @param behavior ToolbarBehavior/LoadingBehavior or..
     * 自定义安装UI行为
     */
    public void setup(UIBehavior behavior) {
        if (behavior == null) {
            return;
        }
        if (behavior instanceof ToolbarBehavior) {
            if (toolbarBehavior == null) {
                toolbarBehavior = (ToolbarBehavior) behavior;
                toolbarBehavior.setup(mActivity);
            }
        } else if (behavior instanceof LoadingBehavior) {
            if (loadingBehavior == null) {
                loadingBehavior = (LoadingBehavior) behavior;
                loadingBehavior.setup(mActivity);
            }
        } else if(behavior instanceof StatusBarBehavior){
            if (statusBarBehavior == null) {
                statusBarBehavior = (StatusBarBehavior) behavior;
                statusBarBehavior.setup(mActivity);
            }
        }
    }

    public void setupToolbar() {
        setup(new ToolbarBehaviorImpl());
    }

    public void setupLoading() {
        setup(new LoadingBehaviorImpl());
    }

    public void setupStatusbar() {
        setup(new StatusBarBehaviorImpl());
    }

    public LoadingBehavior loading() {
        if (loadingBehavior == null){
            throw new NullPointerException("'setupLoading' method is not be called.");
        }
        return loadingBehavior;
    }

    public ToolbarBehavior toolbar() {
        if (toolbarBehavior == null){
            throw new NullPointerException("'setupToolbar' method is not be called.");
        }
        return toolbarBehavior;
    }

    public StatusBarBehavior statusbar() {
        if (statusBarBehavior == null){
            throw new NullPointerException("'setupStatusbar' method is not be called.");
        }
        return statusBarBehavior;
    }

    public void release() {
        mActivity = null;
        if (toolbarBehavior != null) {
            toolbarBehavior.release();
        }
        if (loadingBehavior != null) {
            loadingBehavior.release();
        }
    }

}
