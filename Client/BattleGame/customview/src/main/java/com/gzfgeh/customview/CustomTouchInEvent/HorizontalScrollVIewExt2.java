package com.gzfgeh.customview.CustomTouchInEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by guzhenfu on 15/11/15.
 */
public class HorizontalScrollVIewExt2 extends ViewGroup {
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;
    private int downX = 0;
    private int mChildIndex = 0, mChildWidth = 0, mChildNum = 0;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;


    public HorizontalScrollVIewExt2(Context context) {
        this(context, null);
    }

    public HorizontalScrollVIewExt2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public HorizontalScrollVIewExt2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mVelocityTracker = VelocityTracker.obtain();
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int)ev.getX(), y = (int)ev.getY();
        boolean isIntercept = false;

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastXIntercept = 0;
                mLastYIntercept = 0;
                isIntercept = false;
                break;

            case MotionEvent.ACTION_MOVE:
                isIntercept = true;
                break;

            case MotionEvent.ACTION_UP:
                isIntercept = true;
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;

        return isIntercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX(), y = (int)event.getY();
        mVelocityTracker.addMovement(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int distanceX = x - mLastX;
                scrollBy(-distanceX, 0);
                Log.i("TAG", "onTouchEvent------" + distanceX);
                break;

            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > 50){
                    mChildIndex = xVelocity>0 ? mChildIndex - 1 : mChildIndex +1;
                }else{
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildNum-1, mChildIndex));
                smoothScrollBy(mChildIndex * mChildWidth - scrollX, 0);
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;

        return true;
    }

    private void smoothScrollBy(int x, int y) {
        mScroller.startScroll(getScrollX(), 0, x, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mChildNum = getChildCount();
        int childSize = 0;

        for(int i=0; i<mChildNum; i++){
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE){
                int crtChildSize = getChildAt(i).getMeasuredWidth();
                mChildWidth = crtChildSize;
                childView.layout(childSize, 0, childSize + crtChildSize, childView.getMeasuredHeight());
                childSize += crtChildSize;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidth, measureHeight;

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childNum = getChildCount();
        if (childNum == 0){
            setMeasuredDimension(0, 0);
        }else if (widthMode == MeasureSpec.AT_MOST){
            measureWidth = getChildAt(0).getMeasuredWidth() * childNum;
            setMeasuredDimension(measureWidth, heightSize);
        }else if (heightMode == MeasureSpec.AT_MOST){
            measureHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(widthSize, measureHeight);
        }else{
            measureHeight = getChildAt(0).getMeasuredHeight();
            measureWidth = getChildAt(0).getMeasuredWidth() * childNum;
            setMeasuredDimension(measureWidth, measureHeight);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }
}
