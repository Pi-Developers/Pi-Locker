package com.almas;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

public class PickerApp extends ActionBarActivity {
	
	// Main Content View of ScrollView
	private LinearLayout mContent;
	// To Get App and icon of the apps
	private PackageManager pm;
	// Main Scroll View of App List
	private ScrollView sv;
	// Suppler of AlmasPicker
	private LinearLayout mSuppler;	
	// To Control Debug of The App
	private boolean DEBUG = true;
	// To Sent Logs with this TAG
	private String TAG_NAME = "AlmasPicker";
	// To Indentify in sharedpreferance
	private String PickerGetter = "PickGet";
	// To Get the Current Choose App-
	private String CurGet;
	//Identify Shared Preferences
	private static SharedPreferences sec;
	//Identify Cursor
	private static int Cursor;
	//Identify ActionBar
	android.support.v7.app.ActionBar actionbar;

	
	@Override
	protected void onCreate(Bundle RainOfAlmas) {
		
		super.onCreate(RainOfAlmas);
		
			
		actionbar = getSupportActionBar();
		actionbar.setBackgroundDrawable(new ColorDrawable(0xff00BCD4));
		actionbar.setDisplayShowTitleEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setTitle(Html.fromHtml("<font color='#ffffff'> <b> Choose an app </b> </font>"));
	    actionbar.setDisplayHomeAsUpEnabled(true);
        
		mContent = new LinearLayout(this);
		mContent.setOrientation(LinearLayout.VERTICAL);
		mContent.setVerticalScrollBarEnabled(false);

		mSuppler = new LinearLayout(this);
		mSuppler.setOrientation(LinearLayout.VERTICAL);
		
		sec = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		sv = new ScrollView(getBaseContext());
		sv.addView(mContent);
		
		mSuppler.addView(sv);
		
		setContentView(mSuppler);
		
		
		SharedPreferences sp = getSharedPreferences("AlmasPicker", Context.MODE_PRIVATE);
		CurGet = sp.getString(PickerGetter, "");
		
		
		if (DEBUG) {
			
			if (CurGet == null | CurGet == "") {
				
				Log.e(TAG_NAME, "This is First View of AlmasPicker");
				Log.e(TAG_NAME, "Beause No Saved Data");
				
			}
			
			else {
				
				Log.i(TAG_NAME, "Saved Data Has Found");
				Log.d(TAG_NAME, "Package Name is "+CurGet);
			}
		}
		
		
		ArrayList<ResolveInfo> apps = getApps();
		
		for (int i = 0; i < apps.size(); i++) {
			
			ResolveInfo rInfo = apps.get(i);
			final Picker pick;
			final String pkg;
			PickerAdapter pa = new PickerAdapter(getBaseContext(), null);
			String ss = rInfo.activityInfo.loadLabel(pm).toString();
			Drawable dd = rInfo.activityInfo.loadIcon(pm);
			pick = new Picker(ss,dd);
			pa.setName(pick.getName());
			pa.setIcon(pick.getIcon());
			pkg = rInfo.activityInfo.packageName.toString();

			
			View v = new View(this);
			LayoutParams vv = new LayoutParams(LayoutParams.MATCH_PARENT ,1);

			v.setBackgroundColor(0x33000000);
			v.setPadding(0, 5, 0, 5);
			mContent.addView(v, vv );
			
			pa.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					// If You want to start the app
					Settings.System.putString(getContentResolver(), "PiSC"+ Cursor, pkg);
					save("pkg",pkg);
					
					finish();
					
					
				}
					
			});
			mContent.addView(pa);
		}
	}

	
	public void save(String key, String value) {

		Editor edit = sec.edit();
		edit.putString(key, value);
		edit.commit();

	}
	

	
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onAttachedToWindow() {
		
		super.onAttachedToWindow();

		  View view = getWindow().getDecorView();  
		  view.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
	      WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();  

	     lp.gravity = Gravity.CENTER;  
	     lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
	     lp.height = android.view.ViewGroup.LayoutParams.FILL_PARENT;

	     getWindowManager().updateViewLayout(view, lp);
	}
	
	
	    public int getWidth() {
		DisplayMetrics di = getBaseContext().getResources().getDisplayMetrics();
		int w = (di.widthPixels/2) + 100;
		return w;
	}
	    
	    
	    public int getHeight() {
	    	
		DisplayMetrics di = getBaseContext().getResources().getDisplayMetrics();
		int h = (di.heightPixels/2) + 110;
		return h;
		
	}
	    
	    
	    
	    
	private ArrayList<ResolveInfo> getApps(){

		pm = getPackageManager();

		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		ArrayList<ResolveInfo> list = (ArrayList<ResolveInfo>) pm.queryIntentActivities(intent, PackageManager.GET_META_DATA);
		
		return list;
	}
	
	public static int getCurSor() {
		return Cursor;
	}
	public static void setCurSor(int curSor) {
		Cursor = curSor;
	}

}
