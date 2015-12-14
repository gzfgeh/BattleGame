package com.gzfgeh.customview.CustomSwipeBack;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gzfgeh.customview.Utils.LogUtils;
import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 15/12/9.
 */
public class SwipeBackLayout extends FrameLayout {
    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;
    private static final int MIN_FLING_VELOCITY = 400; // dips per second

    private int mScrimColor = DEFAULT_SCRIM_COLOR;
    private ViewDragHelper mDragHelper;
    private View mContentView;
    private Activity mActivity;
    private Rect mTmpRect = new Rect();
    private Drawable mShadowLeft;
    private float mScrollPercent;
    private float mScrimOpacity;
    private int mContentLeft;
    private boolean mInLayout;


    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;

        mDragHelper = ViewDragHelper.create(this, new ViewDragCallBack());
        mDragHelper.setMinVelocity(minVel);
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        setShadow(R.drawable.shadow_left, ViewDragHelper.EDGE_LEFT);
    }

    public void attachToActivity(Activity activity){
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup linearLayout = (ViewGroup) decorView.getChildAt(0);
        linearLayout.setBackgroundResource(background);
        decorView.removeView(linearLayout);
        addView(linearLayout);
        setContentView(linearLayout);
        decorView.addView(this);
    }

    private void setContentView(View view){
        mContentView = view;
    }

    public void scrollToFinishActivity() {
        final int childWidth = mContentView.getWidth();

        int left = childWidth + mShadowLeft.getIntrinsicWidth() + 20;
        mDragHelper.smoothSlideViewTo(mContentView, left, 0);
        invalidate();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        drawShadow(canvas, child);
        boolean ret = super.drawChild(canvas, child, drawingTime);
        if ((mScrimOpacity > 0) &&
            (mDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE))
            drawScrim(canvas, child);
        LogUtils.i("TAG", "mScrimOpacity :" + mScrimOpacity + "-------ret ：" + ret);
        return ret;
    }

    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * mScrimOpacity);
        final int color = alpha << 24 | (mScrimColor & 0xffffff);

        canvas.clipRect(0, 0, child.getLeft(), getHeight());
        canvas.drawColor(color);
        LogUtils.i("TAG", "child.getLeft() :" + child.getLeft() +
                        "---------" + getHeight());
    }

    private void drawShadow(Canvas canvas, View child) {
        final Rect childRect = mTmpRect;
        child.getHitRect(childRect);
        mShadowLeft.setBounds(childRect.left - mShadowLeft.getIntrinsicWidth(),
                childRect.top, childRect.left, childRect.bottom);
        mShadowLeft.draw(canvas);
    }

    public void setShadow(Drawable shadow, int edgeFlag) {
        mShadowLeft = shadow;
        invalidate();
    }

    public void setShadow(int resId, int edgeFlag) {
        setShadow(getResources().getDrawable(resId), edgeFlag);
    }

    @Override
    public void computeScroll() {
        mScrimOpacity = 1 - mScrollPercent;
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public void requestLayout() {
        if (!mInLayout)
            super.requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mInLayout = true;
        mContentView.layout(mContentLeft, 0,
                mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());
        mInLayout = false;
    }

    /*
     *让ViewDrawerHelper处理 必须的
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    /*
     *让ViewDrawerHelper处理 必须的
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    private class ViewDragCallBack extends ViewDragHelper.Callback{
        /*
         *那些View可以滑动 返回true
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /*
         *水平方向滑动设置
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return Math.min(child.getWidth(), Math.max(left, 0));
        }

        /*
         *竖直方向滑动设置
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        /*
         *View 释放后返回的位置
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (mScrollPercent < 0.3){
                mDragHelper.settleCapturedViewAt(0, 0);
            }else{
                mDragHelper.settleCapturedViewAt(mContentView.getWidth()
                        + mShadowLeft.getIntrinsicWidth(), 0);
            }
            invalidate();
        }

        /*
         *下面两个：如果子View中有Clickable控件，就必须返回大于0的值
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 1;
        }

        /*
         *View 滑动过程中动态计算和改变
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mScrollPercent = Math.abs((float) left
                    / (mContentView.getWidth() + mShadowLeft.getIntrinsicWidth()));
            mContentLeft = left;
            invalidate();
            LogUtils.i("TAG", "mScrollPercent :" + mScrollPercent + "------");
            if (mScrollPercent >= 1.0f) {
                mActivity.finish();
                LogUtils.i("TAG", "mScrollPercent :-------" + mScrollPercent + "------");
            }
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (state == ViewDragHelper.STATE_IDLE) {
                ViewCompat.setLayerType(SwipeBackLayout.this, ViewCompat.LAYER_TYPE_NONE, null);
            } else if (state == ViewDragHelper.STATE_SETTLING) {
                ViewCompat.setLayerType(SwipeBackLayout.this, ViewCompat.LAYER_TYPE_HARDWARE, null);
            }
        }

    }
}
