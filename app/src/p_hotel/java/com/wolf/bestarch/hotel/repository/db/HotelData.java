package com.wolf.bestarch.hotel.repository.db;

import com.zzc.mobilearch.core.storage.CommonData;

/**
 * @author Roye
 * @date 2019/1/23
 */
public class HotelData {

    public final static String SP_FILE = "hotel_bl_data";

    public long getStartMs() {
        return getData().getLongValue("startMs");
    }

    public void setStartMs(long startMs) {
        getData().setValue("startMs", startMs);
    }

    public long getEndMs() {
        return getData().getLongValue("endMs");
    }

    public void setEndMs(long endMs) {
        getData().setValue("endMs", endMs);
    }

    public Object getRoomParam() {
        return null;
    }

    public void setRoomParam(Object room) {
    }

    private static CommonData data;
    private static HotelData mInstance;

    private static CommonData getData() {
        if (data == null) {
            data = new CommonData(SP_FILE);
        }
        return data;
    }

    private HotelData() {
    }

    public static HotelData getInstance() {
        if (mInstance == null) {
            mInstance = new HotelData();
        }
        return mInstance;
    }

}
