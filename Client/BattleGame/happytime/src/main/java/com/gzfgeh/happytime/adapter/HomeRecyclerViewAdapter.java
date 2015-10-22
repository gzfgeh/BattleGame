package com.gzfgeh.happytime.adapter;

import android.content.Context;
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
    private RecyclerItemClickListener mListener;
    private RecyclerItemLongClickListener mLongListener;
    private BaseBanner.OnItemClickL mListenerL;

    public HomeRecyclerViewAdapter(Context context, List<String> data){
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == TYPE_HEADER){
            View view = LayoutInflater.from(context).inflate(R.layout.list_view_header, viewGroup, false);
            return new VHHeader(view, mListenerL);
        }else if (i == TYPE_ITEM){
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, viewGroup, false);
            return new VHItem(view, mListener, mLongListener);
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

    public void setListener(RecyclerItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void setLongListener(RecyclerItemLongClickListener mLongListener) {
        this.mLongListener = mLongListener;
    }

    public void setListenerL(BaseBanner.OnItemClickL mListenerL) {
        this.mListenerL = mListenerL;
    }

    static class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView title;
        private RecyclerItemClickListener mListener;
        private RecyclerItemLongClickListener mLongListener;

        public VHItem(View itemView, RecyclerItemClickListener listener,
                      RecyclerItemLongClickListener longListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.id_num);
            mListener = listener;
            mLongListener = longListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null)
                mListener.onItemClick(v, getPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongListener != null)
                mLongListener.onItemLongClick(v, getPosition());
            return true;
        }
    }

    static class VHHeader extends RecyclerView.ViewHolder implements BaseBanner.OnItemClickL {
        private SimpleImageBanner banner;
        private BaseBanner.OnItemClickL mListener;

        public VHHeader(View itemView, BaseBanner.OnItemClickL listener) {
            super(itemView);
            banner = (SimpleImageBanner) itemView.findViewById(R.id.banner);
            mListener = listener;
            banner.setOnItemClickL(this);
        }

        @Override
        public void onItemClick(int i) {
            if (mListener != null)
                mListener.onItemClick(i);
        }
    }

    public interface RecyclerItemClickListener {
        public void onItemClick(View view,int postion);
    }

    public interface RecyclerItemLongClickListener {
        public void onItemLongClick(View view,int postion);
    }
}
