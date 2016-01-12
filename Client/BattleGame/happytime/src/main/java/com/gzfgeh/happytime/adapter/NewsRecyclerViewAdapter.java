package com.gzfgeh.happytime.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.banner.transform.ZoomOutSlideTransformer;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.gzfgeh.happytime.APP;
import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.beans.NewsBean;
import com.gzfgeh.happytime.module.banner.DataProvider;
import com.gzfgeh.happytime.module.banner.SimpleImageBanner;
import com.gzfgeh.happytime.module.recyclerview.AsyncHttpHandler;
import com.gzfgeh.happytime.utils.ImageLoaderUtils;
import com.gzfgeh.happytime.utils.LogUtils;
import com.gzfgeh.happytime.utils.NetWorkUtils;
import com.gzfgeh.happytime.widget.ZoomInEnter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by guzhenfu on 15/10/20.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_HEADER = 2;

    private Context context;
    private Fragment fragment;
    private List<NewsBean> data;
    private boolean mShowFooter = true;

    private RecyclerItemClickListener mListener;

    public NewsRecyclerViewAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;       //为了Glider
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_view_header, viewGroup, false);
            return new VHHeader(view, null);
        } else if (i == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, viewGroup, false);
            return new VHItem(view);
        } else if (i == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_footer_view, viewGroup, false);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new VHFooter(view);
        }
        throw new RuntimeException("there is no type that matches the type ");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof VHItem) {
            NewsBean bean = data.get(i);
            if (bean == null)
                return ;
            ((VHItem) holder).mTitle.setText(bean.getTitle());
            ((VHItem) holder).mDesc.setText(bean.getDigest());
            ImageLoaderUtils.display(fragment, ((VHItem) holder).mNewsImg, bean.getImgsrc());
        } else if (holder instanceof VHHeader) {
            ((VHHeader) holder).banner
                    .setSelectAnimClass(ZoomInEnter.class)
                    .setSource(DataProvider.getList())
                    .setTransformerClass(ZoomOutSlideTransformer.class)
                    .startScroll();
        }
    }

    public NewsBean getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter?1:0;
        if(data == null) {
            return begin;
        }
        return data.size() + begin;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if(!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setData(List<NewsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public boolean isShowFooter() {
        return mShowFooter;
    }

    public void setShowFooter(boolean mShowFooter) {
        this.mShowFooter = mShowFooter;
    }

    public void setListener(RecyclerItemClickListener mListener) {
        this.mListener = mListener;
    }

    public class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle;
        public TextView mDesc;
        public ImageView mNewsImg;

        public VHItem(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            mNewsImg = (ImageView) itemView.findViewById(R.id.ivNews);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null)
                mListener.onItemClick(v, getPosition());
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
        public ContentLoadingProgressBar progressBar;
        public TextView tvLoadFail;

        public VHFooter(View itemView) {
            super(itemView);
            progressBar = (ContentLoadingProgressBar) itemView.findViewById(R.id.progressbar);
            tvLoadFail = (TextView) itemView.findViewById(R.id.foot_load_fail);
            if (NetWorkUtils.isNetworkAvailable(APP.getContext())) {
                progressBar.setVisibility(View.VISIBLE);
                tvLoadFail.setVisibility(View.GONE);
                LogUtils.i("TAG", "net work");
            }else{
                progressBar.setVisibility(View.GONE);
                tvLoadFail.setVisibility(View.VISIBLE);
                LogUtils.i("TAG", "net work is not available");
            }
        }
    }

    public interface RecyclerItemClickListener {
        void onItemClick(View view, int position);
    }


}
