package com.gzfgeh.battlegame.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.gzfgeh.battlegame.R;

/**
 * Created by guzhenf on 6/27/2015.
 */
public class MainDisplay extends Activity{
    private Button btnBegin;
    private View vPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏

        setContentView(R.layout.main_display);

        vPlay = findViewById(R.id.play);
        btnBegin = (Button) findViewById(R.id.begin);
        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBegin.setVisibility(View.GONE);
                vPlay.setVisibility(View.VISIBLE);
            }
        });
    }
}
