package com.gzfgeh.happytime.presenter.activity_news_details;

import com.gzfgeh.happytime.beans.NewsDetailBean;
import com.gzfgeh.happytime.presenter.fragment_news.INewsModle;
import com.gzfgeh.happytime.presenter.fragment_news.NewModle;

/**
 * Description:
 * Created by guzhenfu on 16/1/10.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public class NewsDetailPresenter implements INewsDetailPresenter, NewModle.OnLoadNewsDetailListener {
    private INewsDetailModle viewModle;
    private INewsModle newModle;

    public NewsDetailPresenter(INewsDetailModle modle) {
        this.viewModle = modle;
        newModle = new NewModle();
    }

    @Override
    public void loadNewsDetail(String id) {
        viewModle.showProgress();
        newModle.loadNewsDetail(id, this);
    }

    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        viewModle.hideProgress();
        if (newsDetailBean != null)
            viewModle.showNewsDetailContent(newsDetailBean.getBody());
    }

    @Override
    public void onFailure(String msg, Exception e) {
        viewModle.hideProgress();

    }
}
