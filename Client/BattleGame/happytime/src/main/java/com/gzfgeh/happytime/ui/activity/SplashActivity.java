package com.gzfgeh.happytime.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.TextView;

import com.gzfgeh.happytime.APP;
import com.gzfgeh.happytime.Global;
import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.utils.ShareUtils;

import java.util.Random;

/**
 * Created by guzhenfu on 15/8/8.
 */
public class SplashActivity extends BaseActivity{
    private static final int TIME = 1000;
    private String userSentence = null;
    private TextView sentence;

    private boolean isActive = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSentence();
        super.onCreate(savedInstanceState);
        initView();

        Thread splashThread = new Thread(){
            @Override
            public void run() {
                int waited = 0;

                try {
                    while (isActive && waited<TIME) {
                        sleep(100);
                        if (isActive)
                            waited += 100;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    finish();
                    if (!ShareUtils.getValue(Global.APP_ONCE, false))
                        startActivity(new Intent(SplashActivity.this, LaunchActivity.class));
                    else
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        };
        splashThread.start();
    }

    private void initView() {
        sentence = (TextView) findViewById(R.id.sentence);
        if (!TextUtils.isEmpty(userSentence))
            sentence.setText(userSentence);
    }

    private void getSentence(){
        boolean once = ShareUtils.getValue(Global.USER_SET, false);
        if (once){
            userSentence = ShareUtils.getValue(Global.USER_SENTENCE, null);
        }else {
            int num = new Random().nextInt(10) + 1;
            String s = String.valueOf(num);
            userSentence = ShareUtils.getValue(s, null);
        }
    }

    @Override
    protected void initToolBar() {
        return;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isActive = false;
        }
        return true;
    }
}
