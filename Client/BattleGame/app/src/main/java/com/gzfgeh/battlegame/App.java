package com.gzfgeh.battlegame;

import android.app.Application;
import android.content.Intent;

import com.gzfgeh.battlegame.service.MinaService;

/**
 * Created by guzhenf on 6/28/2015.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(getApplicationContext(), MinaService.class);
        startService(intent);
    }
}
