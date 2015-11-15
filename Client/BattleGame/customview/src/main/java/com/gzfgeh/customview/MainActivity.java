package com.gzfgeh.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.gzfgeh.customview.CustomCircleView.CustomCircleViewActivity;
import com.gzfgeh.customview.CustomTouchInEvent.TouchEventInActivity;
import com.gzfgeh.customview.CustomTouchOutEvent.TouchEventOutActivity;

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

    }

}
