package com.gzfgeh.todaythings;

import android.app.Application;
import android.content.Context;

import com.gzfgeh.todaythings.utils.ShareUtils;

/**
 * Created by guzhenfu on 15/8/8.
 */
public class APP extends Application {
    private static final String ONCE = "ONCE";
    public static final String USER_SENTENCE = "USER_SENTENCE";
    private static Context context;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        setSentence();

    }

    private void setSentence(){
        boolean once = ShareUtils.getValue(ONCE, false);
        if (!once){
            ShareUtils.putValue("1", "我说1");
            ShareUtils.putValue("2", "我说2");
            ShareUtils.putValue("3", "我说3");
            ShareUtils.putValue("4", "我说4");
            ShareUtils.putValue("5", "我说5");
            ShareUtils.putValue("6", "我说6");
            ShareUtils.putValue("7", "我说7");
            ShareUtils.putValue("8", "我说8");
            ShareUtils.putValue("9", "我说9");
            ShareUtils.putValue("10", "我说10");
            ShareUtils.putValue(ONCE, true);
        }
    }
}
