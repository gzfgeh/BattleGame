package com.gzfgeh.customview.CustomSwipeBack;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
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
        getWindow().setBackgroundDrawable(new ColorDrawable(0xff));
        getWindow().getDecorView().setBackgroundDrawable(null);
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
