package com.gzfgeh.battlegame.ui;

import android.app.Activity;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.gzfgeh.battlegame.socket.MinaListenerManager;
import com.gzfgeh.battlegame.socket.OnMinaMessageListener;

/**
 * Created by guzhenf on 6/28/2015.
 */
public abstract class BaseActivity extends Activity implements OnMinaMessageListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MinaListenerManager.registerMessageListener(this, this);
    }

    @Override
    public void finish() {
        super.finish();
        MinaListenerManager.removeMessageListener(this);
    }

    @Override
    public void onConnectionClosed() {}

    @Override
    public void onConnectionFailed() {}

    @Override
    public void onConnectionSucceed() {}

    @Override
    public void onConnectionStatus(boolean  isConnected){}

    @Override
    public void onReplyReceived(String reply) {}

    @Override
    public void onMessageReceived(String arg0) {}

    @Override
    public void onNetworkChanged(NetworkInfo info){};
}
