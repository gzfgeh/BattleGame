package com.gzfgeh.customview.CustomTouchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by guzhenfu on 15/11/14.
 */
public class ListViewEx extends ListView {
    public ListViewEx(Context context) {
        this(context, null);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
