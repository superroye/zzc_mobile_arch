package com.zzc.mobilearch.core.util;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.zzc.mobilearch.core.app.AppBase;

class DeviceUtils {

   @SuppressLint("MissingPermission")
   public static String imei() {
       String deviceIMEI = null;
       try {
           TelephonyManager teleManager = (TelephonyManager) AppBase.app.getSystemService(Context.TELEPHONY_SERVICE);
           deviceIMEI = teleManager.getDeviceId();
       } catch (Exception e) {

       }
       return deviceIMEI;
   }

   public static String brand() {
       return Build.BRAND;
   }

   public static String model() {
       return Build.MODEL;
   }

   public static String device() {
       return Build.DEVICE;
   }

   public static String product() {
       return Build.PRODUCT;
   }

   public static String display() {
       return Build.DISPLAY;
   }

   public static String manufacture() {
       return Build.MANUFACTURER;
   }

   public static String deviceUUId(Context context) {
       return MD5Utils.toMd5(getInfo(context) + imei() + getAndroidId(context));
   }

   public static String getInfo(Context context) {
       String model = Build.MODEL;
       String device = Build.DEVICE;
       String brand = Build.BRAND;
       String product = Build.PRODUCT;
       String display = Build.DISPLAY;
       String manufacture = Build.MANUFACTURER;

       Resources resources = context.getResources();
       DisplayMetrics dm = resources.getDisplayMetrics();
       int screenWidth = dm.widthPixels;
       int screenHeight = dm.heightPixels;
       float density = dm.density;

       StringBuilder sb = new StringBuilder();
       String finalInfo = sb.append("MODEL " + model).append("\nDEVICE " + device).append("\nBRAND " + brand).append("\nPRODUCT " + product).append("\nDISPLAY " + display)
               .append("\nMANUFACTURE " + manufacture).append("\nSCREEN_WIDTH " + screenWidth).append("\nSCREEN_HEIGHT " + screenHeight).append("\nDENSITY " + density).toString();
       return finalInfo;
   }

   public static final String getBluetoothMac() {
       BluetoothAdapter adapter = null;
       String bluetoothMac = null;
       try {
           adapter = BluetoothAdapter.getDefaultAdapter();
           bluetoothMac = adapter.getAddress();
       } catch (Exception e) {
       }
       return bluetoothMac;
   }

   public static final String getWlanMac(Context context) {
       String wlanMac = null;
       try {
           WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
           wlanMac = wm.getConnectionInfo().getMacAddress();
       } catch (Exception e) {

       }
       return wlanMac;
   }

   public static final String getAndroidId(Context context) {
       String androidID = null;
       try {
           androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
       } catch (Exception e) {

       }
       return androidID;
   }
}