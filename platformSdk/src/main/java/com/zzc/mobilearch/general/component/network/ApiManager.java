package com.zzc.mobilearch.general.component.network;

/**
 * @author Roye
 * @date 2018/11/13
 */
public class ApiManager {

    private static NetworkComponent mNetworkComponent;

    private static NetworkComponent component() {
        if (mNetworkComponent == null) {
            synchronized (ApiManager.class) {
                if (mNetworkComponent == null) {
                    mNetworkComponent = new NetworkComponentImpl();
                }
            }
        }
        return mNetworkComponent;
    }

    public static <API> NetworkComponent.INetworkBuilder builder(Class<API> apiClass) {
        return component().builder(apiClass);
    }

    public static <API> API api(Class<API> apiClass) {
        return component().api(apiClass);
    }

}
