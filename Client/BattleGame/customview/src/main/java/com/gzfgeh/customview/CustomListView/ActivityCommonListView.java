package com.gzfgeh.customview.CustomListView;

import android.os.Bundle;
import android.widget.ListView;

import com.gzfgeh.customview.CustomSwipeBack.SwipeBackActivity;
import com.gzfgeh.customview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by guzhenfu on 15/12/13.
 */
public class ActivityCommonListView extends SwipeBackActivity {
    private ListView mListView;
    private List<String> mDatas = new ArrayList<>(Arrays.asList("Hello",
            "World", "Welcome"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = (ListView) findViewById(R.id.id_lv_main);
        mListView.setAdapter(new ListViewAdapter(this, mDatas, R.layout.list_view_item_layout));
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_custom_list_view;
    }
}
