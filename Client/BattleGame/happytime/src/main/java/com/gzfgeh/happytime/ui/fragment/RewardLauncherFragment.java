package com.gzfgeh.happytime.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.gzfgeh.happytime.R;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by guzhenfu on 15/9/22.
 */
public class RewardLauncherFragment extends LauncherBaseFragment {
    private ImageView ivReward, ivGold;
    private Bitmap goldBitmap;
    private boolean mStarted;

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
        mStarted = true;
        TranslateAnimation animation = new TranslateAnimation(0,0,0,goldBitmap.getHeight()*3 + 80);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        ivGold.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                if (mStarted){
                    ivReward.setVisibility(View.VISIBLE);
                    Animation anim= AnimationUtils.loadAnimation(getActivity(), R.anim.reward_launcher);
                    ivReward.startAnimation(anim);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            AlphaAnimation alphaAnimation=new AlphaAnimation(1,0);
                            alphaAnimation.setDuration(1000);
                            ivReward.startAnimation(alphaAnimation);
                            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {}
                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    ivReward.setVisibility(View.GONE);
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) {}
                            });
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    @Override
    public void stopAnimation() {
        mStarted = false;
        ivGold.clearAnimation();
    }
}
