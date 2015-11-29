package com.gzfgeh.customview.CustomCache;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by guzhenfu on 15/11/28.
 */
public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
