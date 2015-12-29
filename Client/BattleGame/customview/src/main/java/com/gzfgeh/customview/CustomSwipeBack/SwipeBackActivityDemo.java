package com.gzfgeh.customview.CustomSwipeBack;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 15/12/9.
 */
public class SwipeBackActivityDemo extends SwipeBackActivity {
    String[] s = {"1", "2", "3", "4", "5",
                    "1", "2", "3", "4", "5",
                        "1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                s));
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_swipeback;
    }
}
