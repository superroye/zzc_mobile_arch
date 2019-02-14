package com.zzc.mobilearch.uilibs.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import com.zzc.mobilearch.uilibs.R;
import com.zzc.mobilearch.uilibs.Utils;

/**
 * Created by Roye on 2016/1/19.
 */
public class PageFrameActivity extends IntelligentActivityImpl {

    public final static String EXTRA_CLASS = "fragmentClassName";
    public final static String EXTRA_TOOLBAR_TITLE = "toorbarTitle";
    public final static String EXTRA_SCREEN = "screen";

    public static void launchActivity(Context context, Class<? extends Fragment> cls) {
        launchActivity(context, cls, null, null);
    }

    public static void launchActivity(Context context, Class<? extends Fragment> cls, Bundle extras) {
        launchActivity(context, cls, null, extras);
    }

    public static void launchActivity(Context context, Class<? extends Fragment> cls, String toolbarTitle, Bundle extras) {
        Intent intent = new Intent(context, PageFrameActivity.class);
        intent.putExtra(EXTRA_CLASS, cls.getName());
        intent.putExtra(EXTRA_TOOLBAR_TITLE, toolbarTitle);
        if (context == Utils.getApp()) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (extras != null)
            intent.putExtras(extras);
        context.startActivity(intent);
    }

    public static Intent getIntent(Context context, Class<? extends Fragment> cls) {
        Intent intent = new Intent(context, PageFrameActivity.class);
        intent.putExtra(EXTRA_CLASS, cls.getName());

        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getSupportFragmentManager() == null)
            return;
        Fragment f = getSupportFragmentManager().findFragmentByTag("root");
        if (f == null)
            return;
        f.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int layoutId() {
        String fragmentTitle = getIntent().getStringExtra(EXTRA_TOOLBAR_TITLE);
        if (!TextUtils.isEmpty(fragmentTitle)) {
            return R.layout.base_activity_page_frame_inc_title;
        }
        return R.layout.base_activity_page_frame;
    }

    @Override
    public void onSetContentView() {
        int screen = getIntent().getIntExtra(EXTRA_SCREEN, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (screen == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (screen == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void initView() {
        Intent data = getIntent();

        if (data != null) {
            String fragmentClassName = data.getStringExtra(EXTRA_CLASS);
            String fragmentTitle = getIntent().getStringExtra(EXTRA_TOOLBAR_TITLE);
            if (!TextUtils.isEmpty(fragmentTitle)) {
                getUIHelper().setupToolbar();
            }
            getUIHelper().setupStatusbar();
            getUIHelper().setupLoading();

            Fragment fragment = null;
            try {
                fragment = (Fragment) Class.forName(fragmentClassName).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (fragment != null) {
                Bundle args = new Bundle();
                if (data.getExtras() != null) {
                    args.putAll(data.getExtras());
                }
                fragment.setArguments(args);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_content, fragment, "root")
                        .commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void initData() {

    }
}
