package com.almas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.pilockerstable.R;

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
		actionBar.setTitle(Html
				.fromHtml("<font color='#ffffff'> <b> Shortcut </b> </font>"));
		actionBar.setDisplayHomeAsUpEnabled(true);

		mContent = new LinearLayout(this);
		mContent.setOrientation(LinearLayout.VERTICAL);
		mContent.setBackgroundColor(Color.WHITE);

		setContentView(mContent);

		for (int i = 0; i < CAPACITY; i++) {

			final int bb = i;

			final ShortCut txt = new ShortCut(getBaseContext(), null);

			final Handler h = new Handler();

			View v = new View(ShortcutSettings.this);

			LayoutParams vv = new LayoutParams(LayoutParams.MATCH_PARENT, 1);

			v.setBackgroundColor(0x33000000);
			v.setPadding(0, 5, 0, 5);

			mContent.addView(v, vv);

			String Sys = Settings.System.getString(getContentResolver(), "PiSC" + bb);
			ApplicationInfo ai = null;
			PackageManager pm;
			pm = getPackageManager();

			if (Sys != null) {

				try {

					ai = pm.getApplicationInfo(Sys, 0);
					String App_Name = (String) pm.getApplicationLabel(ai);
					Drawable Icon = pm.getApplicationIcon(ai);
					txt.setName(App_Name);
					txt.setImage(Icon);

				} catch (NameNotFoundException e) {

					e.printStackTrace();
				}

			}

			else if (Sys == null) {

			}

			txt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {

					Toast.makeText(getApplicationContext(), "Please Wait..",
							Toast.LENGTH_LONG).show();

					PickerApp.setCurSor(bb);

					startActivity(new Intent(ShortcutSettings.this,
							PickerApp.class));

				}
			});

			mContent.addView(txt);

		}

	}

}
