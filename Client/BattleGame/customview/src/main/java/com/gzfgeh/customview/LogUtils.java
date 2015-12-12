package com.gzfgeh.customview;

import android.util.Log;

/**
 * Created by guzhenf on 7/20/2015.
 */
public class LogUtils {
    public static final String TAG = "TAG";

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static int LEVEL = 0;

    public static void v(String tag, String msg){
        if (LEVEL <= VERBOSE)
            Log.v(tag, msg);
    }

    public static void d(String tag, String msg){
        if (LEVEL <= VERBOSE)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg){
        if (LEVEL <= VERBOSE)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg){
        if (LEVEL <= VERBOSE)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg){
        if (LEVEL <= VERBOSE)
            Log.e(tag, msg);
    }

}
