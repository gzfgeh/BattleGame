package com.gzfgeh.happytime.presenter.fragment_image;

import com.gzfgeh.happytime.beans.ImageBean;

import java.util.List;

/**
 * Description:
 * Created by guzhenfu on 16/1/12.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public class ImagePresenter implements IImagePresenter, ImageModle.OnLoadImageListListener {
    private IImageListView imageListView;
    private IImageModle modle;

    public ImagePresenter(IImageListView imageListView) {
        this.imageListView = imageListView;
        modle = new ImageModle();
    }

    @Override
    public void loadImage() {
        imageListView.showProgress();
        modle.getImageList(this);
    }

    @Override
    public void onSuccess(List<ImageBean> list) {
        imageListView.hideProgress();
        imageListView.addImages(list);
    }

    @Override
    public void onFail(String msg) {
        imageListView.hideProgress();
        imageListView.showLoadFailMsg(msg);
    }
}
