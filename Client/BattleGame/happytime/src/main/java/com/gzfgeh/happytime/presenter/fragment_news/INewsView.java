package com.gzfgeh.happytime.presenter.fragment_news;

import com.gzfgeh.happytime.beans.NewsBean;

import java.util.List;

/**
 * Created by guzhenfu on 16/1/9.
 */
public interface INewsView {
    void showProgress();
    void hideProgress();
    void loadFail(String msg);
    void addNews(List<NewsBean> newsList);
}
