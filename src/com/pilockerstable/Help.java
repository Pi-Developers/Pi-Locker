package com.pilockerstable;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Help extends ActionBarActivity {

    Button btn;	
    android.support.v7.app.ActionBar actionbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_help);
		
		FontsOverride.setDefaultFont(this, "DEFAULT", "Roboto-Regular.ttf");
		FontsOverride.setDefaultFont(this, "MONOSPACE", "Roboto-Regular.ttf");
		FontsOverride.setDefaultFont(this, "SANS_SERIF", "Roboto-Regular.ttf");

		
		actionbar = getSupportActionBar();
		actionbar.setBackgroundDrawable(new ColorDrawable(0xff00BCD4));
		actionbar.setDisplayShowTitleEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		
		actionbar.setTitle(Html.fromHtml("<font color='#ffffff'> <b> Pi Locker guide </b> </font>"));

		btn = (Button) findViewById(R.id.button1);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {


				finish();
				
				
				
			}
		});
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

	
		return super.onOptionsItemSelected(item);
	}
}

