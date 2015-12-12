package com.gzfgeh.customview.CustomSwipeBack;

import android.os.Bundle;

import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 15/12/9.
 */
public class SwipeBackActivityDemo extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_swipeback;
    }
}
