package com.gzfgeh.battlegame.socket;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.gzfgeh.battlegame.handler.MinaClientHandler;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by guzhenf on 6/28/2015.
 */
public class ConnectiorManager {
    private NioSocketConnector connector;
    private ConnectFuture connectFuture;
    private IoSession session;

    private Context context;
    private static ConnectiorManager manager;
    private ExecutorService executor;

    public static final String ACTION_CONNECTION_FAILED = "CONNECTION_FAILED";

    private ConnectiorManager(Context context){
        executor = Executors.newFixedThreadPool(3);

        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(10 * 1000);
        connector.getSessionConfig().setBothIdleTime(180);
        connector.getSessionConfig().setKeepAlive(true);
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(
                Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue())));

        connector.setHandler(new MinaClientHandler());
    }

    public synchronized static ConnectiorManager getManager(Context context) {
        if (manager == null) {
            manager = new ConnectiorManager(context);
        }
        return manager;
    }

    private synchronized void  syncConnection(final String host,final int port) {
        try {
            if(isConnected()){
                return ;
            }

            InetSocketAddress remoteSocketAddress = new InetSocketAddress(host, port);
            connectFuture = connector.connect(remoteSocketAddress);
            connectFuture.awaitUninterruptibly();
            session = connectFuture.getSession();

        } catch (Exception e) {
            Intent intent = new Intent();
            intent.setAction(ACTION_CONNECTION_FAILED);
            intent.putExtra("exception", e);
            context.sendBroadcast(intent);
        }

    }

    public  void connect(final String host, final int port) {

        if (!netWorkAvailable(context)) {

            Intent intent = new Intent();
            intent.setAction(ACTION_CONNECTION_FAILED);
            intent.putExtra("exception", new Exception());
            context.sendBroadcast(intent);
            return;
        }

        Future<?> future = executor.submit(new Runnable() {
            @Override
            public void run() {
                syncConnection(host, port);
            }
        });
        try {
            if(future.get()!=null){
                connect(host,port);
            }
        } catch (Exception e) {

            connect(host,port);
            e.printStackTrace();
        }
    }

    public void destroy() {
        if (manager.session != null) {
            manager.session.close(false);
            manager.session.removeAttribute("account");
        }

        if (manager.connector != null && !manager.connector.isDisposed()) {
            manager.connector.dispose();
        }
        manager = null;
    }

    public boolean isConnected() {
        if (session == null || connector == null) {
            return false;
        }
        return session.isConnected() ;
    }

    public static boolean netWorkAvailable(Context context) {
        try {
            ConnectivityManager nw = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = nw.getActiveNetworkInfo();
            return networkInfo != null;
        } catch (Exception e) {}

        return false;
    }

    public void closeSession(){
        if(session!=null)
        {
            session.close(false);
        }
    }

}
