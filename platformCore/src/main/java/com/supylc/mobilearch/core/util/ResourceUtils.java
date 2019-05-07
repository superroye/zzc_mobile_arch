package com.supylc.mobilearch.core.util;

import android.content.res.Resources;
import com.supylc.mobilearch.core.app.BaseApp;

class ResourceUtils {

   public static int getAnim(String name) {
       return getRes("anim", name);
   }

   public static int getAttr(String name) {
       return getRes("attr", name);
   }

   public static int getColor(String name) {
       return getRes("color", name);
   }

   public static int getDrawable(String name) {
       return getRes("drawable", name);
   }

   public static int getId(String name) {
       return getRes("id", name);
   }

   public static int getLayout(String name) {
       return getRes("layout", name);
   }

   public static int getString(String name) {
       return getRes("string", name);
   }

   public static int getStyle(String name) {
       return getRes("style", name);
   }

   public static int getMipmap(String name) {
       return getRes("mipmap", name);
   }

   public static int getRes(String type, String name) {
       String packageName = BaseApp.gContext.getPackageName();
       Resources res = BaseApp.gContext.getResources();
       int identifier = res.getIdentifier(name, type, packageName);
       if (identifier == 0) {

       }
       return identifier;
   }
}
