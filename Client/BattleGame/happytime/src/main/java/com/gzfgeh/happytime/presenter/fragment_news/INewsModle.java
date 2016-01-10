package com.gzfgeh.happytime.presenter.fragment_news;

/**
 * Description:
 * Created by guzhenfu on 16/1/9.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public interface INewsModle {
    void loadNews(String url, int type, NewModle.OnLoadNewsListListener listener);
    void loadNewsDetail(String id, NewModle.OnLoadNewsDetailListener listener);
}
