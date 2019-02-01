package com.zzc.mobilearch.uilibs.activity.internal;

/**
 * @author Roye
 * @date 2019/1/31
 */
public interface LoadingBehavior extends UIBehavior {

    void showLoading(String text);

    void hideLoading();

}
