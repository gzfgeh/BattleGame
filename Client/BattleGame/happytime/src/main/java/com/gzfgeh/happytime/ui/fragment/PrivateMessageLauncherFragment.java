package com.gzfgeh.happytime.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gzfgeh.happytime.R;

/**
 * Created by guzhenfu on 2015/9/24.
 */
public class PrivateMessageLauncherFragment extends LauncherBaseFragment {
    private ImageView ivLikeVideo,ivThinkReward,ivThisWeek,ivWatchMovie;
    private Animation likeAnimation,thinkAnimation,watchAnimation,thisWeekAnimation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_private_message_launcher, container, false);
        ivLikeVideo=(ImageView) rootView.findViewById(R.id.iv_private_message_like_video);
        ivThinkReward=(ImageView) rootView.findViewById(R.id.iv_private_message_think_reward);
        ivWatchMovie=(ImageView) rootView.findViewById(R.id.iv_private_message_watch_movie);
        ivThisWeek=(ImageView) rootView.findViewById(R.id.private_message_this_week);
        return rootView;
    }

    /**
     * 濂藉枩娆綘鐨勮棰�	 */
    private void likeVideoAnimation(){
        ivLikeVideo.setVisibility(View.VISIBLE);

        likeAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.private_message_launcher);
        ivLikeVideo.startAnimation(likeAnimation);//寮�惎鍔ㄧ敾
        likeAnimation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {//鐩戝惉鍔ㄧ敾缁撴潫
                thinkReward();
            }
        });
    }

    /**
     * 璋㈣阿浣犵殑鎵撹祻
     */
    private void thinkReward(){
        ivThinkReward.setVisibility(View.VISIBLE);
        thinkAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.private_message_launcher);
        ivThinkReward.startAnimation(thinkAnimation);
        thinkAnimation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                watchMovie();
            }
        });
    }

    /**
     * 涓�捣鐪嬩釜鐢靛奖鍛�	 */
    private void watchMovie(){
        ivWatchMovie.setVisibility(View.VISIBLE);
        watchAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.private_message_launcher);
        ivWatchMovie.startAnimation(watchAnimation);
        watchAnimation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                thisWeek();
            }
        });
    }

    /**
     * 濂藉晩  杩欏懆鏈湁绌�	 */
    private void thisWeek(){
        ivThisWeek.setVisibility(View.VISIBLE);
        thisWeekAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.private_message_launcher);
        ivThisWeek.startAnimation(thisWeekAnimation);
    }

    @Override
    public void startAnimation() {
        new Handler().postDelayed(new Runnable() {//寤舵椂0.5绉掍箣鍚庡紑鍚枩娆㈣棰戝姩鐢�			@Override
            public void run() {
                likeVideoAnimation();
            }
        }, 500);
    }

    @Override
    public void stopAnimation() {
        ivLikeVideo.clearAnimation();
        ivThinkReward.clearAnimation();
        ivWatchMovie.clearAnimation();
        ivThisWeek.clearAnimation();
    }
}
