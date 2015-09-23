package com.gzfgeh.happytime.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.gzfgeh.happytime.R;

/**
 * Created by guzhenfu on 15/9/22.
 */
public class RewardLauncherFragment extends LauncherBaseFragment {
    private ImageView ivReward, ivGold;
    private Bitmap goldBitmap;
    private boolean mStart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reward, container, false);
        ivGold = (ImageView) rootView.findViewById(R.id.iv_gold);
        ivReward = (ImageView) rootView.findViewById(R.id.iv_reward);

        goldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_gold);
        startAnimation();
        return rootView;
    }

    @Override
    public void startAnimation() {
        mStart = true;

        TranslateAnimation animation = new TranslateAnimation(
                goldBitmap.getHeight()*2 + 80, 0, 0, 0);
        animation.setDuration(500);
        animation.setFillAfter(true);
        ivGold.startAnimation(animation);
    }

    @Override
    public void stopAnimation() {

    }
}
