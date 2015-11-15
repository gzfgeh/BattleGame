package com.gzfgeh.customview.CustomCircleView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by guzhenfu on 15/11/15.
 */
public class CustomCircleView extends View {
    private Paint mPaint;

    public CustomCircleView(Context context) {
        this(context, null);
    }
    public CustomCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CustomCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200, 200);
        }else if (heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize, 200);
        }else if (widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200, heightSize);
        }else{

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        int width = getWidth() - left - right;
        int height = getHeight() - top - bottom;
        int radius = Math.min(width, height)/2;
        canvas.drawCircle(left + width/2, top + height/2, radius, mPaint);
    }
}
