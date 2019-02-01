package com.zzc.mobilearch.uilibs.activity.internal;

/**
 * @author Roye
 * @date 2019/1/31
 */
public interface IntelligentActivity {

    public void onSetContentView();
    public int layoutId();
    public void initView();
    public void initData();
    public UIBehaviorHelper getUIHelper();
}
