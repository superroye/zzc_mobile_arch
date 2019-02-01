package com.zzc.network.support.response;

//import org.greenrobot.eventbus.EventBus;

/**
 * @author Roye
 * @date 2018/9/28
 */
public class DefaultResponseCodeHandle {

    public static void handle(IHttpResponse response) {
        HttpResponse result = (HttpResponse) response;
        if (result.code == 1 || result.code == 1003) {
            return;
        }
        if (result.code == 1000 || result.code == 1001 || result.code == 1002) {
            //EventBus.getDefault().post(new NeedQuickLoginEvent());
            return;
        }
        if (result.code == 6) {
            //EventBus.getDefault().post(new ClearTokenEvent());
            return;
        }
    }
}
