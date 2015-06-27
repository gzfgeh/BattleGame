package com.gzfgeh.battlegame.thread;

import android.util.Log;

import com.gzfgeh.battlegame.handler.MinaClientHandler;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by guzhenf on 6/27/2015.
 */
public class MinaThread extends Thread {
    private String ip;
    private int port;
    private IoSession session = null;

    public MinaThread(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        Log.i("TAG", "client connect start...");
        IoConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(30000);
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(
                Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue())));
        connector.setHandler(new MinaClientHandler());

        try{
            ConnectFuture future = connector.connect(new InetSocketAddress(ip,port));
            future.awaitUninterruptibly();
            session = future.getSession();
            session.write("start");
        }catch (Exception e){
            Log.i("TAG", "client connect exception----");
        }

        session.getCloseFuture().awaitUninterruptibly();
        Log.i("TAG", "client discut");
        connector.dispose();
        super.run();
    }
}
