package com.gzfgeh.customview.CustomTitleSwitch;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 15/11/29.
 */
public class CustomTitleSwitchActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_title_switch);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        LeftFragment leftFragment = new LeftFragment();
        transaction.add(R.id.fl_content, leftFragment);
        transaction.commitAllowingStateLoss();
    }
}
