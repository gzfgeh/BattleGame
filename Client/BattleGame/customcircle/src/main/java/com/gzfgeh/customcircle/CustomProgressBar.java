package com.gzfgeh.customcircle;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by guzhenfu on 15/9/19.
 */
public class CustomProgressBar extends View {
    private int firstColor;
    private int secondColor;
    private int textSize;
    private int textColor;
    private int mCenterTextSize;
    private int mCenterTextColor;
    private int circleWidth;
    private boolean textIsDisplay;
    private Paint paint;
    private int progress;
    private int startProgress;
    private int maxProgress;
    private Bitmap point;
    boolean mOnce = true;
    private Context mContext;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        firstColor = array.getColor(R.styleable.CircleProgressBar_firstColor, Color.BLUE);
        secondColor = array.getColor(R.styleable.CircleProgressBar_secondColor, Color.BLACK);
        textColor = array.getColor(R.styleable.CircleProgressBar_textColor, Color.WHITE);
        textSize = array.getDimensionPixelSize(R.styleable.CircleProgressBar_textSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        circleWidth = array.getDimensionPixelSize(R.styleable.CircleProgressBar_roundWidth, (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        textIsDisplay = array.getBoolean(R.styleable.CircleProgressBar_textDisplay, false);
        progress = array.getInt(R.styleable.CircleProgressBar_progress, 0);
        startProgress = array.getInt(R.styleable.CircleProgressBar_startProgress, 0);
        maxProgress = array.getInt(R.styleable.CircleProgressBar_maxProgress, 360);
        point = BitmapFactory.decodeResource(getResources(), array.getResourceId(
                R.styleable.CircleProgressBar_point, R.mipmap.ic_launcher));
        mCenterTextSize = array.getDimensionPixelSize(R.styleable.CircleProgressBar_centerTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        mCenterTextColor = array.getColor(R.styleable.CircleProgressBar_centerTextColor, Color.WHITE);
        array.recycle();

        paint = new Paint();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progress != maxProgress){
                    progress++;
                    postInvalidate();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth()/2;
        int radius = center - circleWidth/2;
        paint.setStrokeWidth(circleWidth);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(firstColor);
        canvas.drawCircle(center, center, radius, paint);
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        paint.setColor(secondColor);
        canvas.drawArc(rectF, -startProgress, 270, false, paint);


        if (point.getWidth() < Math.sqrt(2) * radius){
            rectF.left = (int) (center - point.getWidth() * 1.0f / 2);
            rectF.top = (int) (center - point.getHeight() * 1.0f / 2);

            rectF.right = (int) (rectF.left + point.getWidth());
            rectF.bottom = (int) (rectF.top + point.getHeight());
        }
        if (mOnce){
            point = rotateBitmap(point, 45);
            mOnce = false;
        }
        point = rotateBitmap(point, 1);
        canvas.drawBitmap(point, null, rectF, paint);

        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        int percent = (int)((float)progress/(float)maxProgress * 100);
        float textWidth = paint.measureText(percent+"%");
        if (textIsDisplay) {
            canvas.drawText(percent + "%", center - textWidth / 2, center - textSize, paint);
            paint.setColor(mCenterTextColor);
            paint.setTextSize(mCenterTextSize);
            textWidth = paint.measureText("优");
            canvas.drawText("优", center-textWidth/2, center+textSize/2, paint);
        }

    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }
}
