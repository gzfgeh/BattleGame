package com.gzfgeh.happytime.module.recyclerview;

import android.widget.Toast;

import com.gzfgeh.happytime.APP;
import com.gzfgeh.happytime.R;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by guzhenfu on 15/10/31.
 */
public class AsyncHttpHandler extends AsyncHttpResponseHandler {
    private GifImageView mView;
    private GifDrawable mDrawable;

    public GifDrawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(GifDrawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public AsyncHttpHandler(GifImageView view){
        this.mView = view;
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {
        try {
            mDrawable = new GifDrawable(bytes);
            setDrawable(mDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mDrawable.start();
        mView.setImageDrawable(mDrawable);
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        Toast.makeText(APP.getContext(), "load error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgress(int bytesWritten, int totalSize) {
        mView.setImageDrawable(APP.getContext().getResources().getDrawable(R.drawable.ic_add));
        super.onProgress(bytesWritten, totalSize);
    }
}
