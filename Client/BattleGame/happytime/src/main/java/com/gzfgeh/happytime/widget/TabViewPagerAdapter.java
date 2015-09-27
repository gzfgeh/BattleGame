package com.gzfgeh.happytime.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gzfgeh.happytime.Global;

import java.util.List;

/**
 * Created by guzhenfu on 15/9/27.
 */
public class TabViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public TabViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (String)fragments.get(position).getArguments().get(Global.ARG_TITLE);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
