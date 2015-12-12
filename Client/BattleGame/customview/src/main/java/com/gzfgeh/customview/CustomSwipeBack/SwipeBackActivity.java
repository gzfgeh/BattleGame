package com.gzfgeh.customview.CustomSwipeBack;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 15/12/6.
 */
public class SwipeBackActivity extends FragmentActivity {
    private SwipeBackLayout layout;
    private boolean isFinishing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().getDecorView().setBackgroundDrawable(null);
        layout = new SwipeBackLayout(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        layout.attachToActivity(this);
    }

    @Override
    public void finish() {
        if (!isFinishing) {
            layout.scrollToFinishActivity();
            isFinishing = true;
            return;
        }
        isFinishing = false;
        super.finish();
    }
}
