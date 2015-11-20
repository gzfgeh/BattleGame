package com.gzfgeh.customview.threadpool;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 15/11/19.
 */
public class MyListItem extends LinearLayout {
    private TextView mTitle;
    private ProgressBar mProgress;


    public MyListItem(Context context) {
        this(context, null);
    }

    public MyListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setTitle(String title) {
        if (mTitle == null) {
            mTitle = (TextView)findViewById(R.id.task_name);
        }
        mTitle.setText(title);
    }

    public void setProgress(int prog) {
        if (mProgress == null) {
            mProgress = (ProgressBar)findViewById(R.id.task_progress);
        }
        mProgress.setProgress(prog);
    }
}
