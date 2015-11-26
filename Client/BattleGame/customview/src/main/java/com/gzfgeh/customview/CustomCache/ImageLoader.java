package com.gzfgeh.customview.CustomCache;

import android.content.Context;

/**
 * Created by guzhenfu on 15/11/26.
 */
public class ImageLoader {
    private Context context;
    private CustomLruCache lruCache;

    private ImageLoader(Context context){
        this.context = context;
        lruCache = new CustomLruCache();

    }

    private ImageLoader getInstance(Context context){
        return new ImageLoader(context);
    }
}
