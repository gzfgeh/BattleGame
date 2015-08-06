package com.gzfgeh.battlegame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gzfgeh.battlegame.R;

import java.util.List;
import java.util.Map;

/**
 * Created by guzhenfu on 15/8/6.
 */
public class RoomListAdapter extends BaseAdapter {
    private List<Map<String, Object>> datas;
    private LayoutInflater layoutInflater;
    private Context context;

    public RoomListAdapter(Context context, List<Map<String, Object>> datas){
        this.datas = datas;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.room_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.room_name);
            viewHolder.number = (TextView) convertView.findViewById(R.id.room_num);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText((String) datas.get(position).get("name"));
        viewHolder.number.setText((String)datas.get(position).get("number"));
        return convertView;
    }

    public final class ViewHolder{
        public TextView name;
        public TextView number;
    }
}
