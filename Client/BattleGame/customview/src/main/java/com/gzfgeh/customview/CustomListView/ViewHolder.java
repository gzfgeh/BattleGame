package com.gzfgeh.customview.CustomListView;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by guzhenfu on 15/12/13.
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int id, int position) {
        mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(id, parent, false);
        mConvertView.setTag(this);
    }

    public static ViewHolder getInstance(Context context, View convertView,
                  ViewGroup parent, int id, int position){
        if (convertView == null){
            return new ViewHolder(context, parent, id, position);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        return holder;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
