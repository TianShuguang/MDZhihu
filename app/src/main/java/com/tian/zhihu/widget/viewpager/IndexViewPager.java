package com.tian.zhihu.widget.viewpager;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.tian.zhihu.R;
import com.tian.zhihu.base.BitmapCache;
import com.tian.zhihu.network.VolleyQueueController;
import com.tian.zhihu.network.bean.LatestTopStory;
import com.tian.zhihu.utils.ValueUtils;
import com.viewpagerindicator.CirclePageIndicator;

public class IndexViewPager extends LinearLayout {
	private AutoViewPager mPager;
	private CirclePageIndicator mIndicator;

	public ArrayList<LatestTopStory> stories = new ArrayList<LatestTopStory>();

	private OnChildClickListener listener;
	private Context mContext;

	private RequestQueue mQueue;
	private ImageLoader mImageLoader;

	public IndexViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public IndexViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	private void initView(Context context) {
		this.mContext = context;
		this.mQueue= VolleyQueueController.getInstance(mContext).getRequestQueue();
		mImageLoader=new ImageLoader(mQueue,new BitmapCache());
		View view = LayoutInflater.from(context).inflate(
				R.layout.index_view_pager, this);
		mPager = (AutoViewPager) view.findViewById(R.id.index_pager);
		mPager.setAdapter(new ViewPagerAdapter());
		mPager.setAutoSlide(true);
		mIndicator = (CirclePageIndicator) view
				.findViewById(R.id.index_indicator);
		mIndicator.setSnap(true);
		mIndicator.setViewPager(mPager);
		setPagerDefaultHeight();
	}

	private void setPagerDefaultHeight() {
		ViewGroup.LayoutParams params = mPager.getLayoutParams();
		int w = getResources().getDisplayMetrics().widthPixels;
		int h = w*2/3;
		params.width = w;
		params.height = h;
		mPager.setLayoutParams(params);
	}

	public void setOnChildClickListener(OnChildClickListener l) {
		this.listener = l;
	}

	public void setData(ArrayList<LatestTopStory> stories) {
		this.stories.clear();
		this.stories.addAll(stories);
		mIndicator.setVisibility(stories.size()>1?View.VISIBLE:View.INVISIBLE);
		mPager.NotifyDataSetChanged();
	}

	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return stories.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView((ImageView) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
			layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
			layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
			View view=LayoutInflater.from(mContext).inflate(R.layout.news_header, null, false);

			NetworkImageView imageView= (NetworkImageView) view.findViewById(R.id.theme_image);
			TextView theme_desc= (TextView) view.findViewById(R.id.theme_desc);
			((ViewPager) container).addView(view, layoutParams);
			final LatestTopStory story = stories.get(position);
			theme_desc.setText(""+story.title);
			if (ValueUtils.isStrNotEmpty(story.image)) {
				imageView.setImageUrl(story.image, new ImageLoader(mQueue, new BitmapCache()));
			}
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goByAction(story);
				}
			});
			return view;
		}

	}

	private void goByAction(final LatestTopStory story) {
		if (listener != null) {
			try {
				listener.onClick(story);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
