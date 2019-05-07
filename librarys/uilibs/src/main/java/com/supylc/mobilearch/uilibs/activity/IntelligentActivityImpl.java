package com.supylc.mobilearch.uilibs.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.supylc.mobilearch.uilibs.activity.internal.IntelligentActivity;
import com.supylc.mobilearch.core.util.T;
import com.supylc.mobilearch.uilibs.activity.internal.UIBehaviorHelper;

/**
 * @author Roye
 * @date 2019/1/31
 */
public abstract class IntelligentActivityImpl extends AppCompatActivity implements IntelligentActivity {

    private UIBehaviorHelper mUIBehaviorHelper;
    private boolean isRestoreInstance;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isRestoreInstance = true;
        super.onRestoreInstanceState(savedInstanceState);
    }

    public boolean isRestoreInstance() {
        return isRestoreInstance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mUIBehaviorHelper == null) {
            mUIBehaviorHelper = new UIBehaviorHelper(this);
        }

        if (savedInstanceState != null) {
            isRestoreInstance = true;
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        onSetContentView();

        setContentView(layoutId());

        initView();
    }

    @Override
    public void onSetContentView() {

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        initData();
    }

    @Override
    public UIBehaviorHelper getUIHelper() {
        return mUIBehaviorHelper;
    }

    @Override
    protected void onResume() {
        super.onResume();

        T.UI.init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUIBehaviorHelper != null) {
            mUIBehaviorHelper.release();
        }
    }

    @Override
    public void finish() {
        super.finish();
        super.overridePendingTransition(com.supylc.mobilearch.design.R.anim.idle, com.supylc.mobilearch.design.R.anim.slide_out_right);
    }

    @TargetApi(11)
    public void startActivities(Intent[] paramArrayOfIntent) {
        super.startActivities(paramArrayOfIntent);
        anim();
    }

    @TargetApi(16)
    public void startActivities(Intent[] paramArrayOfIntent, Bundle paramBundle) {
        super.startActivities(paramArrayOfIntent, paramBundle);
        anim();
    }

    public void startActivity(Intent paramIntent) {
        super.startActivity(paramIntent);
        anim();
    }

    @TargetApi(16)
    public void startActivity(Intent paramIntent, Bundle paramBundle) {
        super.startActivity(paramIntent, paramBundle);
        anim();
    }

    public void startActivityForResult(Intent paramIntent, int paramInt) {
        super.startActivityForResult(paramIntent, paramInt);
        anim();
    }

    private void anim() {
        super.overridePendingTransition(com.supylc.mobilearch.design.R.anim.slide_in_left, com.supylc.mobilearch.design.R.anim.idle);
    }

}
