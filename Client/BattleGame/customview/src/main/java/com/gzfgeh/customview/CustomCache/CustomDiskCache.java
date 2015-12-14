package com.gzfgeh.customview.CustomCache;

import android.content.Context;

import com.gzfgeh.customview.Utils.Utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by guzhenfu on 15/11/27.
 */
public class CustomDiskCache{
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;


    public static DiskLruCache getDiskCache(Context context){
        DiskLruCache cache = null;
        File diskCacheDir = Utils.getDiskCacheDir(context, "bitmap");
        if (!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }
        if (Utils.getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                cache = DiskLruCache.open(diskCacheDir, 1, 1,
                        DISK_CACHE_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cache;
    }
}
