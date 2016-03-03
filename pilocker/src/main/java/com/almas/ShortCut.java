package com.almas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class ShortCut extends LinearLayout {
	
	 ImageView icon;
	 TextView name ;
	 LayoutParams pro = new LayoutParams(120,120);
	 
	public ShortCut(Context context, AttributeSet attrs) {
		
		super(context, attrs);
	
		icon = new ImageView(context);
	    icon.setLayoutParams(pro);
	    icon.setPadding(10, 6, 15, 6);
	    name = new TextView(context);
	    name.setTextSize(20);
	    name.setPadding(10, 18, 15, 2);
	    name.setTextColor(Color.BLACK);
	    name.setGravity(Gravity.CENTER_HORIZONTAL);
	    addView(icon);
	    addView(name);	
	    
	}
	
	
	public void setName(String named ) {
		name.setText(named);
	}
	
	
	
	public void setImage(Drawable d ) {
		icon.setImageDrawable(d);
	}
	
}
