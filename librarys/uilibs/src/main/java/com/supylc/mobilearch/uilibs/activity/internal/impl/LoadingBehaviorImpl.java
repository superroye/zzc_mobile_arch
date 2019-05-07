package com.supylc.mobilearch.uilibs.activity.internal.impl;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;

import com.uber.autodispose.android.ext.LifecycleUtils;
import com.supylc.mobilearch.core.lifearch.ActionEntity;
import com.supylc.mobilearch.core.lifearch.LifeUIBehavior;
import com.supylc.mobilearch.core.lifearch.UIViewModel;
import com.supylc.mobilearch.uilibs.R;
import com.supylc.mobilearch.uilibs.activity.internal.LoadingBehavior;
import com.supylc.mobilearch.uilibs.dialog.ProgressDialogFragment;

/**
 * @author Roye
 * @date 2019/1/31
 */
public class LoadingBehaviorImpl implements LoadingBehavior {

    FragmentActivity mActivity;

    @Override
    public void showLoading(String text) {
        if (!LifecycleUtils.isActivityAvailable(mActivity))
            return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        if (fragmentManager == null || fragmentManager.isDestroyed()) {
            return;
        }

        if (TextUtils.isEmpty(text)) {
            text = mActivity.getResources().getString(R.string.common_loading);
        }

        Fragment fragment = fragmentManager.findFragmentByTag("progressDialog");
        if (fragment != null && ((DialogFragment) fragment).getDialog() != null) {
            ((ProgressDialog) ((DialogFragment) fragment).getDialog()).setMessage(text);
        } else {
            AppCompatDialogFragment dialog = new ProgressDialogFragment();
            Bundle args = new Bundle();
            args.putString("message", text);
            dialog.setArguments(args);

            try {
                dialog.show(fragmentManager, "progressDialog");
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void hideLoading() {
        if (!LifecycleUtils.isActivityAvailable(mActivity))
            return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        if (fragmentManager == null || fragmentManager.isDestroyed()) {
            return;
        }
        Fragment fragment = fragmentManager.findFragmentByTag("progressDialog");
        if (fragment != null) {
            DialogFragment df = (DialogFragment) fragment;
            try {
                df.dismissAllowingStateLoss();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setup(FragmentActivity activity) {
        this.mActivity = activity;

        UIViewModel viewModel = ViewModelProviders.of(activity).get(UIViewModel.class);
        viewModel.getLifeBehavior().loading().observe(activity, new Observer<ActionEntity>() {
            @Override
            public void onChanged(@Nullable ActionEntity data) {
                if (LifeUIBehavior.LOADING_SHOW == data.id) {
                    String text = "";
                    if (data.extra != null && data.extra.length > 0) {
                        text = data.extra[0].toString();
                    }
                    showLoading(text);
                } else if (LifeUIBehavior.LOADING_HIDE == data.id) {
                    hideLoading();
                }
            }
        });
    }

    @Override
    public void release() {
        mActivity = null;
    }
}
