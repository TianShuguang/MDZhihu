package com.tian.zhihu.widget.viewpager;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;


public class ChildViewPager extends ViewPager {

	/** 触摸时按下的点 **/
	PointF downP = new PointF();
	/** 触摸时当前的点 **/
	PointF curP = new PointF();
	OnSingleTouchListener onSingleTouchListener;
	private int mTouchSlop;
	
	public ChildViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		final ViewConfiguration vc = ViewConfiguration.get(context);
	     mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(vc);
	}

	public ChildViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		final ViewConfiguration vc = ViewConfiguration.get(context);
	     mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(vc);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		// 当拦截触摸事件到达此位置的时候，返回true，
		// 说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
		/*curP.x = arg0.getX();
		curP.y = arg0.getY();

		RectF r = new RectF(0, 0, getWidth(), getHeight());
		
		boolean isIntercept = super.onInterceptTouchEvent(arg0);
		if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
			if(r.contains(curP.x, curP.y)) {
				getParent().requestDisallowInterceptTouchEvent(true);
				return isIntercept;
			}
		}
		*/

		switch (arg0.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downP.x = arg0.getX();
			downP.y = arg0.getY();
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_MOVE:
			curP.x = arg0.getX();
			curP.y = arg0.getY();
			if(Math.abs(curP.x-downP.x)<Math.abs(curP.y-downP.y))
				getParent().requestDisallowInterceptTouchEvent(false);
			else
				getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_UP:
			
			break;

		default:
			break;
		}
		return super.onInterceptTouchEvent(arg0);
	}
	
	private ViewParent getPagerParent(View v, int id) {
		if(((View)v.getParent()).getId() == id) {
			return v.getParent();
		}
		return getPagerParent((View)v.getParent(), id);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		// 每次进行onTouch事件都记录当前的按下的坐标
		/*curP.x = arg0.getX();
		curP.y = arg0.getY();

		if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
			// 记录按下时候的坐标
			// 切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
			downP.x = arg0.getX();
			downP.y = arg0.getY();
			// 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
			getParent().requestDisallowInterceptTouchEvent(true);
		}

		if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
			// 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
			getParent().requestDisallowInterceptTouchEvent(true);
		}

		if (arg0.getAction() == MotionEvent.ACTION_UP) {
			// 在up时判断是否按下和松手的坐标为一个点
			// 如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
			if (downP.x == curP.x && downP.y == curP.y) {
				onSingleTouch(getCurrentItem());
				return true;
			}
		}*/

		return super.onTouchEvent(arg0);
	}

	/**
	 * 单击
	 */
	public void onSingleTouch(int position) {
		if (onSingleTouchListener != null) {

			onSingleTouchListener.onSingleTouch(position);
		}
	}

	/**
	 * 创建点击事件接口
	 * 
	 * @author wanpg
	 * 
	 */
	public interface OnSingleTouchListener {
		public void onSingleTouch(int position);
	}

	public void setOnSingleTouchListener(
			OnSingleTouchListener onSingleTouchListener) {
		this.onSingleTouchListener = onSingleTouchListener;
	}

}
