package com.gzfgeh.happytime.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.presenter.IProgress;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Description:
 * Created by guzhenfu on 16/1/11.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public abstract class BaseListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, IProgress {
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;

    protected LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list, null);
        ButterKnife.bind(this, view);
        swipeRefreshWidget.setColorSchemeResources(R.color.yellow, R.color.pink,
                R.color.blue, R.color.green);
        swipeRefreshWidget.setOnRefreshListener(this);

        recycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(mLayoutManager);

        setRecycleView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public abstract void setRecycleView();

    @Override
    public abstract void onRefresh();

    @Override
    public void showProgress() {
        if (swipeRefreshWidget != null)
            swipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshWidget != null)
            swipeRefreshWidget.setRefreshing(false);
    }
}
