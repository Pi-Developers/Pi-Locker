package com.pilockerstable;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 *
 * @author rashad
 *
 */


/**
 * 
 * This extension of viewGroup is to block touch events by "onInterceptTouchEvent"
 * 
 * */

  public class customViewGroup extends ViewGroup {


	
	
	public customViewGroup(Context context) {
		super(context);
	}

	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	}

	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}
	
	
	
}
