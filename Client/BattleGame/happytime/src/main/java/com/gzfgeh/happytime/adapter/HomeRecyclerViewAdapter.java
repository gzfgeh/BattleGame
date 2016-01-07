package com.gzfgeh.happytime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.banner.transform.ZoomOutSlideTransformer;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.gzfgeh.happytime.APP;
import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.module.recyclerview.AsyncHttpHandler;
import com.gzfgeh.happytime.module.recyclerview.RecyclerViewItem;
import com.gzfgeh.happytime.module.banner.DataProvider;
import com.gzfgeh.happytime.module.banner.SimpleImageBanner;
import com.gzfgeh.happytime.widget.ZoomInEnter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by guzhenfu on 15/10/20.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_HEADER    = 0;
    private static final int TYPE_ITEM      = 1;
    private static final int TYPE_FOOTER    = 2;

    private Context context;
    private List<RecyclerViewItem> data;
    private RecyclerItemClickListener mListener;
    private RecyclerItemLongClickListener mLongListener;
    private BaseBanner.OnItemClickL mListenerL;
    private AsyncHttpClient mAsyncHttpClient;
    private AsyncHttpHandler handler;

    public List<RecyclerViewItem> getData() {
        return data;
    }

    public HomeRecyclerViewAdapter(Context context){
        data = new ArrayList<>();
        this.context = context;
        mAsyncHttpClient = APP.getAsyncHttpClientInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == TYPE_HEADER){
            View view = LayoutInflater.from(context).inflate(R.layout.list_view_header, viewGroup, false);
            return new VHHeader(view, mListenerL);
        }else if (i == TYPE_ITEM){
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, viewGroup, false);
            return new VHItem(view, mListener, mLongListener);
        } else if (i == TYPE_FOOTER){
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_footer_view, viewGroup, false);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new VHFooter(view);
        }
        throw new RuntimeException("there is no type that matches the type ");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof VHItem) {
            RecyclerViewItem dataItem = data.get(i - 1);
            ((VHItem) holder).title.setText(dataItem.mName);
            handler = new AsyncHttpHandler(((VHItem) holder).gifImageView);
            mAsyncHttpClient.get(dataItem.mGifViewUrl, null);

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
        return data.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        if (isPositionFooter(position))
            return TYPE_FOOTER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position){
        return position == data.size() + 1;
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void add(RecyclerViewItem item, int position) {
        data.add(position, item);
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
        private GifImageView gifImageView;
        private RecyclerItemClickListener mListener;
        private RecyclerItemLongClickListener mLongListener;

        public VHItem(View itemView, RecyclerItemClickListener listener,
                      RecyclerItemLongClickListener longListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.id_num);
            gifImageView = (GifImageView) itemView.findViewById(R.id.gif_view);
            mListener = listener;
            mLongListener = longListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            gifImageView.setOnClickListener(this);
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

    static class VHFooter extends RecyclerView.ViewHolder {

        public VHFooter(View itemView) {
            super(itemView);
        }
    }

    public interface RecyclerItemClickListener {
        public void onItemClick(View view,int position);
    }

    public interface RecyclerItemLongClickListener {
        public void onItemLongClick(View view,int position);
    }


}
