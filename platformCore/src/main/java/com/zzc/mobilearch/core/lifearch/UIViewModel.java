package com.zzc.mobilearch.core.lifearch;

import android.arch.lifecycle.ViewModel;

/**
 * @author Roye
 * @date 2019/2/1
 */
public class UIViewModel extends ViewModel {

    private LifeUIBehavior mLifeUIBehavior;

    public UIViewModel() {
        mLifeUIBehavior = new LifeUIBehavior();
    }

    public LifeUIBehavior getLifeBehavior() {
        return mLifeUIBehavior;
    }
}
