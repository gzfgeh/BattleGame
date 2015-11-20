package com.gzfgeh.customview.threadpool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 15/11/19.
 */
public class ThreadPoolActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);

        Button btn1 = (Button) findViewById(R.id.no_limit);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThreadPoolActivity.this, NoLimitActivity.class));
            }
        });


    }
}
