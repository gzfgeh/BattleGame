package com.gzfgeh.battlegame.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.gzfgeh.battlegame.socket.ConnectiorManager;
import com.gzfgeh.battlegame.socket.MinaManager;
import com.gzfgeh.battlegame.utils.SharePerferencesTool;

/**
 * Created by guzhenf on 6/28/2015.
 */
public class MinaService extends Service {
    private ConnectiorManager manager;

    @Override
    public void onCreate() {
        manager = ConnectiorManager.getManager(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //new MinaThread("192.155.83.65", 8989).run();
        if (intent == null){
            intent = new Intent();
            String host = SharePerferencesTool.getValue(this, MinaManager.SERVIER_HOST, null);
            int port = SharePerferencesTool.getValue(this, MinaManager.SERVIER_PORT, 0);
            intent.putExtra(MinaManager.SERVIER_HOST, host);
            intent.putExtra(MinaManager.SERVIER_PORT, port);
        }

        String action = intent.getStringExtra(MinaManager.SERVICE_ACTION);

        if (ConnectiorManager.ACTION_CONNECTION.equals(action)){
            String host = intent.getStringExtra(MinaManager.SERVIER_HOST);
            int port = intent.getIntExtra(MinaManager.SERVIER_PORT, 0);
            manager.connect(host, port);
        }

        if (ConnectiorManager.ACTION_SEND_MESSAGE.equals(action)){
            //Message message = (Message)intent.getSerializableExtra(ConnectiorManager.MESSAGE);
            manager.sendMessage(intent.getStringExtra(ConnectiorManager.MESSAGE));
        }

        if (ConnectiorManager.ACTION_DISCONNECTION.equals(action)){
            manager.closeSession();
        }

        return Service.START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
