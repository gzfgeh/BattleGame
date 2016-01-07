package com.gzfgeh.happytime.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gzfgeh.happytime.R;

/**
 * Created by guzhenf on 6/25/2015.
 */
public class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;

    protected void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
