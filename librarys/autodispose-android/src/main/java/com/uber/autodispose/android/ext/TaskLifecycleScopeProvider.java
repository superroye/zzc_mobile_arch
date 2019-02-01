package com.uber.autodispose.android.ext;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

/**
 * @author Roye
 * @date 2018/12/13
 */
public class TaskLifecycleScopeProvider {

    private TaskLifecycleScopeProvider() {

    }

    private Lifecycle.State mAtLeast;
    private LifecycleOwner mLifecycleOwner;

    private static TaskLifecycleScopeProvider from(LifecycleOwner lifecycleOwner, Lifecycle.State atLeast) {
        TaskLifecycleScopeProvider provider = new TaskLifecycleScopeProvider();
        provider.mAtLeast = atLeast;
        provider.mLifecycleOwner = lifecycleOwner;
        return provider;
    }

    /**
     * 默认在destroy状态时中止运行
     */
    public static TaskLifecycleScopeProvider from(Context context) {
        LifecycleOwner lifecycleOwner = null;
        if (context != null && context instanceof LifecycleOwner) {
            lifecycleOwner = (LifecycleOwner) context;
        }
        return from(lifecycleOwner, Lifecycle.State.INITIALIZED);
    }

    public static TaskLifecycleScopeProvider from(Context context, Lifecycle.State atLeast) {
        LifecycleOwner lifecycleOwner = null;
        if (context != null && context instanceof LifecycleOwner) {
            lifecycleOwner = (LifecycleOwner) context;
        }
        return from(lifecycleOwner, atLeast);
    }

    public boolean shouldDoByLifecycle() {
        if (mLifecycleOwner != null && mAtLeast != null) {
            if (mLifecycleOwner.getLifecycle().getCurrentState().isAtLeast(mAtLeast)) {
                return true;
            }
        }
        return false;
    }

    public Context getContext() {
        if (mLifecycleOwner != null && mLifecycleOwner instanceof Context) {
            return (Context) mLifecycleOwner;
        }
        return null;
    }


}
