package com.gzfgeh.todaythings.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by guzhenfu on 15/8/9.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    private static final String TITLE[] = {};

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
