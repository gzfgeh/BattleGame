package com.gzfgeh.happytime.ui.activity;

import android.os.Bundle;

import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.module.swipeback.SwipeBackActivity;
import com.gzfgeh.happytime.widget.PowerImageView;

/**
 * Created by guzhenfu on 15/10/19.
 */
public class BannerActivityOne extends BaseActivity {
    private PowerImageView powerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        powerImageView = (PowerImageView) findViewById(R.id.power_image_view);
        powerImageView.setUrl("http://cdn.duitang.com/uploads/item/201311/20/20131120213622_mJCUy.thumb.600_0.gif");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_banner_one;
    }
}
