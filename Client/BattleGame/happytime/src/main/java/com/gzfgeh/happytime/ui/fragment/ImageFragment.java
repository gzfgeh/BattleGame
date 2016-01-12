package com.gzfgeh.happytime.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

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
    private int type;
    private IImagePresenter presenter;
    private List<ImageBean> mData;

    public static ImageFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        ImageFragment fragment = new ImageFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
        presenter = new ImagePresenter(this);
    }

    @Override
    public void setRecycleView() {

    }

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
    }

    @Override
    public void showLoadFailMsg(String msg) {
        Toast.makeText(getActivity(), "加载数据失败", Toast.LENGTH_SHORT).show();
    }
}
