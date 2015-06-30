package com.gzfgeh.battlegame.handler;

import android.content.Context;
import android.content.Intent;

import com.gzfgeh.battlegame.socket.ConnectiorManager;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by guzhenf on 6/28/2015.
 */
public class MinaClientHandler extends IoHandlerAdapter {
    private Context context;

    public MinaClientHandler(Context context){
        this.context = context;
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        Intent intent = new Intent();
        intent.setAction(ConnectiorManager.ACTION_CONNECTION_SUCCESS);
        context.sendBroadcast(intent);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        session.getConfig().setBothIdleTime(180);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
                 throws Exception {
        Intent intent = new Intent();
        intent.setAction(ConnectiorManager.ACTION_CONNECTION_FAILED);
        context.sendBroadcast(intent);
    }

    @Override
    public void messageReceived(IoSession session, Object message)
         throws Exception {
         String msg = message.toString();
        Intent intent = new Intent();
        intent.setAction(ConnectiorManager.ACTION_MESSAGE_RECEIVED);
        intent.putExtra(ConnectiorManager.MESSAGE, msg);
        context.sendBroadcast(intent);
     }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
         // TODO Auto-generated method stub
        super.messageSent(session, message);
     }
}
