/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.gzfgeh.happytime.adapter;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.module.BitmapCache;

/**
 * @Description: 图片适配器
 * @author http://blog.csdn.net/finddreams
 */ 
public class ImagePagerAdapter extends BaseAdapter {

	private Context context;
	private List<String> imageIdList;
	private List<String> linkUrlArray;
	private List<String> urlTitlesList;
	private int size;
	private boolean isInfiniteLoop;
	private ImageLoader mImageLoader;

	public ImagePagerAdapter(Context context, List<String> imageIdList,
			List<String> urlList, List<String> urlTitlesList) {
		this.context = context;
		this.imageIdList = imageIdList;
		if (imageIdList != null) {
			this.size = imageIdList.size();
		}
		this.linkUrlArray = urlList;
		this.urlTitlesList = urlTitlesList;
		isInfiniteLoop = false;

		RequestQueue mQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
	}

	@Override
	public int getCount() {
		// Infinite loop
		return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
	}

	/**
	 * get really position
	 * 
	 * @param position
	 * @return
	 */
	private int getPosition(int position) {
		return isInfiniteLoop ? position % size : position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup container) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = holder.imageView = new NetworkImageView(context);
			holder.imageView
					.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
			holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.imageView.setDefaultImageResId(R.drawable.ic_add);
		holder.imageView.setErrorImageResId(R.drawable.ic_add);
		holder.imageView.setImageUrl(this.imageIdList.get(getPosition(position)), mImageLoader);

		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String url = linkUrlArray.get(ImagePagerAdapter.this
						.getPosition(position));
				String title = urlTitlesList.get(ImagePagerAdapter.this
						.getPosition(position));
				/*
				 * if (TextUtils.isEmpty(url)) {
				 * holder.imageView.setEnabled(false); return; }
				 */
				Bundle bundle = new Bundle();

				bundle.putString("url", url);
				bundle.putString("title", title);

//				Intent intent = new Intent(context, BaseWebActivity.class);
//				intent.putExtras(bundle);
//				context.startActivity(intent);

				Toast.makeText(context, "点击了第" + getPosition(position) + "美女", Toast.LENGTH_SHORT).show();

			}
		});

		return view;
	}

	private static class ViewHolder {

		NetworkImageView imageView;
	}

	/**
	 * @return the isInfiniteLoop
	 */
	public boolean isInfiniteLoop() {
		return isInfiniteLoop;
	}

	/**
	 * @param isInfiniteLoop
	 *            the isInfiniteLoop to set
	 */
	public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}
