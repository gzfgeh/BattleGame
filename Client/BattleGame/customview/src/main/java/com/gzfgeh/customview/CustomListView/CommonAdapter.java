package com.gzfgeh.customview.CustomListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by guzhenfu on 15/12/13.
 */
public abstract class CommonAdapter<T> extends BaseAdapter{
    public Context mContext;
    private LayoutInflater inflater;
    public List<T> mData;
    protected final int mItemLayoutId;

    public CommonAdapter(Context context, List<T> data, int id){
        this.mContext = context;
        this.mData = data;
        inflater = LayoutInflater.from(context);
        this.mItemLayoutId = id;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent) {
        return ViewHolder.getInstance(mContext, convertView, parent, mItemLayoutId,
                position);
    }

    public abstract void convert(ViewHolder helper, T item);
}
