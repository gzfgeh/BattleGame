package com.gzfgeh.battlegame.socket;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by guzhenf on 6/28/2015.
 */
public class MinaListenerManager {
    private static ArrayList<OnMinaMessageListener> listeners = new ArrayList<>();

    public static void registerMessageListener(Context context, OnMinaMessageListener listener){
        if (!listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    public static void removeMessageListener(OnMinaMessageListener listener){
        for(int i=0; i<listeners.size(); i++){
            if (listener.getClass() == listeners.get(i).getClass()){
                listeners.remove(i);
            }
        }
    }

    public static ArrayList<OnMinaMessageListener> getListeners(){
        return  listeners;
    }
}
