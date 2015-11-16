package com.gzfgeh.customview.CustomDrawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by guzhenfu on 15/11/16.
 */
public class CustomDrawable extends Drawable {
    private Paint mPaint;

    public CustomDrawable(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect scale = getBounds();
        float width = scale.exactCenterX();
        float height = scale.exactCenterY();
        canvas.drawCircle(width, height, Math.min(width, height), mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
