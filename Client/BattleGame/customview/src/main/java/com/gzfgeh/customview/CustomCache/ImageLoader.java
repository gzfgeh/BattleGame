package com.gzfgeh.customview.CustomCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.gzfgeh.customview.R;
import com.gzfgeh.customview.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by guzhenfu on 15/11/26.
 */
public class ImageLoader {
    public static final int MESSAGE_POST_RESULT = 1;
    public static final int MESSAGE_POST_ERROR = 2;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CACHE_INDEX = 0;
    private Context mContext;
    private CustomLruCache mMemoryCache;
    private DiskLruCache mDiskCache;
    private ImageResizer mImageResizer;

    private static final int CPU_COUNT = Runtime.getRuntime()
            .availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), sThreadFactory);

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_POST_RESULT:
                    LoaderResult result = (LoaderResult) msg.obj;
                    ImageView imageView = result.imageView;
//                String tag = (String) imageView.getTag();
//                if (!TextUtils.equals(tag, result.uri)){
//                    imageView.setBackgroundResource(R.drawable.image1);
//                }else{
//                    imageView.setImageBitmap(result.bitmap);
//                }
                    imageView.setImageBitmap(result.bitmap);
                    break;

                case MESSAGE_POST_ERROR:
                    ((ImageView)(msg.obj)).setBackgroundResource(R.drawable.image1);
                    break;
            }
        }
    };

    private ImageLoader(Context context){
        this.mContext = context;
        mMemoryCache = new CustomLruCache();
        mDiskCache = CustomDiskCache.getDiskCache(context);
        mImageResizer = new ImageResizer();
    }

    public static ImageLoader getInstance(Context context){
        return new ImageLoader(context);
    }

    public void bindBitmap(final String url, final ImageView imageView, final int width, final int height){
//        imageView.setTag(url);
        String tag = (String) imageView.getTag();
        if (!TextUtils.equals(tag, url)){
            imageView.setBackgroundResource(R.drawable.image1);
        }

        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            return;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(url, width, height);
                if (bitmap != null){
                    LoaderResult result = new LoaderResult(imageView, url, bitmap);
                    mHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }else{
                    mHandler.obtainMessage(MESSAGE_POST_ERROR, imageView).sendToTarget();
                }
            }
        };

        THREAD_POOL_EXECUTOR.execute(runnable);
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    private Bitmap getBitmapFromDiskCache(String url, int width, int height) throws IOException{
        Bitmap bitmap = null;
        String key = Utils.hashKeyFormUrl(url);
        DiskLruCache.Snapshot snapshot = mDiskCache.get(key);
        if(snapshot != null){
            FileInputStream inputStream = (FileInputStream) snapshot.getInputStream(0);
            FileDescriptor fileDescriptor = inputStream.getFD();
            bitmap = mImageResizer.decodeSampledBitmapFromFileDescriptor(fileDescriptor,
                    width, height);
            if (bitmap != null){
                addBitmapToMemoryCache(url, bitmap);
            }
        }
        return bitmap;
    }

    public Bitmap loadBitmap(String url, int width, int height){
        Bitmap bitmap = getBitmapFromMemCache(url);
        if (bitmap != null){
            return bitmap;
        }

        try {
            bitmap = getBitmapFromDiskCache(url, width, height);
            if (bitmap != null){
                return bitmap;
            }
            bitmap = downloadBitmapFromHttp(url, width, height);
            if (bitmap != null){
                return bitmap;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return downloadBitmapFromUrl(url);
    }


    private Bitmap downloadBitmapFromHttp(String url, int width, int height)
            throws IOException{
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network from UI Thread.");
        }
        if (mDiskCache == null) {
            return null;
        }

        String key = Utils.hashKeyFormUrl(url);
        DiskLruCache.Editor editor = mDiskCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (downloadUrlToStream(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskCache.flush();
        }
        return getBitmapFromDiskCache(url, width, height);
    }

    public boolean downloadUrlToStream(String urlString,
                                       OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (IOException e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            Utils.close(out);
            Utils.close(in);
        }
        return false;
    }

    private Bitmap downloadBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (final IOException e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            Utils.close(in);
        }
        return bitmap;
    }

    private static class LoaderResult {
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }

}
