package com.gzfgeh.battlegame.socket;

import android.net.NetworkInfo;
import android.os.Message;

/**
 * Created by guzhenf on 6/28/2015.
 */
public interface OnMinaMessageListener {
    /**
     * 当收到服务端推送过来的消息时调用
     * @param message
     */
    public abstract void onMessageReceived(Message message);

    /**
     * 当调用CIMPushManager.sendRequest()向服务端发送请求，获得相应时调用
     * @param replybody
     */
    public abstract void onReplyReceived(String replybody);

    /**
     * 当手机网络发生变化时调用
     * @param networkinfo
     */
    public abstract void onNetworkChanged(NetworkInfo networkinfo);

    /**
     * 获取到是否连接到服务端
     * 通过调用CIMPushManager.detectIsConnected()来异步获取
     *
     */
    public abstract void onConnectionStatus(boolean  isConnected);

    /**
     * 连接服务端成功
     */
    public abstract void onConnectionSucceed();


    /**
     * 连接断开
     */
    public abstract void onConnectionClosed();
}
