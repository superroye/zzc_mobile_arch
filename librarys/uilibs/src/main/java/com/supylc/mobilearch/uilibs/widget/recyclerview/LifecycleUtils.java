package com.supylc.mobilearch.uilibs.widget.recyclerview;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

/**
 * @author Roye
 * @date 2019/1/30
 */
public class LifecycleUtils {

    public static boolean isAvailable(LifecycleOwner lifecycleOwner) {
        if (lifecycleOwner == null) {
            return false;
        }
        if (lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.INITIALIZED)) {
            return true;
        }
        return false;
    }

    public static boolean isActivityAvailable(Activity mActivity) {
        if (mActivity == null || mActivity.isFinishing())
            return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (mActivity.isDestroyed())
                return false;
        }
        return true;
    }

    public static boolean isContextAvailable(Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof LifecycleOwner) {
            return isAvailable((LifecycleOwner) context);
        }

        Context context1 = context;
        while (!(context1 instanceof Activity) && context1 instanceof ContextWrapper) {
            context1 = ((ContextWrapper) context1).getBaseContext();
        }

        Activity activity = null;
        if (context1 instanceof Activity) {
            activity = (Activity) context1;
        }
        if (activity != null) {
            return isActivityAvailable(activity);
        }
        return false;
    }

}
