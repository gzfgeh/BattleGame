package com.gzfgeh.happytime.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.adapter.NewsRecyclerViewAdapter;
import com.gzfgeh.happytime.beans.NewsBean;
import com.gzfgeh.happytime.presenter.fragment_news.INewsPresenter;
import com.gzfgeh.happytime.presenter.fragment_news.INewsView;
import com.gzfgeh.happytime.presenter.fragment_news.NewsPresenter;
import com.gzfgeh.happytime.ui.activity.NewsDetailActivity;
import com.gzfgeh.happytime.utils.LogUtils;
import com.gzfgeh.happytime.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by guzhenfu on 16/1/9.
 */
public class NewsListFragment extends BaseListFragment implements INewsView {
    private int type;
    private int pageIndex = 0;
    private INewsPresenter presenter;
    private NewsRecyclerViewAdapter adapter;
    private List<NewsBean> mData;

    public static NewsListFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
        presenter = new NewsPresenter(this);
    }



    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener(){
        private int lastVisiableItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisiableItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisiableItem + 1 == adapter.getItemCount()
                    && adapter.isShowFooter()) {
                //加载更多
                LogUtils.d("TAG", "loading more data");
                presenter.loadNews(type, pageIndex + UrlUtils.PAZE_SIZE);
            }
        }
    };

    private NewsRecyclerViewAdapter.RecyclerItemClickListener listener = new NewsRecyclerViewAdapter.RecyclerItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            NewsBean bean = adapter.getItem(position);
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("bean", bean);

            View transitionView = view.findViewById(R.id.ivNews);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            transitionView, getString(R.string.transition_news_img));
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };

    @Override
    public void setRecycleView() {
        adapter = new NewsRecyclerViewAdapter(getContext(), this);
        adapter.setListener(listener);
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(scrollListener);
    }

    @Override
    public void loadFail(String msg) {
        Toast.makeText(getActivity(), "加载数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addNews(List<NewsBean> newsList) {
        adapter.setShowFooter(true);
        if (mData == null)
            mData = new ArrayList<>();
        mData.addAll(newsList);
        if (pageIndex == 0){
            adapter.setData(mData);
        }else{
            if (newsList == null || newsList.size() == 0)
                adapter.setShowFooter(true);
            adapter.notifyDataSetChanged();
        }
        pageIndex += UrlUtils.PAZE_SIZE;
    }

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if (mData != null){
            mData.clear();
        }
        presenter.loadNews(type, pageIndex);
    }
}
