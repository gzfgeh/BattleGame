package com.gzfgeh.happytime.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gzfgeh.happytime.APP;
import com.gzfgeh.happytime.R;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.InputStream;

/**
 * Created by guzhenfu on 2015/11/4.
 */
public class PowerImageView extends ImageView implements View.OnClickListener {
    /**
     * 播放GIF动画的关键类
     */
    private Movie mMovie;

    /**
     * 开始播放按钮图片
     */
    private Bitmap mStartButton;

    /**
     * 记录动画开始的时间
     */
    private long mMovieStart;

    /**
     * GIF图片的宽度
     */
    private int mImageWidth;

    /**
     * GIF图片的高度
     */
    private int mImageHeight;

    /**
     * 图片是否正在播放
     */
    private boolean isPlaying;

    /**
     * 是否允许自动播放
     */
    private boolean isAutoPlay;

    private String url;
    private Bitmap bitmap;
    private TypedArray a;
    private boolean mIsLoadFail;
    private int mLoadFailResId = -1;
    private Bitmap bitmapLoadFail;

    private static final int CIRCLE_WIDTH = 10;

    public void setLoadFailResId(int mLoadFailResId) {
        this.mLoadFailResId = mLoadFailResId;
        invalidate();
    }

    public void setUrl(String url) {
        this.url = url;
        APP.getAsyncHttpClientInstance().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                mMovie = Movie.decodeByteArray(bytes, 0, bytes.length);
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                if (mMovie != null) {
                    mImageWidth = bitmap.getWidth();
                    mImageHeight = bitmap.getHeight();
                    bitmap.recycle();
                    measure(MeasureSpec.makeMeasureSpec(mMovie.width(), MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(mMovie.height(), MeasureSpec.EXACTLY));
                }
                invalidate();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(APP.getContext(), "load power image view error", Toast.LENGTH_SHORT).show();
                mIsLoadFail = true;
                bitmapLoadFail = BitmapFactory.decodeResource(getResources(), mLoadFailResId);
            }
        });
    }

    public PowerImageView(Context context) {
        this(context, null);
    }

    public PowerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PowerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PowerImageView);
        isAutoPlay = a.getBoolean(R.styleable.PowerImageView_auto_play, false);
        a.recycle();

        if (!isAutoPlay) {
            mStartButton = BitmapFactory.decodeResource(getResources(),R.drawable.ic_add);
            setOnClickListener(this);
        }

        int resourceId = getResourceId(attrs);
        if (resourceId != 0) {
            // 当资源id不等于0时，就去获取该资源的流
            InputStream is = getResources().openRawResource(resourceId);
            // 使用Movie类对流进行解码
            mMovie = Movie.decodeStream(is);
            bitmap = BitmapFactory.decodeStream(is);
            if (mMovie != null) {
                // 如果返回值不等于null，就说明这是一个GIF图片，下面获取是否自动播放的属性

                mImageWidth = bitmap.getWidth();
                mImageHeight = bitmap.getHeight();
                bitmap.recycle();

            }
        }

    }

    private int getResourceId(AttributeSet attrs){
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            if(attrs.getAttributeName(i).equals("src")) {
                return attrs.getAttributeResourceValue(i, 0);
            }
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == getId()) {
            // 当用户点击图片时，开始播放GIF动画
            isPlaying = !isPlaying;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mMovie == null) {
            if (mIsLoadFail && (mLoadFailResId != -1)){
                canvas.drawBitmap(bitmapLoadFail, bitmapLoadFail.getWidth(),
                        bitmapLoadFail.getHeight(), null);
            }else{
                // mMovie等于null，说明是张普通的图片，则直接调用父类的onDraw()方法
                if (mIsLoadFail)
                    super.onDraw(canvas);
            }
        } else {

            // mMovie不等于null，说明是张GIF图片
            if (isAutoPlay) {
                // 如果允许自动播放，就调用playMovie()方法播放GIF动画
                playMovie(canvas);
                invalidate();
            } else {
                // 不允许自动播放时，判断当前图片是否正在播放
                if (isPlaying) {
                    // 正在播放就继续调用playMovie()方法，一直到动画播放结束为止
                    if (playMovie(canvas)) {
                        isPlaying = false;
                    }
                    invalidate();
                } else {
                    // 还没开始播放就只绘制GIF图片的第一帧，并绘制一个开始按钮
                    mMovie.setTime(0);
                    mMovie.draw(canvas, 0, 0);
                    int offsetW = (mImageWidth - mStartButton.getWidth()) / 2;
                    int offsetH = (mImageHeight - mStartButton.getHeight()) / 2;
                    canvas.drawBitmap(mStartButton, offsetW, offsetH, null);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mMovie != null){
            setMeasuredDimension(mImageWidth, mImageHeight);
        }
//        ViewGroup.LayoutParams lp= getLayoutParams();
//        boolean isWidMat=lp.width== ViewGroup.LayoutParams.MATCH_PARENT;
//        boolean isHeiMat=lp.height== ViewGroup.LayoutParams.MATCH_PARENT;
//        boolean isWidWra=lp.width== ViewGroup.LayoutParams.WRAP_CONTENT;
//        boolean isHeiWra=lp.height== ViewGroup.LayoutParams.WRAP_CONTENT;
//        if(isWidMat&&isHeiMat){
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            return;
//        }
//
//        if (mMovie != null) {
//            requestLayout();
//            setMeasuredDimension(isWidMat?MeasureSpec.getSize(widthMeasureSpec):isWidWra?mMovie.width():lp.width,
//                    isHeiMat?MeasureSpec.getSize(heightMeasureSpec):isHeiWra?mMovie.height():lp.height);
//        } else {
//            setMeasuredDimension(isWidMat?MeasureSpec.getSize(widthMeasureSpec):isWidWra?0:lp.width,
//                    isHeiMat?MeasureSpec.getSize(heightMeasureSpec):isHeiWra?0:lp.height);
//        }
    }

    /**
     * 开始播放GIF动画，播放完成返回true，未完成返回false。
     *
     * @param canvas
     * @return 播放完成返回true，未完成返回false。
     */
    private boolean playMovie(Canvas canvas) {
        long now = SystemClock.uptimeMillis();
        if (mMovieStart == 0) {
            mMovieStart = now;
        }
        int duration = mMovie.duration();
        if (duration == 0) {
            duration = 1000;
        }
        int relTime = (int) ((now - mMovieStart) % duration);
        mMovie.setTime(relTime);
        mMovie.draw(canvas, 0, 0);
        if ((now - mMovieStart) >= duration) {
            mMovieStart = 0;
            return true;
        }
        return false;
    }

}
