package com.zzc.mobilearch.core.util;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Roye
 * @date 2018/8/23
 */
class ContextUtils {

    public static FragmentActivity getActivity(Context context) {
        Context context1 = context;
        while (!(context1 instanceof Activity) && context1 instanceof ContextWrapper) {
            context1 = ((ContextWrapper) context1).getBaseContext();
        }

        AppCompatActivity activity = null;
        if (context1 instanceof AppCompatActivity) {
            activity = (AppCompatActivity) context1;
        }
        if (activity == null) {
            android.util.Log.e("ContextUtils", "current activity is null");
        }
        return activity;
    }

    public static FragmentActivity getActivity(LifecycleOwner lifecycleOwner) {
        if (lifecycleOwner == null) {
            return null;
        }
        if (lifecycleOwner instanceof Context) {
            return getActivity((Context) lifecycleOwner);
        } else if (lifecycleOwner instanceof Fragment) {
            return ((Fragment) lifecycleOwner).getActivity();
        }
        return null;
    }
}
