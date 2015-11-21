package com.gzfgeh.customview.threadpool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gzfgeh.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guzhenfu on 15/11/19.
 */
public class AsyncTaskAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<AsyncTaskTest> mDatas;

    public AsyncTaskAdapter(Context context, List<AsyncTaskTest> data){
        this.mContext = context;
        this.mDatas = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_view_item, null);
            AsyncTaskTest task = new AsyncTaskTest((MyListItem)convertView);
            task.executeOnExecutor(NoLimitActivity.allTaskExecutor);
            mDatas.add(task);
        }
        return convertView;
    }
}
