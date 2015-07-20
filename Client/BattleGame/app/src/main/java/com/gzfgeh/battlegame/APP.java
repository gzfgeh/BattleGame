package com.gzfgeh.battlegame;

import android.app.Application;
import android.content.Context;

/**
 * Created by guzhenf on 7/20/2015.
 */
public class APP extends Application{

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }
}
