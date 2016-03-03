package com.almas;

import android.graphics.drawable.Drawable;

/**
 * @author rashad
 * 
 */


/**
 * 
 * Class used to get the name and picture of apps in list
 * 
 * 
 **/


public class Picker {
	
	String ss;
	Drawable dd;
	
	
	public Picker(String name ,Drawable d) {
		
		ss = name;
		dd = d;
	}
	
	
	//get string name
	public String getName(){
		return ss;
	}
	
	
	//get app icon
	public Drawable getIcon() {
		return dd;
	}
	

}
