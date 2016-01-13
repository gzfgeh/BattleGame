package com.gzfgeh.happytime.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gzfgeh.happytime.R;

/**
 * Description:
 * Created by guzhenfu on 16/1/10.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public class ImageLoaderUtils {
    public static void display(Context context, ImageView imageView, String url,
                    int placeHold, int error){
        if (imageView == null){
            throw new IllegalArgumentException("argument error");
        }

        Glide.with(context).load(url).placeholder(placeHold).into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loadfail).crossFade().into(imageView);
    }

    public static void display(Fragment fragment, ImageView imageView, String url) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(fragment).load(url).placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loadfail).crossFade().into(imageView);
    }


}
