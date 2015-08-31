package com.gzfgeh.battlegame.socket;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.gzfgeh.battlegame.handler.MinaClientHandler;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
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
import java.util.concurrent.TimeUnit;

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

    public static final String ACTION_CONNECTION_SUCCESS    = "ACTION_CONNECTION_SUCCESS";
    public static final String ACTION_CONNECTION_FAILED     = "ACTION_CONNECTION_FAILED";
    public static final String ACTION_CONNECTION            = "ACTION_CONNECTION";
    public static final String ACTION_DISCONNECTION         = "ACTION_DISSENDREQUEST";
    public static final String ACTION_SEND_MESSAGE          = "ACTION_SEND_MESSAGE";
    public static final String ACTION_SEND_FAIL             = "ACTION_SEND_FAIL";
    public static final String MESSAGE                      = "MESSAGE";
    public static final String ACTION_MESSAGE_RECEIVED      = "ACTION_MESSAGE_RECEIVED";

    private ConnectiorManager(Context context){
        this.context = context;
        executor = Executors.newFixedThreadPool(3);

        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(10 * 1000);
        connector.getSessionConfig().setBothIdleTime(180);
        connector.getSessionConfig().setKeepAlive(true);
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("mycoder", new ProtocolCodecFilter(
                new MyTextLineFactory(Charset.forName("UTF-8"))));

        connector.setHandler(new MinaClientHandler(context));
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
            if (session == null){
                Intent intent = new Intent();
                intent.setAction(ACTION_CONNECTION_FAILED);
                context.sendBroadcast(intent);
            }
        } catch (Exception e) {
            Intent intent = new Intent();
            intent.setAction(ACTION_CONNECTION_FAILED);
            intent.putExtra("exception", e);
            context.sendBroadcast(intent);
        }

    }

    public void connect(final String host, final int port) {

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

    public void sendMessage(final String message){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (session != null && session.isConnected()){
                    WriteFuture wf = session.write(message);
                    wf.awaitUninterruptibly(5, TimeUnit.SECONDS);
                    if (!wf.isWritten()){
                        Intent intent = new Intent();
                        intent.setAction(ACTION_SEND_FAIL);
                        intent.putExtra("exception", new Exception());
                        intent.putExtra("sendMessage", message);
                        context.sendBroadcast(intent);
                    }
                }else{
                    Intent intent = new Intent();
                    intent.setAction(ACTION_SEND_FAIL);
                    intent.putExtra("exception", new Exception());
                    intent.putExtra("sendMessage", message);
                    context.sendBroadcast(intent);
                }
            }
        });
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
