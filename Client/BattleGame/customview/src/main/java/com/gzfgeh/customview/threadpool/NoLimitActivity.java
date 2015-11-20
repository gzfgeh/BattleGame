package com.gzfgeh.customview.threadpool;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 15/11/19.
 */
public class NoLimitActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_limit_thread_pool);

        ListView listView = (ListView) findViewById(R.id.task_list);
    }
}
