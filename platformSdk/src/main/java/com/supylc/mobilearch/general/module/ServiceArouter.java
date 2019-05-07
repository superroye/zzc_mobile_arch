package com.supylc.mobilearch.general.module;

import android.text.TextUtils;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author Roye
 * @date 2019/3/27
 */
public class ServiceArouter {

    private static final String TAG = "ServiceArouter";

    //利用路径查找
    public static <T extends IProvider> T api(String path) {

        if (TextUtils.isEmpty(path)) {
            return null;
        }

        IProvider provider = null;

        try {
            provider = (IProvider) ARouter.getInstance()
                    .build(path)
                    .navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T) provider;
    }

    //利用类查找
    public static <T extends IProvider> T api(Class<T> clz) {

        IProvider provider = null;
        try {
            provider = ARouter.getInstance().navigation(clz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T) provider;
    }
}
