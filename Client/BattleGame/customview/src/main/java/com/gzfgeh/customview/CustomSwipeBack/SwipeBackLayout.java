package com.gzfgeh.customview.CustomSwipeBack;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by guzhenfu on 15/12/6.
 */
public class SwipeBackLayout extends LinearLayout {
    private ViewDragHelper mDragger;
    private View mDragView;
    private View AutoBackView;
    private View mEdgeTrackerView;
    private Point mAutoBackOriginPos = new Point();

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mDragView || child == AutoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int rightBound = getWidth() - mDragView.getWidth() - getPaddingLeft();
                int leftBound = getPaddingLeft();
                int newLeft = Math.min(Math.max(leftBound, left), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == AutoBackView){
                    mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mDragger.captureChildView(mEdgeTrackerView, pointerId);
            }
        });
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        AutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackOriginPos.x = AutoBackView.getLeft();
        mAutoBackOriginPos.y = AutoBackView.getTop();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragger.continueSettling(true))
            invalidate();
    }
}
