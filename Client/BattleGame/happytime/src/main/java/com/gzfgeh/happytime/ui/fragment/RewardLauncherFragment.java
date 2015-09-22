package com.gzfgeh.happytime.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gzfgeh.happytime.R;

/**
 * Created by guzhenfu on 15/9/22.
 */
public class RewardLauncherFragment extends LauncherBaseFragment {
    private ImageView ivReward, ivGold;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reward, container, false);
        ivGold = (ImageView) rootView.findViewById(R.id.iv_gold);
        ivReward = (ImageView) rootView.findViewById(R.id.iv_reward);
        return rootView;
    }

    @Override
    public void startAnimation() {

    }

    @Override
    public void stopAnimation() {

    }
}
