package com.almas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PickerAdapter extends LinearLayout {

	ImageView icon;
	TextView name;
	LayoutParams pro = new LayoutParams(120, 120);

	public PickerAdapter(Context context, AttributeSet attrs) {

		super(context, attrs);
		icon = new ImageView(context);
		icon.setLayoutParams(pro);
		icon.setPadding(6, 5, 15, 5);
		addView(icon);
		name = new TextView(context);
		name.setPadding(8, 15, 2, 2);
		name.setTextColor(Color.BLACK);
		name.setTextSize(20);
		addView(name);

	}

	public void setName(String n) {
		name.setText(n);
	}

	public void setIcon(Drawable d) {
		icon.setImageDrawable(d);
	}

}