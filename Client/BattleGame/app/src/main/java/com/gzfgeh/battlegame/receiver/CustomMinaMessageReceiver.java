package com.gzfgeh.battlegame.receiver;

import android.net.NetworkInfo;

import com.gzfgeh.battlegame.socket.MinaListenerManager;

/**
 * Created by guzhenf on 6/29/2015.
 */
public class CustomMinaMessageReceiver extends MinaEventListenerReceiver {

    @Override
    public void onMessageReceived(String message) {
        for (int i=0; i< MinaListenerManager.getListeners().size(); i++){
            MinaListenerManager.getListeners().get(i).onMessageReceived(message);
        }
    }

    @Override
    public void onReplyReceived(String body) {

    }

    @Override
    public void onNetworkChanged(NetworkInfo info) {

    }

    @Override
    public void onConnectionStatus(boolean isConnected) {

    }

    @Override
    public void onConnectionSucceed() {
        for (int i=0; i< MinaListenerManager.getListeners().size(); i++){
            MinaListenerManager.getListeners().get(i).onConnectionSucceed();
        }
    }

    @Override
    public void onConnectionClosed() {

    }
}
