package com.gzfgeh.customview.CustomTitleSwitch;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzfgeh.customview.R;

/**
 * Created by guzhenfu on 15/11/29.
 */
public class LeftFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_left, null);
    }
}
