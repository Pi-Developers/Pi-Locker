package com.almas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pilockerstable.LockerService;
import com.pilockerstable.PinActivity;

public class ShortcutView extends LinearLayout {
	private int CAPACITY = 4;
	private PackageManager pm;
	SharedPreferences sec;
	String pkg,pin;
	public ShortcutView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		pm = context.getPackageManager();
		
		sec = PreferenceManager.getDefaultSharedPreferences(getContext());

		pkg = sec.getString("pkg", "");
		pin = sec.getString("pin", "");		
		
		
		for (int i = 0; i < CAPACITY; i++) {
			final int b = i;
			ApplicationInfo ai = null;
			String sv = null;
			String s = Settings.System.getString(context.getContentResolver(), "PiSC" + i);

			if (s == null | s == "") {

				Settings.System.putString(context.getContentResolver(), "PiSC"+b, "com.pilockerstable");
			}
			sv = s;
			ShortCut sc = new ShortCut(context, attrs);
			
			try {
				ai = pm.getApplicationInfo(sv, 0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			try{
			String App_Name = (String) pm.getApplicationLabel(ai);
			Drawable Icon = pm.getApplicationIcon(ai);
			Shortcutter st = new Shortcutter(App_Name, Icon);
			sc.setName(st.getName());
			sc.setImage(st.getIcon());			
			addView(sc);
			
			}catch(Exception e){
				Settings.System.putString(context.getContentResolver(), "PiSC"+b, "com.pilockerstable");
			}
			
			sc.setOnClickListener(new View.OnClickListener() {
				
				String sv = Settings.System.getString( context.getContentResolver(), "PiSC" + b);

				@Override 
				public void onClick(View arg0) {

					if( pin.equals("") || pin.equals(null) || pin.isEmpty()) {
						
						((Activity) context).finish();
						Intent i = new Intent();
						i.setClass(getContext(), LockerService.class);
						context.startService(i);
						Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(sv);
						context.startActivity(LaunchIntent);
						
						
					}else{
					
					save("pkg", "true");
					Intent x = new Intent(getContext(), PinActivity.class);
					x.putExtra("sv", sv);
					context.startActivity(x);
			
					
					}
				}
			});
		}
	}

	
	public void save(String key, String value) {

		Editor edit = sec.edit();
		edit.putString(key, value);
		edit.commit();

	}
	
	
	class ShortCut extends LinearLayout {
		ImageView icon;
		TextView name;
		LayoutParams pro;

		public ShortCut(Context context, AttributeSet attrs) {
			super(context, attrs);
			
			setOrientation(LinearLayout.VERTICAL);
			setGravity(Gravity.CENTER_HORIZONTAL);
			icon = new ImageView(context);
			name = new TextView(context);
			
			
			double density = getResources().getDisplayMetrics().density;
			
			
			if (density >= 4.0) {
			   //"xxxhdpi";
				pro = new LayoutParams(250, 250);
				icon.setPadding(15, 30, 15, 15);

				
			}
			
			
			if (density >= 3.0 && density < 4.0) {
			   //xxhdpi
				pro = new LayoutParams(200, 200);
				icon.setPadding(12, 20, 12, 12);
				name.setTextSize(17);

			}
			
			
			if (density >= 2.0) {
				
			   //xhdpi
				pro = new LayoutParams(150, 150);
				icon.setPadding(10, 18, 10, 10);
				name.setTextSize(14);

			}
			
			
			if (density >= 1.5 && density < 2.0) {
				
			   //hdpi
				pro = new LayoutParams(100, 100);
				icon.setPadding(10, 18, 10, 10);
				name.setTextSize(12);

			}
			if (density >= 1.0 && density < 1.5) {
				
			   //mdpi
				pro = new LayoutParams(65, 65);
				icon.setPadding(10, 18, 10, 10);
				name.setTextSize(10);

				
			}
			
			if(density >= 0.75 && density < 1.0){
				//ldpi
				pro = new LayoutParams(40, 40);
				icon.setPadding(5, 9, 5, 5);
				name.setTextSize(10);

			}
			
			
			
		
			icon.setLayoutParams(pro);
			addView(icon);

			
			name.setTextSize(10);
			name.setTextColor(Color.WHITE);
			name.setGravity(Gravity.CENTER_HORIZONTAL);
			addView(name);
			
		}

		
		public void setName(String named) {
			name.setText(named);
		}

		
		public void setImage(Drawable d) {
			icon.setImageDrawable(d);
		}
		
		

	}


}
