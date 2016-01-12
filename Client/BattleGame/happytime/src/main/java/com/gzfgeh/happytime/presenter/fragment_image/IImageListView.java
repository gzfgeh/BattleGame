package com.gzfgeh.happytime.presenter.fragment_image;

import com.gzfgeh.happytime.beans.ImageBean;
import com.gzfgeh.happytime.presenter.IProgress;

import java.util.List;

/**
 * Description:
 * Created by guzhenfu on 16/1/12.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public interface IImageListView extends IProgress{
    void addImages(List<ImageBean> list);
    void showLoadFailMsg(String msg);
}
