package com.gzfgeh.happytime.presenter.fragment_image;

import com.gzfgeh.happytime.beans.ImageBean;
import com.gzfgeh.happytime.utils.JsonUtils;
import com.gzfgeh.happytime.utils.UrlUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

/**
 * Description:
 * Created by guzhenfu on 16/1/12.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public class ImageModle implements IImageModle {

    @Override
    public void getImageList(final OnLoadImageListListener listener) {
        String url = UrlUtils.IMAGES_URL;
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFail(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                List<ImageBean> imageBeanList = JsonUtils.readJsonImageBeans(response);
                listener.onSuccess(imageBeanList);
            }
        });
    }

    public interface OnLoadImageListListener{
        void onSuccess(List<ImageBean> list);
        void onFail(String msg);
    }
}
