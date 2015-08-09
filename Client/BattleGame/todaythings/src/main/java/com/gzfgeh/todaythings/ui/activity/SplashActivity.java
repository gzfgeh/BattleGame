package com.gzfgeh.todaythings.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.gzfgeh.todaythings.APP;
import com.gzfgeh.todaythings.R;
import com.gzfgeh.todaythings.utils.ShareUtils;

import java.util.Random;

/**
 * Created by guzhenfu on 15/8/8.
 */
public class SplashActivity extends BaseActivity{
    private static final String USER = "USER";
    private String userSentence = null;
    private TextView sentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSentence();
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        sentence = (TextView) findViewById(R.id.sentence);
        if (!TextUtils.isEmpty(userSentence))
            sentence.setText(userSentence);
    }

    private void getSentence(){
        boolean once = ShareUtils.getValue(USER, false);
        if (once){
            userSentence = ShareUtils.getValue(APP.USER_SENTENCE, null);
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
}
