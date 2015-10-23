package com.gzfgeh.happytime.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "DOWN", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                    mAdapter.getData().clear();
                    addList();
                    break;

                case 1:
                    Toast.makeText(getActivity(), "UP", Toast.LENGTH_SHORT).show();
                    addList();
                    break;

                default:
                    break;
            }

        }

    };

    private void addList() {
        List<String> list = getList();
        mAdapter.getData().addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private List<String> getList() {
        List<String> list = new ArrayList<>();
        int size = mAdapter.getData().size();
        int lastPosition = size > 0 ? Integer.valueOf(mAdapter.getData().get(size - 2)) : 0;
        for (int i = 1; i < 20; i++) {
            list.add(lastPosition + i + "");
        }

        return list;
    }

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
        mSwipeRefreshLayout.post(new Runnable() {

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                try {
                    handler.sendEmptyMessageDelayed(0, 3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.id_recycler_view);
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    handler.sendEmptyMessageDelayed(1, 3000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        return rootView;
    }

    private List<String> getData(){
        List<String> data = new ArrayList<>();
        for (int i=0; i<10; i++){
            data.add("" + i);
        }
        return data;
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "click --" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(getActivity(), "long click --" + position, Toast.LENGTH_SHORT).show();
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
