package com.gzfgeh.happytime.presenter.fragment_news;

import com.gzfgeh.happytime.beans.NewsBean;
import com.gzfgeh.happytime.beans.NewsDetailBean;
import com.gzfgeh.happytime.ui.fragment.NewsFragment;
import com.gzfgeh.happytime.utils.JsonUtils;
import com.gzfgeh.happytime.utils.LogUtils;
import com.gzfgeh.happytime.utils.UrlUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

/**
 * Description: 业务类
 * Created by guzhenfu on 16/1/9.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public class NewModle implements INewsModle {

    @Override
    public void loadNews(String url, final int type, final OnLoadNewsListListener listener) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.body().toString(), e);
            }

            @Override
            public void onResponse(String response) {
                LogUtils.i("TAG", response);
                List<NewsBean> list = JsonUtils.readJsonNewsBeans(response, getID(type));
                listener.onSuccess(list);
            }
        });
    }

    @Override
    public void loadNewsDetail(final String id, final OnLoadNewsDetailListener listener) {
        String url = getDetailUrl(id);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFailure(request.body().toString(), e);
            }

            @Override
            public void onResponse(String response) {
                NewsDetailBean bean = JsonUtils.readJsonNewsDetailBeans(response, id);
                listener.onSuccess(bean);
            }
        });
    }

    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(UrlUtils.NEW_DETAIL);
        sb.append(docId).append(UrlUtils.END_DETAIL_URL);
        return sb.toString();
    }

    private String getID(int type) {
        String id;
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                id = UrlUtils.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                id = UrlUtils.NBA_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = UrlUtils.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                id = UrlUtils.JOKE_ID;
                break;
            default:
                id = UrlUtils.TOP_ID;
                break;
        }
        return id;
    }

    public interface OnLoadNewsListListener {
        void onSuccess(List<NewsBean> list);
        void onFailure(String msg, Exception e);
    }

    public interface OnLoadNewsDetailListener {
        void onSuccess(NewsDetailBean newsDetailBean);
        void onFailure(String msg, Exception e);
    }
}
