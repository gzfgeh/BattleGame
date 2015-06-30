package com.gzfgeh.battlegame.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;

import com.gzfgeh.battlegame.socket.ConnectiorManager;
import com.gzfgeh.battlegame.socket.OnMinaMessageListener;

/**
 * Created by guzhenf on 6/29/2015.
 */
public abstract  class MinaEventListenerReceiver extends BroadcastReceiver implements OnMinaMessageListener{
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        if (intent.getAction().equals(ConnectiorManager.ACTION_CONNECTION_SUCCESS)){
            onConnectionSucceed();

        }else if (intent.getAction().equals(ConnectiorManager.ACTION_CONNECTION_FAILED)){
            onConnectionFailed();

        }else if (intent.getAction().equals(ConnectiorManager.ACTION_MESSAGE_RECEIVED)){
            String message = intent.getStringExtra(ConnectiorManager.MESSAGE);
            onMessageReceived(message);
        }
    }

    @Override
    public abstract void onReplyReceived(String body);

    public abstract void onNetworkChanged(NetworkInfo info);

}
