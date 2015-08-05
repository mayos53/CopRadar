package com.app.copradar.util.util;


import android.util.Log;

import com.crashlytics.android.Crashlytics;

public class Loger {

    public static void d (String tag, String msg){
        if (Tools.DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void d (String tag, String msg, Throwable tr){
        if (Tools.DEBUG){
            Log.d(tag, msg,tr);
        }
    }

    public static void e (String tag, String msg, Throwable tr){
        if (Tools.DEBUG){
            Log.e(tag, msg, tr);
        }else{
            Log.e(tag, msg, tr);
            Crashlytics.getInstance().core.logException(tr);
        }
    }

    public static void i (String tag, String msg){
        if (Tools.DEBUG){
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if (Tools.DEBUG){
            Log.w(tag, msg);
        }
    }
}
