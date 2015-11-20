package com.gzfgeh.customview.threadpool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guzhenfu on 15/11/19.
 */
public class AsyncTaskAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List mDatas;
    private int mTaskCnt;

    public AsyncTaskAdapter(Context context, int taskCnt){
        this.mContext = context;
        this.mTaskCnt = taskCnt;
        inflater = LayoutInflater.from(context);
        mDatas = new ArrayList();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
