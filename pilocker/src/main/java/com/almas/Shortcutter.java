package com.almas;

import android.graphics.drawable.Drawable;

class Shortcutter {
	String App_Name;
	Drawable Icon;

	public Shortcutter(String name, Drawable d) {
		this.App_Name = name;
		this.Icon = d;
	}

	public Drawable getIcon() {
		return Icon;
	}

	public String getName() {
		return App_Name;
	}
}