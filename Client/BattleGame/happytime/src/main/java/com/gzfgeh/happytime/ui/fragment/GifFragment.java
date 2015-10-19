package com.gzfgeh.happytime.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.flyco.banner.transform.ZoomOutSlideTransformer;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.gzfgeh.happytime.Global;
import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.adapter.ImagePagerAdapter;
import com.gzfgeh.happytime.banner.DataProvider;
import com.gzfgeh.happytime.banner.SimpleImageBanner;
import com.gzfgeh.happytime.widget.CircleFlowIndicator;
import com.gzfgeh.happytime.widget.ViewFlow;
import com.gzfgeh.happytime.widget.ZoomInEnter;

import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by guzhenfu on 15/9/27.
 */
public class GifFragment extends Fragment implements WaveSwipeRefreshLayout.OnRefreshListener {
    private ListView mListView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    public static GifFragment newInstance(String title){
        GifFragment fragment = new GifFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Global.ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gif_fragment, container, false);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) rootView.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        mWaveSwipeRefreshLayout.setWaveColor(Color.RED);
        mListView = (ListView) rootView.findViewById(android.R.id.list);
        mListView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, getData()));
        initListViewHeader();
        return rootView;
    }

    private void initListViewHeader() {
        View headView = View.inflate(getActivity(), R.layout.list_view_header, null);
        mListView.addHeaderView(headView);
        SimpleImageBanner banner = (SimpleImageBanner) headView.findViewById(R.id.banner);
        banner.setSelectAnimClass(ZoomInEnter.class)
                .setSource(DataProvider.getList())
                .setTransformerClass(ZoomOutSlideTransformer.class)
                .startScroll();

        banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
            @Override
            public void onItemClick(int i) {
                //BannerFragmentOne.newInstance(i + 1 + "");
            }
        });
    }

    private List<String> getData(){
        List<String> data = new ArrayList<>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        data.add("测试数据5");
        data.add("测试数据6");
        data.add("测试数据7");
        data.add("测试数据8");
        data.add("测试数据9");
        data.add("测试数据10");
        data.add("测试数据11");
        data.add("测试数据12");
        data.add("测试数据13");
        data.add("测试数据14");
        data.add("测试数据15");
        data.add("测试数据16");
        return data;
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

}
