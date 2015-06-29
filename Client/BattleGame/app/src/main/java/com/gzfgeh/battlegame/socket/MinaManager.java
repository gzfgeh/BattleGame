package com.gzfgeh.battlegame.socket;

import android.content.Context;
import android.content.Intent;

import com.gzfgeh.battlegame.service.MinaService;
import com.gzfgeh.battlegame.utils.SharePerferencesTool;

/**
 * Created by guzhenf on 6/28/2015.
 */
public class MinaManager {

    public static String SERVIER_HOST = "SERVIER_HOST";
    public static String SERVIER_PORT = "SERVIER_PORT";
    public static String SERVICE_ACTION = "SERVICE_ACTION";


    private MinaManager(){

    }

    public static void init(Context context, String ip, int port){
        Intent intent = new Intent(context, MinaService.class);
        intent.putExtra(SERVIER_HOST, ip);
        intent.putExtra(SERVIER_PORT, port);
        intent.putExtra(SERVICE_ACTION, ConnectiorManager.ACTION_CONNECTION);
        context.startService(intent);
        SharePerferencesTool.putValue(context, SERVIER_HOST, ip);
        SharePerferencesTool.putValue(context, SERVIER_PORT, port);
    }

    public static void sendMessage(Context context, String message){
        Intent intent = new Intent(context, MinaService.class);
        intent.putExtra(SERVICE_ACTION, ConnectiorManager.ACTION_SEND_MESSAGE);
        intent.putExtra(ConnectiorManager.MESSAGE, message);
        context.startService(intent);
    }
}
