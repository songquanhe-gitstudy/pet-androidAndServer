package com.song.petLeague.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/*
 * 让ViewPAger不会左右滑动
 */
public class NOHorizontalScrollViewPager extends ViewPager {

	public NOHorizontalScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NOHorizontalScrollViewPager(Context context) {
		super(context);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
//		return super.onTouchEvent(arg0);
		return true;  //return true说明没有用父类的方法  直接返回为空
	}
	
	//不拦子ViewPager的事件
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}
}








