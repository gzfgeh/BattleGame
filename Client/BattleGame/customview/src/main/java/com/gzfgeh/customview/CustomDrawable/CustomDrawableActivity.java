package com.gzfgeh.customview.CustomDrawable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 2015/11/16.
 */
public class CustomDrawableActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_drawable);

        View view = findViewById(R.id.custom_drawable);
        view.setBackground(new CustomDrawable());
    }


}
