package com.gzfgeh.battlegame.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.gzfgeh.battlegame.thread.MinaThread;

/**
 * Created by guzhenf on 6/28/2015.
 */
public class MinaService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new MinaThread("dd", 8989).run();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
