package com.gzfgeh.addsub;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by guzhenfu on 15/9/14.
 */
public class AddSub extends LinearLayout implements View.OnClickListener {
    private Context context;
    private Button addBtn, subBtn;
    private EditText edt;
    private int num = 0;
    private ChangeListener listener = null;
    private int maxNum = 0;

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public void setChangeListener(ChangeListener listener) {
        this.listener = listener;
    }

    public AddSub(Context context){
        this(context, null);
    }

    public AddSub(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.add_sub_layout, this, true);
        addBtn = (Button) view.findViewById(R.id.add_btn);
        subBtn = (Button) view.findViewById(R.id.sub_btn);
        edt = (EditText) view.findViewById(R.id.et);
        edt.setText(String.valueOf(num));
        edt.setSelection(edt.getText().length());

        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        edt.addTextChangedListener(new TextWatcher() {
            private boolean isChanged = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isChanged)
                    return;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged)
                    return;

                if (!addBtn.isClickable()) {
                    addBtn.setClickable(true);
                    addBtn.setAlpha(1.0f);
                }

                if (!subBtn.isClickable()) {
                    subBtn.setClickable(true);
                    subBtn.setAlpha(1.0f);
                }

                String numString = s.toString();
                if(numString == null || numString.equals("")) {
                    num = 0;
                }
                else {

                    int numInt = Integer.parseInt(numString);
                    if (numInt <= 0) {
                        subBtn.setClickable(false);
                        subBtn.setAlpha(0.5f);
                    } else if(numInt >= maxNum){
                        addBtn.setAlpha(0.5f);
                        addBtn.setClickable(false);
                        numInt = maxNum;
                        isChanged = true;
                        edt.setText(String.valueOf(maxNum));
                        isChanged = false;
                        edt.invalidate();
                    }
                    else {
                        //设置EditText光标位置 为文本末端
                        edt.setSelection(edt.getText().toString().length());
                    }
                    num = numInt;
                    if (listener != null)
                        listener.onChangeListener(Integer.valueOf(edt.getText().toString()));
                }

            }
        });

    }


    @Override
    public void onClick(View v) {
        String numString = edt.getText().toString();
        if (numString == null || numString.equals("")) {
            num = 0;
            edt.setText("0");
        }else{
            switch (v.getId()){
                case R.id.sub_btn:
                    if (--num < 0){
                        num++;
                    }else{
                        edt.setText(String.valueOf(num));

                        if (num == 0) {
                            subBtn.setClickable(false);
                            subBtn.setAlpha(0.5f);
                        }
                        else if (!addBtn.isClickable()) {
                            addBtn.setClickable(true);
                            addBtn.setAlpha(1.0f);
                        }
                    }
                    break;

                case R.id.add_btn:
                    if (++num < 0){
                        num--;
                    }else{
                        edt.setText(String.valueOf(num));
                        if (!subBtn.isClickable()) {
                            subBtn.setClickable(true);
                            subBtn.setAlpha(1.0f);
                        }
                        else if(num == maxNum) {
                            addBtn.setClickable(false);
                            addBtn.setAlpha(0.5f);
                        }
                    }
                    break;
            }

        }

    }

    public interface ChangeListener{
        void onChangeListener(int num);
    }
}
