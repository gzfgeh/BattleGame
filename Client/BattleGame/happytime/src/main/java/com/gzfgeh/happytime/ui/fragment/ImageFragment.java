package com.gzfgeh.happytime.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.gzfgeh.happytime.adapter.ImageRecyclerViewAdapter;
import com.gzfgeh.happytime.beans.ImageBean;
import com.gzfgeh.happytime.presenter.fragment_image.IImageListView;
import com.gzfgeh.happytime.presenter.fragment_image.IImagePresenter;
import com.gzfgeh.happytime.presenter.fragment_image.ImagePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by guzhenfu on 16/1/12.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public class ImageFragment extends BaseListFragment implements IImageListView {
    private String title;
    private IImagePresenter presenter;
    private List<ImageBean> mData;
    private ImageRecyclerViewAdapter adapter;

    public static ImageFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        ImageFragment fragment = new ImageFragment();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
        presenter = new ImagePresenter(this);
    }

    @Override
    public void setRecycleView() {
        adapter = new ImageRecyclerViewAdapter(getContext(), this);
        adapter.setListener(listener);
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(scrollListener);
    }

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount() ) {

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };

    ImageRecyclerViewAdapter.OnItemClickListener listener = new ImageRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

        }
    };

    @Override
    public void onRefresh() {
        if (mData != null)
            mData.clear();
        presenter.loadImage();
    }

    @Override
    public void addImages(List<ImageBean> list) {
        if (mData == null){
            mData = new ArrayList<>();
        }
        mData.addAll(list);
        adapter.setDatas(mData);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        Toast.makeText(getActivity(), "加载数据失败", Toast.LENGTH_SHORT).show();
    }
}
