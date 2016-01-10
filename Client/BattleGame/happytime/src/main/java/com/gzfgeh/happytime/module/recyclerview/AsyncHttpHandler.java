package com.gzfgeh.happytime.module.recyclerview;

import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.gzfgeh.happytime.APP;
import com.gzfgeh.happytime.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by guzhenfu on 15/10/31.
 */
public class AsyncHttpHandler {
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

    public void onSuccess(int i, PreferenceActivity.Header[] headers, byte[] bytes) {
        try {
            mDrawable = new GifDrawable(bytes);
            setDrawable(mDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mDrawable.start();
        mView.setImageDrawable(mDrawable);
    }

    public void onFailure(int i, PreferenceActivity.Header[] headers, byte[] bytes, Throwable throwable) {
        Toast.makeText(APP.getContext(), "load error", Toast.LENGTH_SHORT).show();
    }
}
