package com.gzfgeh.happytime.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.banner.transform.ZoomOutSlideTransformer;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.module.banner.DataProvider;
import com.gzfgeh.happytime.module.banner.SimpleImageBanner;
import com.gzfgeh.happytime.ui.activity.BannerActivityOne;
import com.gzfgeh.happytime.widget.ZoomInEnter;

import java.util.List;

/**
 * Created by guzhenfu on 15/10/20.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private Context context;
    private List<String> data;

    public HomeRecyclerViewAdapter(Context context, List<String> data){
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == TYPE_HEADER){
            View view = LayoutInflater.from(context).inflate(R.layout.list_view_header, viewGroup, false);
            return new VHHeader(view);
        }else if (i == TYPE_ITEM){
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, viewGroup, false);
            return new VHItem(view);
        }
        throw new RuntimeException("there is no type that matches the type ");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof VHItem) {
            String dataItem = data.get(i);
            ((VHItem) holder).title.setText(dataItem);
        } else if (holder instanceof VHHeader) {
            ((VHHeader) holder).banner
            .setSelectAnimClass(ZoomInEnter.class)
                    .setSource(DataProvider.getList())
                    .setTransformerClass(ZoomOutSlideTransformer.class)
                    .startScroll();
            ((VHHeader) holder).banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                @Override
                public void onItemClick(int i) {
                    switch (i) {
                        case 0:

                        case 1:
                            break;
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private String getItem(int position) {
        return data.get(position - 1);
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void add(String text, int position) {
        data.add(position, text);
        notifyItemInserted(position);
    }

    static class VHItem extends RecyclerView.ViewHolder {
        TextView title;

        public VHItem(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.id_num);
        }
    }

    static class VHHeader extends RecyclerView.ViewHolder {
        SimpleImageBanner banner;

        public VHHeader(View itemView) {
            super(itemView);
            banner = (SimpleImageBanner) itemView.findViewById(R.id.banner);
        }
    }
}
