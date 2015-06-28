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
    public static String  ACTION_CONNECTION ="ACTION_CONNECTION";
    public static String  ACTION_DISCONNECTION ="ACTION_DISSENDREQUEST";

    private MinaManager(){

    }

    public static void init(Context context, String ip, int port){
        Intent intent = new Intent(context, MinaService.class);
        intent.putExtra(SERVIER_HOST, ip);
        intent.putExtra(SERVIER_PORT, port);
        intent.putExtra(SERVICE_ACTION, ACTION_CONNECTION);
        context.startService(intent);
        SharePerferencesTool.putValue(context, SERVIER_HOST, ip);
        SharePerferencesTool.putValue(context, SERVIER_PORT, port);
    }
}
