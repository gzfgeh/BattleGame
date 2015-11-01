package com.gzfgeh.happytime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzfgeh.happytime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guzhenfu on 15/11/1.
 */
public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mDatas;

    public List<String> getData() {
        return mDatas;
    }

    public void setData(List<String> mDatas) {
        this.mDatas = mDatas;
    }

    public CommentRecyclerViewAdapter(Context context){
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.recycler_view_comment_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.test);
        }
    }
}

