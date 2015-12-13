package com.gzfgeh.customview.CustomListView;

import android.content.Context;
import android.widget.TextView;

import com.gzfgeh.customview.R;

import java.util.List;

/**
 * Created by guzhenfu on 15/12/13.
 */
public class ListViewAdapter<T> extends CommonAdapter<T>{
    public ListViewAdapter(Context mContext, List<T> mData, int id) {
        super(mContext, mData, id);
    }

    @Override
    public void convert(ViewHolder helper, T item) {
        helper.setText(R.id.id_tv_title, (String)item);
    }
}
