
package com.gzfgeh.customview.CustomSwipeBack;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.gzfgeh.customview.R;
import com.gzfgeh.customview.threadpool.BaseActivity;

public class SwipeBackActivity extends BaseActivity {

    private SwipeBackLayout mSwipeBackLayout;

    private boolean mOverrideExitAnimation = true;

    private boolean mIsFinishing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = new SwipeBackLayout(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.toolbar_layout;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this);
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v != null)
            return v;
        return mSwipeBackLayout.findViewById(id);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public void setSwipeBackEnable(boolean enable) {
        //mSwipeBackLayout.setEnableGesture(enable);
    }

    /**
     *  slide from left
     */
    public void setEdgeFromLeft(){
//        final int edgeFlag = SwipeBackLayout.EDGE_LEFT;
//        mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlag);
    }
    
    /**
     * Override Exit Animation
     * 
     * @param override
     */
    public void setOverrideExitAniamtion(boolean override) {
        mOverrideExitAnimation = override;
    }

    /**
     * Scroll out contentView and finish the activity
     */
    public void scrollToFinishActivity() {
        mSwipeBackLayout.scrollToFinishActivity();
    }

    @Override
    public void finish() {
        if (mOverrideExitAnimation && !mIsFinishing) {
            scrollToFinishActivity();
            mIsFinishing = true;
            return;
        }
        mIsFinishing = false;
        super.finish();
    }
}
