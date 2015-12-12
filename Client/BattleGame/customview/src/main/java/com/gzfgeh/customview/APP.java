package com.gzfgeh.customview;

import android.app.Application;
import android.content.Context;

/**
 * Created by guzhenfu on 15/8/8.
 */
public class APP extends Application {
    private static Context context;
//    private RefWatcher refWatcher;
//    private volatile static AsyncHttpClient asyncHttpClient;
//
//    public static AsyncHttpClient getAsyncHttpClientInstance(){
//        if (asyncHttpClient == null){
//            synchronized (APP.class){
//                if (asyncHttpClient == null){
//                    asyncHttpClient = new AsyncHttpClient();
//                }
//            }
//        }
//        return asyncHttpClient;
//    }

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        setSentence();
//        if (debugMode()){
//            refWatcher = LeakCanary.install(this);
//            LogUtils.LEVEL = 0;
//        }else{
//            refWatcher = RefWatcher.DISABLED;
//            LogUtils.LEVEL = LogUtils.NOTHING;
//        }

    }
//
//    private void setSentence(){
//        boolean once = ShareUtils.getValue(Global.APP_ONCE, false);
//        if (!once){
//            ShareUtils.putValue("1", "我说1");
//            ShareUtils.putValue("2", "我说2");
//            ShareUtils.putValue("3", "我说3");
//            ShareUtils.putValue("4", "我说4");
//            ShareUtils.putValue("5", "我说5");
//            ShareUtils.putValue("6", "我说6");
//            ShareUtils.putValue("7", "我说7");
//            ShareUtils.putValue("8", "我说8");
//            ShareUtils.putValue("9", "我说9");
//            ShareUtils.putValue("10", "我说10");
//        }
//    }
//
//    private static boolean debugMode(){
//        ApplicationInfo info = getContext().getApplicationInfo();
//        if ((info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
//            return true;
//        }else{
//            return false;
//        }
//    }
}
