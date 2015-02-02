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
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PickerApp extends ActionBarActivity {
	private LinearLayout mContent;
	// Main Content View of ScrollView
	private PackageManager pm;
	// To Get App and icon of the apps
	private ScrollView sv;
	// Main Scroll View of App List
	private LinearLayout mSuppler;
	// Suppler of AlmasPicker

	// On the Tab this will be shown
	private boolean DEBUG = true;
	// To Control Debug of The App
	private String TAG_NAME = "AlmasPicker";
	// To Sent Logs with this TAG
	private String PickerGetter = "PickGet";
	// To Indentify in sharedpreferance
	private String CurGet;
	// To Get the Current Choose App-
	private static SharedPreferences sec;
	private static int CurSor;
	
	android.support.v7.app.ActionBar actionbar;
	
	
	

	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle RainOfAlmas) {
		
		super.onCreate(RainOfAlmas);
		
		com.pilockerstable.FontsOverride.setDefaultFont(this, "DEFAULT", "Roboto-Regular.ttf");
		com.pilockerstable.FontsOverride.setDefaultFont(this, "MONOSPACE", "Roboto-Regular.ttf");
		com.pilockerstable.FontsOverride.setDefaultFont(this, "SANS_SERIF", "Roboto-Regular.ttf");

		
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
		
		if (Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD || Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD_MR1) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		}
		else {
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		}

		
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
			
			pa.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// If You want to start the app
				
					Settings.System.putString(getContentResolver(), "PiSC"+CurSor, pkg);
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
	
	public class PickerAdapter extends LinearLayout {
		ImageView icon;
		TextView name;
		 LayoutParams pro = new LayoutParams(45,45);
		public PickerAdapter(Context context, AttributeSet attrs) {
			super(context, attrs);
			icon = new ImageView(context);
			icon.setLayoutParams(pro);
			icon.setPadding(6, 2, 15, 2);
			addView(icon);
			name = new TextView(context);
			name.setPadding(6, 15, 2, 2);
			name.setTextColor(Color.BLACK);
			addView(name);
		}
	public void setName(String n) {
		name.setText(n);
	}
	public void setIcon(Drawable d) {
		icon.setImageDrawable(d);
	}
	}
	public class Picker {
		String ss;
		Drawable dd;
		public Picker(String name ,Drawable d) {
			ss = name;
			dd = d;
		}
		public String getName(){
			return ss;
		}
		public Drawable getIcon() {
			return dd;
		}
	}
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
		int w = (di.widthPixels/2)+100;
		return w;
	}
	    public int getHeight() {
		DisplayMetrics di = getBaseContext().getResources().getDisplayMetrics();
		int h = (di.heightPixels/2)+110;
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
		return CurSor;
	}
	public static void setCurSor(int curSor) {
		CurSor = curSor;
	}

}
