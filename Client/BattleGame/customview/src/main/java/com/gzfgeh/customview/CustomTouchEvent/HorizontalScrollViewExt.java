package com.gzfgeh.customview.CustomTouchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by guzhenfu on 15/11/14.
 */
public class HorizontalScrollViewExt extends ViewGroup {

    public HorizontalScrollViewExt(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewExt(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewExt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int lastX = 0, lastY = 0, distanceX = 0, distanceY = 0;
        int x = (int)getX(), y = (int)getY();
        boolean isIntercept = false;

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isIntercept = false;
                break;

            case MotionEvent.ACTION_MOVE:
                distanceX = lastX - x;
                distanceY = lastY - y;
                if (Math.abs(distanceX) > Math.abs(distanceY)){
                    isIntercept = true;
                }else{
                    isIntercept = false;
                }
                break;

            case MotionEvent.ACTION_UP:
                isIntercept = false;
                break;
        }
        lastX = x; lastY = y;
        return isIntercept;
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childNum = getChildCount();
        int childSize = 0;

        for(int i=0; i<childNum; i++){
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE){
                int crtChildSize = getChildAt(i).getMeasuredWidth();
                childView.layout(childSize, 0, crtChildSize, childView.getMeasuredHeight());
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

        int childNum = getChildCount();
        if (childNum == 0){
            setMeasuredDimension(0, 0);
        }else if (widthMode == MeasureSpec.AT_MOST){
            int childWidth = getChildAt(0).
        }
    }

}
