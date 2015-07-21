package com.gzfgeh.battlegame.model;

import com.gzfgeh.battlegame.utils.LogUtils;

/**
 * Created by guzhenf on 7/21/2015.
 */
public class Game {

    public static void setDebugMode(boolean debug){
        if (debug){
            LogUtils.LEVEL = LogUtils.VERBOSE;
        }else{
            LogUtils.LEVEL = LogUtils.NOTHING;
        }
    }

}
