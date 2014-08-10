package com.pilockerstable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Lock extends Activity implements OnGesturePerformedListener {

	private Handler mainhandler;
	private HomeKeyLocker mHomeKeyLocker;
	private GestureLibrary gLibrary;
	public static boolean isLighOn = false;
	public static Camera mcam;
	int col, colbg;
	int iii = 0;
	String Pass,Pin;
	TextView battery, Date, Time, text;
	Context context;
	AudioManager audiomanage;
	Parameters p;
	GestureDetectorCompat gDetector;
	String load2, txt, img, color, colorbg, XYZ;
	PackageManager pm;
	Bitmap bmImg;
	SharedPreferences sse;
	WindowManager wm;
	WindowManager.LayoutParams params;
	GestureOverlayView gOverlay;
	TableLayout	r0;
	
	@SuppressWarnings("deprecation")
	@SuppressLint({ "SimpleDateFormat", "HandlerLeak" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		sse = PreferenceManager.getDefaultSharedPreferences(this);
		XYZ = sse.getString("XYZ", "");
		save("Locker" , "1");
		
	    android.provider.Settings.System.putInt(getContentResolver(),
	            Settings.System.SCREEN_OFF_TIMEOUT, 30000);

		
		
		if (XYZ.equals("true")) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		} else {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			}

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		
		setContentView(R.layout.activity_lock);

		
	
		
		 Pass = sse.getString("pass", "");
		 Pin = sse.getString("pin", "");
		context = this;
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(this);
		pm = context.getPackageManager();
	    gOverlay = (GestureOverlayView) findViewById(R.id.gOvers);
		gOverlay.addOnGesturePerformedListener(this);
		battery = (TextView) findViewById(R.id.battery);
		Date = (TextView) findViewById(R.id.date);
		Time = (TextView) findViewById(R.id.time);
		text = (TextView) findViewById(R.id.texts);
		r0 = (TableLayout) findViewById(R.id.r0);
		
		loadlock();

		gLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!gLibrary.load()) {
			finish();
		}

		gOverlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				String Double = sse.getString("Double", "");
				
				if(Double.equals("True")){
                	
				iii++;
				 Handler handler = new Handler();
				 
				 Runnable r = new Runnable() {
					@Override
					public void run() {
					iii = 0;
					}
				};
			
				if (iii == 1 ) {
					
					
					handler.postDelayed(r, 200);
				} else if (iii == 2) {
					
					
				   
					
				}else{
					

			}

				}	
			}
		});
		

		Time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
                String E = sse.getString("emergency", "");
                
                if (E.equals("true")){
                	finish();
                	
                }else{
                	
                }
		
				
			}
		});	
		
		if (txt == null ||  txt == ""){
			
		}
		else{
			text.setText(txt);	
		}
	

		if (color != "") {
			col = Integer.parseInt(color);
			battery.setTextColor(col);
			Date.setTextColor(col);
			Time.setTextColor(col);
			text.setTextColor(col);
		}

		if (img != "" || img != null ) {

			bmImg = BitmapFactory.decodeFile(img);
			BitmapDrawable background = new BitmapDrawable(bmImg);
			r0.setBackgroundDrawable(background);

		} else if (colorbg != "") {
			colbg = Integer.parseInt(colorbg);
			r0.setBackgroundColor(colbg);

		}

		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf;

		sdf = new SimpleDateFormat("hh:mm:ss");
		String time = sdf.format(c.getTime());
		Time.setText(time);

		sdf = new SimpleDateFormat("d/M/yyyy EE");
		String date = sdf.format(c.getTime());
		Date.setText(date);

		registerReceiver(batteryStatusReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));

		mainhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf;

				sdf = new SimpleDateFormat("hh:mm a");
				String time = sdf.format(c.getTime());
				Time.setText(time);

				sdf = new SimpleDateFormat("d/M/yyyy EE");
				String date = sdf.format(c.getTime());
				Date.setText(date);

				registerReceiver(batteryStatusReceiver, new IntentFilter(
						Intent.ACTION_BATTERY_CHANGED));
				unregisterReceiver(batteryStatusReceiver);

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

	
	
	
	
	public void loadlock() {

		Servicelock.spf = PreferenceManager.getDefaultSharedPreferences(this);

		txt = Servicelock.spf.getString("text", "");

		img = Servicelock.spf.getString("img", "");

		color = Servicelock.spf.getString("color", "");

		colorbg = Servicelock.spf.getString("colorbg", "");

	}

	
	
	
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = gLibrary.recognize(gesture);

		if (predictions.size() > 0) {
			if (predictions.get(0).score > 2.0) {

				String action = predictions.get(0).name;

				if ("unlock".equals(action)) {
					mHomeKeyLocker.unlock();

					
			
					
					if (Pass.equals("") && Pin.equals("")) {
						
						finish();
					}else{
						
						startActivity(new Intent (this , PinActivity.class ));
						finish();
					}

				} else if ("camera".equals(action)) {
					mHomeKeyLocker.unlock();
					
					if (Pass.equals("") && Pin.equals("")) {
						
						Intent intent = new Intent(
								"android.media.action.IMAGE_CAPTURE");
						startActivityForResult(intent, 0);		
						
					}else{
						
						startActivity(new Intent (this , PinActivity.class ));
						save("camera", "true");
					}

				
					finish();
				} else if ("torch".equals(action)) {

					if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
						Log.e("err", "Device has no camera!");
						return;
					}

					if (isLighOn) {
						Toast.makeText(this, "Torch off", Toast.LENGTH_SHORT)
								.show();

						p.setFlashMode(Parameters.FLASH_MODE_OFF);
						mcam.setParameters(p);
						mcam.stopPreview();
						isLighOn = false;

					} else {

						Toast.makeText(this, "Torch on", Toast.LENGTH_SHORT)
								.show();
						mcam = Camera.open();
						p = mcam.getParameters();
						p.setFlashMode(Parameters.FLASH_MODE_TORCH);
						mcam.setParameters(p);
						mcam.startPreview();
						isLighOn = true;

					}
				} else if ("general".equals(action)) {
					AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
					audiomanage.getRingerMode();

					audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

					Toast.makeText(this, "General mode", Toast.LENGTH_SHORT)
							.show();

				} else if ("silent".equals(action)) {
					AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
					audiomanage.getRingerMode();
					audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);

					Toast.makeText(this, "silent mode", Toast.LENGTH_SHORT)
							.show();

				} else if ("vibration".equals(action)) {
					AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
					audiomanage.getRingerMode();
					audiomanage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

					Toast.makeText(this, "vibration mode", Toast.LENGTH_SHORT)
							.show();

				} else if ("wifi".equals(action)) {

					WifiManager wifi = (WifiManager) getSystemService(Lock.WIFI_SERVICE);

					if (wifi.isWifiEnabled() == false) {

						wifi.setWifiEnabled(true);
						Toast.makeText(this, "Wifi on", Toast.LENGTH_SHORT)
								.show();

					} else if (wifi.isWifiEnabled() == true) {
						wifi.setWifiEnabled(false);
						Toast.makeText(this, "Wifi off", Toast.LENGTH_SHORT)
								.show();

					}

				} else if ("bt".equals(action)) {

					BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
							.getDefaultAdapter();
					if (mBluetoothAdapter.isEnabled() == false) {
						mBluetoothAdapter.enable();
						Toast.makeText(this, "Bluetooth on", Toast.LENGTH_SHORT)
								.show();

					} else if (mBluetoothAdapter.isEnabled() == true) {

						mBluetoothAdapter.disable();
					}
					Toast.makeText(this, "Bluetooth off", Toast.LENGTH_SHORT)
							.show();

				} else if ("browser".equals(action)) {
					mHomeKeyLocker.unlock();


					if (Pass.equals("") && Pin.equals("")) {
						Uri uri = Uri.parse("http://www.google.com");
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						startActivity(intent);
						finish();	
					}else{
						
						startActivity(new Intent (this , PinActivity.class ));
						save("browser", "true");
						finish();
					}
			
				}
			}
		}

	}
	

	
	
	
	public void save(String key, String value) {
		Editor edit = sse.edit();
		edit.putString(key, value);
		edit.commit();

	}
	

	@Override
	public void onBackPressed() {
		return;
	}

	
	
	@Override
	protected void onPause() {
		super.onPause();

	}

	
	
	
	@Override
	public void onStop() {
		super.onStop();
	}

	
	
	public void onDestroy() {
		super.onDestroy();
        save("Locker" , "0");
		if (img != "" || bmImg != null) {
			
			try{
			bmImg.recycle();
			bmImg = null;
			}catch(Exception e){
				
			}
		}

		if (mHomeKeyLocker != null) {
			mHomeKeyLocker.unlock();
			mHomeKeyLocker = null;
		}
		System.gc();
		
		
	}

	
	
	

}
