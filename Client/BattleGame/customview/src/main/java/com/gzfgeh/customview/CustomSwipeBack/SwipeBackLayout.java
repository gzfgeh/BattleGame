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
    private int mContentLeft;


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
        decorView.addView(this);
        setContentView(linearLayout);
    }

    private void setContentView(View view){
        mContentView = view;
    }

    public void scrollToFinishActivity() {
        final int childWidth = mContentView.getWidth();
        final int childHeight = mContentView.getHeight();

        int left = 0, top = 0;
        left = childWidth;
        mDragHelper.smoothSlideViewTo(mContentView, left, top);
        invalidate();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        drawShadow(canvas, child);
        boolean ret = super.drawChild(canvas, child, drawingTime);
        drawScrim(canvas, child);
        return ret;
    }

    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * (1 - mScrollPercent));
        final int color = alpha << 24 | (mScrimColor & 0xffffff);

        canvas.clipRect(0, 0, child.getLeft(), getHeight());
        canvas.drawColor(color);
    }

    private void drawShadow(Canvas canvas, View child) {
        final Rect childRect = mTmpRect;
        child.getHitRect(childRect);
        mShadowLeft.setBounds(childRect.width() - mShadowLeft.getIntrinsicWidth(),
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
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mContentView.layout(mContentLeft, 0,
                mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    private class ViewDragCallBack extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return Math.min(child.getWidth(), Math.max(left, 0));
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            mDragHelper.settleCapturedViewAt(0, 0);
            invalidate();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 1;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mScrollPercent = Math.abs((float) left
                    / (mContentView.getWidth() + mShadowLeft.getIntrinsicWidth()));
            mContentLeft = left;
            invalidate();
        }
    }
}
