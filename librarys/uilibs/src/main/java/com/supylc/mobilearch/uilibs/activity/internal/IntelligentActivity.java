package com.supylc.mobilearch.uilibs.activity.internal;

/**
 * @author Roye
 * @date 2019/1/31
 */
public interface IntelligentActivity {

    void onSetContentView();

    int layoutId();

    void initView();

    void initData();

    UIBehaviorHelper getUIHelper();
}
