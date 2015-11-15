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
        super(context, attrs, 0);
    }
    public CustomCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width/2, height/2);
        canvas.drawCircle(width/2, height/2, radius, mPaint);
    }
}
