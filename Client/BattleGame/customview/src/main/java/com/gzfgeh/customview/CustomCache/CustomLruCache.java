package com.gzfgeh.customview.CustomCache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by guzhenfu on 15/11/26.
 */
public class CustomLruCache extends LruCache<String, Bitmap> {
    static int cacheSize;

    static {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        cacheSize = maxMemory / 8;
    }

    public CustomLruCache() {
        super(cacheSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
    }
}
