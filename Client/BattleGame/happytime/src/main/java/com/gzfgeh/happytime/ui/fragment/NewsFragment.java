package com.gzfgeh.happytime.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzfgeh.happytime.Global;
import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.adapter.TabViewPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by guzhenfu on 15/9/27.
 */
public class NewsFragment extends Fragment {
    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_NBA = 1;
    public static final int NEWS_TYPE_CARS = 2;
    public static final int NEWS_TYPE_JOKES = 3;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    public static NewsFragment newInstance(String title) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Global.ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);
        initViewPager();
        initTabLayout();
        return rootView;
    }

    private void initTabLayout() {
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.top));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.cars));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.nba));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.jokes));
        tabLayout.setupWithViewPager(viewpager);
    }

    private void initViewPager() {
        viewpager.setOffscreenPageLimit(3);
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_TOP), getString(R.string.top));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_CARS), getString(R.string.cars));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_NBA), getString(R.string.nba));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_JOKES), getString(R.string.jokes));
        viewpager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
