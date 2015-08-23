package com.gzfgeh.customdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guzhenfu on 15/8/23.
 */
public class CheckedTextAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Map<String, Object>> data;
    private HashMap<Integer,Boolean> checkedMap;

    public CheckedTextAdapter(Context context, List data, HashMap<Integer, Boolean> checkedMap){
        this.context = context;
        this.data = data;
        this.checkedMap = checkedMap;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_view_checked_text, null);
            holder = new ViewHolder();
            holder.checktv_title = (CheckedTextView) convertView.findViewById(R.id.checktv_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.checktv_title.setText(data.get(position).get("text").toString());
        //根据checkMap中position的状态设置是否被选中
        if (checkedMap.get(position) != null && checkedMap.get(position) == true) {
            holder.checktv_title.setChecked(true);
        }else{
            holder.checktv_title.setChecked(false);
        }

        return convertView;
    }

    public final class ViewHolder {
        CheckedTextView checktv_title;
    }
}
