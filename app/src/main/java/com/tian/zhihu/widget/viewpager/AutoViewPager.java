package com.tian.zhihu.widget.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自动轮播，无限循环，支持第三方indicator
 * 需要注意的是本view的adapter实际是myPagerAdapter,刷新方法用AutoViewPager.NotifyDataSetChanged();
 */
public class AutoViewPager extends ChildViewPager {

	/**AutoViewPager真正的适配器*/
	private PagerAdapter myPagerAdapter;
	/**为第三方indicator准备的假适配,自己不要使用*/
	private PagerAdapter pagerAdapter;

	private static final int START_AUTO_SCROLL = 0x01;
	private static final int STOP_AUTO_SCROLL = 0x02;
	private static final int HANDLE_AUTO_SCROLL = 0x3;
	/**外部开关,是否开启自动轮播*/
	private boolean _autoSlide = false;
	/**内部开关,是否自动轮播*/
	private boolean auto;
	/**自动轮播间隔时间*/
	private static final int TIME_SLOT = 4000;
	private Handler timer = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case START_AUTO_SCROLL:
				auto = true;
				Message start = obtainMessage(HANDLE_AUTO_SCROLL);
				sendMessageDelayed(start, TIME_SLOT);
				break;

			case STOP_AUTO_SCROLL:
				auto = false;
				removeMessages(HANDLE_AUTO_SCROLL);
				break;

			case HANDLE_AUTO_SCROLL:
				int c = getCurrentItem();
				c++;
				setCurrentItem(c, true);
				if (auto) {
					Message scroll = obtainMessage(HANDLE_AUTO_SCROLL);
					sendMessageDelayed(scroll, TIME_SLOT);
				}
				break;
			}
		}

	};

	public void setAutoSlide(boolean auto) {
		this._autoSlide = auto;
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		if (_autoSlide) {
			timer.obtainMessage(START_AUTO_SCROLL).sendToTarget();
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		timer.obtainMessage(STOP_AUTO_SCROLL).sendToTarget();
	}

	public AutoViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * AutoViewPager真正的适配器
	 */
	private class MyPagerAdapter extends PagerAdapter {
		private PagerAdapter pa;

		public MyPagerAdapter(PagerAdapter pa) {
			this.pa = pa;
		}

		@Override
		public int getCount() {
			return pa.getCount() > 1 ? pa.getCount() + 2 : pa.getCount();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			if (position == 0) {
				return pa.instantiateItem(container, pa.getCount() - 1);
			} else if (position == pa.getCount() + 1) {
				return pa.instantiateItem(container, 0);
			} else {
				return pa.instantiateItem(container, position - 1);
			}
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return pa.isViewFromObject(arg0, arg1);
		}
	}

	private class MyOnPageChangeListener implements OnPageChangeListener {
		private OnPageChangeListener listener;

		public MyOnPageChangeListener(OnPageChangeListener listener) {
			this.listener = listener;
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			listener.onPageScrollStateChanged(state);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			listener.onPageScrolled(getRealPosition(position), positionOffset,
					positionOffsetPixels);
		}

		@Override
		public void onPageSelected(int position) {
			int realPosition = getRealPosition(position);
			setCurrentItem(realPosition, false);
			listener.onPageSelected(realPosition);
		}

	}

	/**
	 * 获取实际适配器中的position
	 * @param position
	 * @return
	 */
	private int getRealPosition(int position) {
		int realPosition = position;
		if (position == 0) {
			realPosition = pagerAdapter.getCount() - 1;
		} else if (position == pagerAdapter.getCount() + 1) {
			realPosition = 0;
		} else {
			realPosition = position - 1;
		}
		return realPosition;
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		super.setOnPageChangeListener(listener == null ? null
				: new MyOnPageChangeListener(listener));
	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		myPagerAdapter = adapter == null ? null : new MyPagerAdapter(adapter);
		super.setAdapter(myPagerAdapter);

		if (adapter != null && adapter.getCount() > 1) {//adapter.getCount() > 1避免只有一条的时候setCurrentItem越界
			setCurrentItem(0, false);
		}
		this.pagerAdapter = adapter;
	}

	@Override
	public PagerAdapter getAdapter() {
		return pagerAdapter;
	}

	public PagerAdapter getMyPagerAdapter() {
		return myPagerAdapter;
	}

	@Override
	public int getCurrentItem() {
		return super.getCurrentItem() - 1;
	}

	@Override
	public void setCurrentItem(int item) {
		// TODO Auto-generated method stub
		setCurrentItem(item, false);
	}

	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		super.setCurrentItem(item + 1, smoothScroll);
	}

	/**
	 * 适配器数据刷新本view,不要用外部的adapter刷新
	 */
	public void NotifyDataSetChanged() {
		myPagerAdapter.notifyDataSetChanged();
		if (pagerAdapter != null && pagerAdapter.getCount() > 1) {//adapter.getCount() > 1避免只有一条的时候setCurrentItem越界
			setCurrentItem(0, false);
		}
	}

}
