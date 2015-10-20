package com.gzfgeh.happytime.ui.activity;

import android.os.Bundle;

import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.module.swipeback.SwipeBackActivity;

/**
 * Created by guzhenfu on 15/10/19.
 */
public class BannerActivityOne extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_one);

        setEdgeFromLeft();
    }
}
