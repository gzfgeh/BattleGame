package com.gzfgeh.battlegame.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gzfgeh.battlegame.R;

/**
 * Created by guzhenf on 7/3/2015.
 */
public class TopBar extends RelativeLayout {
    private TextView left, center, right;
    private OnLeftAndRightClickListener listener;

    public OnLeftAndRightClickListener getListener() {
        return listener;
    }

    public void setListener(OnLeftAndRightClickListener listener) {
        this.listener = listener;
    }

    //设置左边按钮的可见性
    public void setLeftVisibility(boolean flag){
        if(flag)
            left.setVisibility(VISIBLE);
        else
            left.setVisibility(INVISIBLE);
    }
    //设置右边按钮的可见性
    public void setRightVisibility(boolean flag){
        if(flag)
            right.setVisibility(VISIBLE);
        else
            right.setVisibility(INVISIBLE);
    }

    public void setCenterVisibility(boolean flag){
        if(flag)
            center.setVisibility(VISIBLE);
        else
            center.setVisibility(INVISIBLE);
    }

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.title_bar, this);
        left = (TextView) findViewById(R.id.tv_left);
        center = (TextView) findViewById(R.id.tv_center);
        right = (TextView) findViewById(R.id.tv_right);

        left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onLeftButtonClick();
            }
        });

        right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onRightButtonClick();
            }
        });

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        int leftBg      = array.getResourceId(R.styleable.TopBar_leftBg, R.drawable.top_back_left_selector);
        int leftSize    = array.getDimensionPixelSize(R.styleable.TopBar_leftSize, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 4,getResources().getDisplayMetrics()));
        String leftText = array.getString(R.styleable.TopBar_leftText);
        boolean leftVisibility = array.getBoolean(R.styleable.TopBar_leftVisibility, true);

        int rigthBg     = array.getResourceId(R.styleable.TopBar_rightBg, R.drawable.common_button_blue);
        String rightText= array.getString(R.styleable.TopBar_rightText);
        int rightSize   = array.getDimensionPixelSize(R.styleable.TopBar_rightSize, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 4, getResources().getDisplayMetrics()));
        boolean rightVisibility = array.getBoolean(R.styleable.TopBar_rightVisibility, true);

        int centerSize  = array.getDimensionPixelSize(R.styleable.TopBar_centerSize, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 6, getResources().getDisplayMetrics()));
        String centerText= array.getString(R.styleable.TopBar_centerText);
        boolean centerVisibility = array.getBoolean(R.styleable.TopBar_centerVisibility, true);

        array.recycle();

        left.setBackgroundResource(leftBg);
        left.setText(leftText);
        left.setTextSize(leftSize);
        setLeftVisibility(leftVisibility);

        setRightVisibility(rightVisibility);
        right.setBackgroundResource(rigthBg);
        right.setTextSize(rightSize);
        right.setText(rightText);

        setCenterVisibility(centerVisibility);
        center.setText(centerText);
        center.setTextSize(centerSize);

    }

    public interface OnLeftAndRightClickListener {
        public void onLeftButtonClick();

        public void onRightButtonClick();
    }
}
