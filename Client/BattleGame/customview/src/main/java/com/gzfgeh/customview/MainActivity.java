package com.gzfgeh.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gzfgeh.customview.CustomCache.CustomCacheActivity;
import com.gzfgeh.customview.CustomCircleView.CustomCircleViewActivity;
import com.gzfgeh.customview.CustomDrawable.CustomDrawableActivity;
import com.gzfgeh.customview.CustomSwipeBack.SwipeBackActivity;
import com.gzfgeh.customview.CustomTitleSwitch.CustomTitleSwitchActivity;
import com.gzfgeh.customview.CustomTouchInEvent.TouchEventInActivity;
import com.gzfgeh.customview.CustomTouchOutEvent.TouchEventOutActivity;
import com.gzfgeh.customview.threadpool.ThreadPoolActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.horizontal);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TouchEventOutActivity.class));
            }
        });

        Button btn2 = (Button) findViewById(R.id.horizontal2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TouchEventInActivity.class));
            }
        });

        Button btn3 = (Button) findViewById(R.id.circle_view);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomCircleViewActivity.class));
            }
        });

        Button btn4 = (Button) findViewById(R.id.drawable_view);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomDrawableActivity.class));
            }
        });

        Button btn5 = (Button) findViewById(R.id.thread_pool);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ThreadPoolActivity.class));
            }
        });

        Button btn6 = (Button) findViewById(R.id.cache);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomCacheActivity.class));
            }
        });

        Button btn7 = (Button) findViewById(R.id.title_switch);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomTitleSwitchActivity.class));
            }
        });

        Button btn8 = (Button) findViewById(R.id.swipe_back);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SwipeBackActivity.class));
            }
        });
    }

}
