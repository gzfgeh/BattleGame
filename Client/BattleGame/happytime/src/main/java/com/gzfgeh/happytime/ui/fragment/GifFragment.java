package com.gzfgeh.happytime.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.gzfgeh.happytime.Global;
import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.adapter.HomeRecyclerViewAdapter;
import com.gzfgeh.happytime.ui.activity.BannerActivityOne;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * Created by guzhenfu on 15/9/27.
 */
public class GifFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, HomeRecyclerViewAdapter.RecyclerItemClickListener, HomeRecyclerViewAdapter.RecyclerItemLongClickListener, BaseBanner.OnItemClickL {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomeRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private boolean isLoading, hasMore;

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
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.main_swipe);
        mSwipeRefreshLayout.setColorScheme(R.color.red, R.color.blue, R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.id_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HomeRecyclerViewAdapter(getActivity(), getData());
        mRecyclerView.setItemAnimator(new FadeInAnimator());
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        mRecyclerView.setAdapter(scaleAdapter);
        mAdapter.setListener(this);
        mAdapter.setLongListener(this);
        mAdapter.setListenerL(this);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isLoading && hasMore
                        && mAdapter.getItemCount() == (mLayoutManager
                        .findLastVisibleItemPosition() + 1)
                        && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isLoading = true;
                    requestServerArticleMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mRecyclerView.setEnabled(mLayoutManager
                        .findFirstCompletelyVisibleItemPosition() == 0);
            }
        });
        return rootView;
    }

    private void requestServerArticleMore() {

    }

    private List<String> getData(){
        List<String> data = new ArrayList<>();
        for (int i=0; i<100; i++){
            data.add("" + i);
        }
        return data;
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onItemClick(View view, int postion) {
        Toast.makeText(getActivity(), "click --" + postion, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int postion) {
        Toast.makeText(getActivity(), "long click --" + postion, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int i) {
        switch (i){
            case 0:
                Intent intent = new Intent(getActivity(), BannerActivityOne.class);
                startActivity(intent);
                break;

            default:
                Toast.makeText(getActivity(), i + "", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
