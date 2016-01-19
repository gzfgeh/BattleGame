package com.gzfgeh.happytime.presenter.fragment_news;

import com.gzfgeh.happytime.beans.NewsBean;
import com.gzfgeh.happytime.ui.fragment.NewsFragment;
import com.gzfgeh.happytime.utils.LogUtils;
import com.gzfgeh.happytime.utils.UrlUtils;

import java.util.List;

/**
 * Created by guzhenfu on 16/1/9.
 */
public class NewsPresenter implements INewsPresenter, NewModle.OnLoadNewsListListener {
    private INewsView view;
    private INewsModle modle;

    public NewsPresenter(INewsView view) {
        this.view = view;
        this.modle = new NewModle();
    }

    @Override
    public void loadNews(int type, int pageIndex) {
        String url = getUrl(type, pageIndex);
        LogUtils.i("TAG", url);
        if (pageIndex == 0){
            view.showProgress();
        }
        modle.loadNews(url, type, this);
    }

    private String getUrl(int type, int pageIndex) {
        StringBuilder sb = new StringBuilder();
        switch (type){
            case NewsFragment.NEWS_TYPE_TOP:
                sb.append(UrlUtils.TOP_URL).append(UrlUtils.TOP_ID);
                break;

            case NewsFragment.NEWS_TYPE_CARS:
                sb.append(UrlUtils.TOP_URL).append(UrlUtils.CAR_ID);
                break;

            case NewsFragment.NEWS_TYPE_NBA:
                sb.append(UrlUtils.TOP_URL).append(UrlUtils.NBA_ID);
                break;

            case NewsFragment.NEWS_TYPE_JOKES:
                sb.append(UrlUtils.TOP_URL).append(UrlUtils.JOKE_ID);
                break;
        }
        sb.append("/").append(pageIndex).append(UrlUtils.END_URL);
        return sb.toString();
    }

    @Override
    public void onSuccess(List<NewsBean> list) {
        view.hideProgress();
        view.addNews(list);
    }

    @Override
    public void onFailure(String msg) {
        view.hideProgress();
        view.loadFail(msg);
    }
}
