package com.gzfgeh.customview.CustomSwipeBack;

import android.app.Activity;
import android.os.Bundle;

import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 15/12/6.
 */
public class SwipeBackActivity extends Activity {
    private SwipeBackLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_swipeback);
        layout = new SwipeBackLayout(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        layout.attachToActivity(this);
    }

    public void scrollToFinishActivity() {
        layout.scrollToFinishActivity();
    }

    @Override
    public void finish() {
        super.finish();
        layout.scrollToFinishActivity();
    }
}
