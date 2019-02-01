package com.zzc.mobilearch.core.app;

import android.app.Application;
import android.text.TextUtils;
import com.zzc.mobilearch.core.util.T;

/**
 * @author Roye
 * @date 2018/10/24
 */
public class AppBase {

    public static String APP_ID;
    public static String APP_CHANNEL;
    public static Application app;
    public static String ENV;
    public static boolean DEBUG;
    public static boolean PRODUCT_ENV;

    public static final String SP_KEY_APPID = "appId";
    public static final String SP_KEY_CHANNEL = "app_channel";
    public static final String SP_KEY_ENV = "env";
    public static final String ENV_TEST = "test";
    public static final String ENV_PRODUCT = "product";

    public static void init(Application app, boolean isDebug) {
        AppBase.app = app;
        DEBUG = isDebug;

        T.Log.setDebug(isDebug);

        APP_ID = String.valueOf(T.App.getMetadataInt(app, SP_KEY_APPID));
        T.SP.put(SP_KEY_APPID, APP_ID);

        initChannel();

        initEnv();

        T.UI.init(app);
    }

    private static void initChannel() {
        String channel = T.App.getMetadataString(app, SP_KEY_CHANNEL);
        if (TextUtils.isEmpty(channel)) {
            channel = String.valueOf(T.App.getMetadataInt(app, SP_KEY_CHANNEL));
        }
        APP_CHANNEL = channel;
        String channel_sp = T.SP.get(SP_KEY_CHANNEL);
        if (TextUtils.isEmpty(channel_sp)) {
            T.SP.put(SP_KEY_CHANNEL, APP_CHANNEL);
        } else {
            APP_CHANNEL = channel_sp;
        }
    }

    private static void initEnv() {
        String env = T.SP.get(SP_KEY_ENV);
        if (TextUtils.isEmpty(env)) {
            env = T.App.getMetadataString(app, SP_KEY_ENV);
        }

        setEnv(env);
    }

    public static void setEnv(String env) {
        if (env != null) {
            ENV = env;
        } else {
            ENV = ENV_TEST;
        }

        PRODUCT_ENV = !ENV_TEST.equals(ENV);

        T.SP.put(SP_KEY_ENV, ENV);
    }

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }
}
