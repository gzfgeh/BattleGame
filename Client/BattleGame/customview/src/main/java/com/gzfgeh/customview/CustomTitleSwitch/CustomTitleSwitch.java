package com.gzfgeh.customview.CustomTitleSwitch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 15/11/29.
 */
public class CustomTitleSwitch extends View implements View.OnClickListener {
    private SwitchOnClickListener listener;

    public void setListener(SwitchOnClickListener listener) {
        this.listener = listener;
    }

    public CustomTitleSwitch(Context context) {
        this(context, null);
    }

    public CustomTitleSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.title_switch, null);
        Button leftBtn = (Button) view.findViewById(R.id.left);
        Button rightBtn = (Button) view.findViewById(R.id.right);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            switch(v.getId()){
                case R.id.left:
                    listener.leftClickListener();
                    break;

                case R.id.right:
                    listener.rightClickListener();
                    break;
            }
        }

    }

    public interface SwitchOnClickListener{
        void leftClickListener();
        void rightClickListener();
    }
}
