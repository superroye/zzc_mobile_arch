package com.zzc.mobilearch.core.lifearch;

import android.arch.lifecycle.MediatorLiveData;
import android.text.TextUtils;

import com.zzc.mobilearch.core.ui.ILoadingDialog;

/**
 * 通过继承此ViewModel，就能通知UI组件呈现loading，动画等效果
 *
 * @author Roye
 * @date 2018/11/14
 */
public final class LifeUIBehavior implements ILoadingDialog {

    public static final int LOADING_HIDE = 0;
    public static final int LOADING_SHOW = 1;
    private MediatorLiveData<ActionEntity> loading;
    private MediatorLiveData<ActionEntity> animation;

    public LifeUIBehavior() {
        loading = new MediatorLiveData();
        animation = new MediatorLiveData();
    }

    public MediatorLiveData<ActionEntity> loading() {
        return loading;
    }

    public MediatorLiveData<ActionEntity> animation() {
        return animation;
    }

    @Override
    public void showLoading(String text) {
        String text1 = text;
        if (TextUtils.isEmpty(text1)) {
            text = "loading...";
        }
        loading().postValue(new ActionEntity(LOADING_SHOW, new Object[]{text}));
    }

    @Override
    public void hideLoading() {
        loading().postValue(new ActionEntity(LOADING_HIDE, null));
    }
}
