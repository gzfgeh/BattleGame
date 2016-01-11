package com.gzfgeh.happytime.presenter.fragment_news;

import com.gzfgeh.happytime.beans.NewsBean;
import com.gzfgeh.happytime.presenter.IProgress;

import java.util.List;

/**
 * Created by guzhenfu on 16/1/9.
 */
public interface INewsView extends IProgress{
    void loadFail(String msg);
    void addNews(List<NewsBean> newsList);
}
