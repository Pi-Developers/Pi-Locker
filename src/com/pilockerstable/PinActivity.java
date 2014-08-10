package com.pilockerstable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PinActivity extends Activity {
	SharedPreferences sec;
	EditText unlock;
	Button button1,button2;
	TextView TextView1,battery, Date, Time, text;
	int X = 0;
	private Handler mainhandler;
	private HomeKeyLocker mHomeKeyLocker;
	String  txt, img, color, colorbg;
	Bitmap bmImg;
	int col, colbg;
    SharedPreferences spfs;
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		sec = PreferenceManager.getDefaultSharedPreferences(this);
		String XYZ = sec.getString("XYZ", "");
		final String pass = sec.getString("pass", "");
		final String pin = sec.getString("pin", "");
		loadlock();
		
		if (XYZ.equals("true")) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		} else {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			}

		
		if(!pass.equals("") ){
			setContentView(R.layout.password);
			unlock = (EditText) findViewById(R.id.pass);
			X++;
			
		}
		
		else if (!pin.equals("")){
			setContentView(R.layout.pin);
			unlock = (EditText) findViewById(R.id.pin);
			}
		
		
		button1 = (Button)findViewById(R.id.button1);
		button2 = (Button)findViewById(R.id.button2);
		TextView1 = (TextView)findViewById(R.id.textView1);
		battery = (TextView) findViewById(R.id.battery);
		Date = (TextView) findViewById(R.id.date);
		text = (TextView) findViewById(R.id.texts);
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(this);
		
		
		final View activityRootView = findViewById(R.id.activityRoot);
		activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		    @Override
		    public void onGlobalLayout() {
		        int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
		        if (heightDiff < 100 ) {

		        mHomeKeyLocker.lock(PinActivity.this);
		        
		        }
		     }
		});

		
		unlock.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
		unlock.setOnTouchListener(new View.OnTouchListener(){
		    public boolean onTouch(View view, MotionEvent motionEvent) {                                                       

		    	mHomeKeyLocker.unlock();
		    	
		    	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);                
		         return false;
		    }
		});
		

		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			String unlocker = unlock.getText().toString();
			String Browser = sec.getString("browser", "");	
			String camera = sec.getString("camera", "");
				if(X==1){
					
					if(unlocker.equals(pass)){
						
					 if(unlocker.equals(pass)){
						 finish();
					 }else if (unlocker.equals(pass) && Browser.equals("true") ){
						 
							Uri uri = Uri.parse("http://www.google.com");
							Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							startActivity(intent);
							
							save("browser","");
							
							finish();	
						 
					 }else if(unlocker.equals(pass) && camera.equals("true")){
						 
						 Intent intent = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							startActivityForResult(intent, 0);	
							save("camera","");

							finish();	

							
					 }
					}
					
				else{
					
					Toast.makeText(getApplicationContext(), "Wrong password try again", Toast.LENGTH_SHORT).show();
					unlock.setText("");
				}	
			}
				else{
					
					 unlocker = unlock.getText().toString();
					 
					 if(unlocker.equals(pin)){
							
							
						 if(unlocker.equals(pin)){
							 finish();
						 }else if (unlocker.equals(pin) && Browser.equals("true") ){
							 
								Uri uri = Uri.parse("http://www.google.com");
								Intent intent = new Intent(Intent.ACTION_VIEW, uri);
								startActivity(intent);
								
								save("browser","");
								
								finish();	
							 
						 }else if(unlocker.equals(pin) && camera.equals("true")){
							 
							 Intent intent = new Intent(
										"android.media.action.IMAGE_CAPTURE");
								startActivityForResult(intent, 0);	
								save("camera","");

								finish();	

								
						 }						
					 }
						
					else{
						
						Toast.makeText(getApplicationContext(), "Wrong PIN try again", Toast.LENGTH_SHORT).show();
						unlock.setText("");
					}	
					
				}
			
			}
		});


		
		
		if (txt == null ||  txt == ""){
			
		}
		else{
			text.setText(txt);	
		}
	
		
		
		if (color == null || color == ""){
			
		}
		else{
			
			col = Integer.parseInt(color);
			battery.setTextColor(col);
			Date.setTextColor(col);
			TextView1.setTextColor(col);
			text.setTextColor(col);
	
	       }
		
		
	
		
		if (img != "") {

			bmImg = BitmapFactory.decodeFile(img);
			BitmapDrawable background = new BitmapDrawable(bmImg);
			activityRootView.setBackgroundDrawable(background);

		} else if (colorbg != "") {
			colbg = Integer.parseInt(colorbg);
			activityRootView.setBackgroundColor(colbg);

		}

		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf;
		

		sdf = new SimpleDateFormat("d/M/yyyy EE");
		String date = sdf.format(c.getTime());
		Date.setText(date);

		registerReceiver(batteryStatusReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));


		
		
		
		

		mainhandler = new Handler() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf;

				sdf = new SimpleDateFormat("hh:mm a");
				String time = sdf.format(c.getTime());
				TextView1.setText(time);

				sdf = new SimpleDateFormat("d/M/yyyy EE");
				String date = sdf.format(c.getTime());
				Date.setText(date);


			}
		};

		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {

						Thread.sleep(1000);
						mainhandler.sendEmptyMessage(0);

					} catch (InterruptedException e) {

						e.printStackTrace();
					}

				}

			}
		}).start();

	}

	public void loadlock() {

		Servicelock.spf = PreferenceManager.getDefaultSharedPreferences(this);

		txt = Servicelock.spf.getString("text", "");

		img = Servicelock.spf.getString("img", "");

		color = Servicelock.spf.getString("color", "");

		colorbg = Servicelock.spf.getString("colorbg", "");

	}
	
	@Override
	public void onDestroy() {
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();
      

		
	} 
	
	
	BroadcastReceiver batteryStatusReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int level = intent.getIntExtra("level", 0);
			int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
			boolean batteryCharge = status == BatteryManager.BATTERY_STATUS_CHARGING;

			int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,
					-1);
			boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
			boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

			if ((acCharge) || (usbCharge) || (batteryCharge)) {
				battery.setText(level + "% " + "Charging");
			} else {
				if (level < 20) {
					battery.setText(level + "% " + "Low Battery");

				} else {
					battery.setText(level + "% " + "Battery");
				}

			}

		}
	};

	
	public void save(String key, String value) {
		Editor edit = sec.edit();
		edit.putString(key, value);
		edit.commit();

	}
	
	

	
	
	
	
	
	
	
	
	


}
