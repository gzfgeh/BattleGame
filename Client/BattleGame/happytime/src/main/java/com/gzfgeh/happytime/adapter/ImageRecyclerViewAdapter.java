package com.gzfgeh.happytime.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.beans.ImageBean;
import com.gzfgeh.happytime.utils.ImageLoaderUtils;
import com.gzfgeh.happytime.utils.Utils;

import java.util.List;

/**
 * Description:
 * Created by guzhenfu on 16/1/13.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ItemViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_HEADER = 2;

    private Context context;
    private List<ImageBean> datas;
    private int mMaxWidth;
    private int mMaxHeight;
    private Fragment fragment;

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setDatas(List<ImageBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public ImageRecyclerViewAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        mMaxWidth = Utils.getWidthInPx(context) - 20;
        mMaxHeight = Utils.getHeightInPx(context) - Utils.getStatusHeight(context) -
                Utils.dip2px(context, 96);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ImageBean bean = datas.get(position);
        if (bean == null)
            return;

        holder.textView.setText(bean.getTitle());
        float scale = (float)bean.getWidth() / (float) mMaxWidth;
        int height = (int)(bean.getHeight() / scale);
        if(height > mMaxHeight) {
            height = mMaxHeight;
        }
        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(mMaxWidth, height));
        ImageLoaderUtils.display(fragment, holder.imageView, bean.getThumburl());
    }

    @Override
    public int getItemCount() {
        if (datas == null)
            return 0;
        return datas.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvTitle);
            imageView = (ImageView) itemView.findViewById(R.id.ivImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
