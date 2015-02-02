package com.almas;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShortcutSettings extends ActionBarActivity {
	private int CAPACITY = 4;
	private LinearLayout mContent;
	 android.support.v7.app.ActionBar actionBar;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);

		actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(0xff00BCD4));
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(Html.fromHtml("<font color='#ffffff'> <b> Shortcut </b> </font>"));
	    actionBar.setDisplayHomeAsUpEnabled(true);

		com.pilockerstable.FontsOverride.setDefaultFont(this, "DEFAULT", "Roboto-Regular.ttf");
		com.pilockerstable.FontsOverride.setDefaultFont(this, "MONOSPACE", "Roboto-Regular.ttf");
		com.pilockerstable.FontsOverride.setDefaultFont(this, "SANS_SERIF", "Roboto-Regular.ttf");

		
		mContent = new LinearLayout(this);
		mContent.setOrientation(LinearLayout.VERTICAL);
		mContent.setBackgroundColor(Color.WHITE);
		setContentView(mContent);
		
		for (int i = 0 ; i < CAPACITY ; i++) {
			final int bb = i;
			
			final ShortCut txt = new ShortCut(getBaseContext(), null);
			
			 final Handler h = new Handler();
		       h.post(new Runnable() {
		      @Override
		      public void run() {
		    		String Sys = Settings.System.getString(getContentResolver(), "PiSC"+bb);
		    		ApplicationInfo ai = null;
		    		PackageManager pm;
		    		pm = getPackageManager();
		    		
					if (Sys != null) {
						try  {
							 ai = pm.getApplicationInfo(Sys, 0);
						} catch (NameNotFoundException e) {
							e.printStackTrace();
						}
						String App_Name = (String) pm.getApplicationLabel(ai);
						Drawable Icon = pm.getApplicationIcon(ai);
						txt.setName(App_Name);
						txt.setImage(Icon);
					
					}
					else if (Sys == null){
					
					}
		          h.postDelayed(this, 1000);
		      }
		           }); 
		
		
			txt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					
					PickerApp.setCurSor(bb);
				
					startActivity(new Intent(ShortcutSettings.this , PickerApp.class));
					
				}
			});
			
			mContent.addView(txt);
			
		}
			
		}
	class ShortCut extends LinearLayout {
		
		 ImageView icon;
		 TextView name ;
		 LayoutParams pro = new LayoutParams(60,60);
		 
		public ShortCut(Context context, AttributeSet attrs) {
			
			super(context, attrs);
			   icon = new ImageView(context);
		    icon.setLayoutParams(pro);
		    icon.setPadding(10, 2, 15, 2);
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
	
}
