package com.gzfgeh.happytime.ui.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.ui.fragment.LauncherBaseFragment;
import com.gzfgeh.happytime.ui.fragment.PrivateMessageLauncherFragment;
import com.gzfgeh.happytime.ui.fragment.RewardLauncherFragment;
import com.gzfgeh.happytime.ui.fragment.StereoscopicLauncherFragment;
import com.gzfgeh.happytime.widget.BaseFragmentAdapter;
import com.gzfgeh.happytime.widget.GuideViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guzhenfu on 15/9/22.
 */
public class LaunchActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private ImageView[] mIcons;
    private GuideViewPager viewPager;
    private List<LauncherBaseFragment> mFragmentList;
    private int mLastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.ll_view);
        mIcons = new ImageView[3];
        for (int i=0; i<mIcons.length; i++){
            ImageView ivIcon = new ImageView(this);
            ivIcon.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            ivIcon.setBackgroundResource(
                    i == 0 ? R.drawable.page_indicator_focused : R.drawable.page_indicator_unfocused);
            mIcons[i] = ivIcon;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(30,30));
            params.setMargins(30,0,30,120);
            viewGroup.addView(ivIcon, params);
        }

        viewPager = (GuideViewPager) findViewById(R.id.vp_launcher);
        viewPager.setBackGround(BitmapFactory.decodeResource(getResources(), R.drawable.bg_launcher));
        mFragmentList = new ArrayList<>();
        RewardLauncherFragment rewardFragment = new RewardLauncherFragment();
        PrivateMessageLauncherFragment messageLauncherFragment = new PrivateMessageLauncherFragment();
        StereoscopicLauncherFragment stereoscopicLauncherFragment = new StereoscopicLauncherFragment();
        mFragmentList.add(rewardFragment);
        mFragmentList.add(messageLauncherFragment);
        mFragmentList.add(stereoscopicLauncherFragment);

        BaseFragmentAdapter mAdapter = new BaseFragmentAdapter(getSupportFragmentManager(),mFragmentList);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        for(int j=0; j<mFragmentList.size(); j++){
            mIcons[j].setBackgroundResource(j == i ? R.drawable.page_indicator_focused : R.drawable.page_indicator_unfocused);
        }
        LauncherBaseFragment fragment = mFragmentList.get(i);
        fragment.startAnimation();
        mFragmentList.get(mLastFragment).stopAnimation();
        mLastFragment = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
