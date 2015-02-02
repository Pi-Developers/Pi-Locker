package com.pilockerstable;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
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
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
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
	boolean admin;

	int col, colbg;
	int iii = 0;

	String Pass, Pin;
	static TextView battery, Date, Time, text, carrier;
	Context context;
	AudioManager audiomanage;
	Parameters p;
	GestureDetectorCompat gDetector;
	String load2, txt, img, color, colorbg, XYZ, carrierName, E, proximity,
			tap, skips;
	PackageManager pm;
	static Bitmap bmImg;
	SharedPreferences sse;
	WindowManager.LayoutParams params;
	static GestureOverlayView gOverlay;
	static TableLayout r0;
	TelephonyManager manager;
	Calendar c = Calendar.getInstance();
	SimpleDateFormat sdf;
	Intent closeDialog;
	Runnable runnable;
	DevicePolicyManager policyManager;
	ComponentName adminReceiver;

	@SuppressWarnings("deprecation")
	@SuppressLint({ "SimpleDateFormat", "HandlerLeak" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		stopService(new Intent(Lock.this, LockerService.class));

		loadlock();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		setContentView(R.layout.activity_lock);

		// Works on some phones
		
		FontsOverride.setDefaultFont(this, "DEFAULT", "Roboto-Thin.ttf");
		FontsOverride.setDefaultFont(this, "MONOSPACE", "Roboto-Thin.ttf");
		FontsOverride.setDefaultFont(this, "SANS_SERIF", "Roboto-Thin.ttf");
		FontsOverride.setDefaultFont(this, "SERIF", "Roboto-Thin.ttf");
		


		Pass = sse.getString("pass", "");
		Pin = sse.getString("pin", "");
		skips = sse.getString("skip", "0");
		context = this;

		try {
			FullScreencall();

		} catch (Exception e) {

			Log.d("failed", "XXX");

		}

		manager = (TelephonyManager) getApplicationContext().getSystemService(
				Context.TELEPHONY_SERVICE);
		carrierName = manager.getNetworkOperatorName();
		carrier = (TextView) findViewById(R.id.textView2);
		carrier.setText(carrierName);

		if (carrierName.equals(null) || carrierName.isEmpty()
				|| carrierName.equals("")) {

			carrier.setText("Limited or no service");
		}

		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(this);
		gLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		pm = context.getPackageManager();
		gOverlay = (GestureOverlayView) findViewById(R.id.gestureOverlayView1);
		gOverlay.addOnGesturePerformedListener(this);
		battery = (TextView) findViewById(R.id.battery);
		Date = (TextView) findViewById(R.id.date);
		Time = (TextView) findViewById(R.id.time);
		text = (TextView) findViewById(R.id.texts);
		r0 = (TableLayout) findViewById(R.id.r0);

		policyManager = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		adminReceiver = new ComponentName(context, DeviceAdmin.class);
		admin = policyManager.isAdminActive(adminReceiver);
		r0 = (TableLayout) findViewById(R.id.r0);

		r0.setDrawingCacheEnabled(true);
		r0.buildDrawingCache();

		if (skips.equals("1")) {

			if (Pin != null || Pin != "")

				startActivity(new Intent(Lock.this, PinActivity.class));
			finish();

		}

		if (!gLibrary.load()) {
			onCan();
		}

		
		
		
		

		gOverlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (tap.equals("enable")) {

					iii++;
					Handler handler = new Handler();
					Runnable r = new Runnable() {

						@Override
						public void run() {

							iii = 0;
						}
					};
					if (iii == 1) {

						handler.postDelayed(r, 250);

					} else if (iii == 2) {

						if (admin) {

							policyManager.lockNow();

						} else {
						}

						iii = 0;

					}

				} else {
				}

			}

		});

		Time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (E.equals("true") && Pin.equals("")) {

					onCan();

				} else {

				}

			}
		});

		if (txt == null || txt == "") {

		} else {
			text.setText(txt);
		}

		if (color != "") {

			col = Integer.parseInt(color);
			battery.setTextColor(col);
			Date.setTextColor(col);
			Time.setTextColor(col);
			text.setTextColor(col);
			gOverlay.setGestureColor(col);
			carrier.setTextColor(col);

		}

		if (img != "" || img != null) {

			bmImg = BitmapFactory.decodeFile(img);
			BitmapDrawable background = new BitmapDrawable(bmImg);
			r0.setBackgroundDrawable(background);

		} else if (colorbg != "") {
			colbg = Integer.parseInt(colorbg);
			r0.setBackgroundColor(colbg);

		}

		registerReceiver(batteryStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		sdf = new SimpleDateFormat("hh:mm a");
		String time = sdf.format(c.getTime());
		Time.setText(time);

		sdf = new SimpleDateFormat(" EE, d  MMM yyyy");
		String date = sdf.format(c.getTime());
		Date.setText(date);

		
		
		mainhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				c = Calendar.getInstance();

				sdf = new SimpleDateFormat("hh:mm a");
				String time = sdf.format(c.getTime());
				Time.setText(time);

				sdf = new SimpleDateFormat(" EE, d  MMM yyyy");
				String date = sdf.format(c.getTime());
				Date.setText(date);

				if (!Pin.equals("")) {

					Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
					sendBroadcast(closeDialog);
				}

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

			int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
			boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
			boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

			if ((acCharge) || (usbCharge) || (batteryCharge)) {

				battery.setText(level + "% " + "Charging");
				battery.setTextColor(Color.GREEN);

			} else if (level < 20) {

				battery.setTextColor(Color.RED);
				battery.setText(level + "% " + "Discharging");
			}

			else {

				battery.setText(level + "% " + "Discharging");
				battery.setTextColor(Color.WHITE);

			}

		}

	};

	public void loadlock() {

		sse = PreferenceManager.getDefaultSharedPreferences(this);

		save("Locker", "1");

		txt = sse.getString("text", "");
		img = sse.getString("img", "");
		color = sse.getString("color", "");
		colorbg = sse.getString("colorbg", "");
		E = sse.getString("emergency", "");
		XYZ = sse.getString("XYZ", "");

		tap = sse.getString("tap", "disable");
		img = sse.getString("img", "");

	}

	public void FullScreencall() {

		if (Build.VERSION.SDK_INT >= 19) { // 19 or above api
			View decorView = getWindow().getDecorView();
			decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

		} else {
			// for lower api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN;

			decorView.setSystemUiVisibility(uiOptions);
		}
	}

	@SuppressWarnings("deprecation")
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

		ArrayList<Prediction> predictions = gLibrary.recognize(gesture);

		if (predictions.size() > 0) {

			if (predictions.get(0).score > 2.0) {

				String action = predictions.get(0).name;

				if ("unlock".equals(action)) {

					mHomeKeyLocker.unlock();

					if (Pass.equals("") && Pin.equals("")) {

						onCan();

					} else {

						startActivity(new Intent(this, PinActivity.class));

						onCan();
						finish();

					}

				} else if ("camera".equals(action)) {
					mHomeKeyLocker.unlock();

					if (Pass.equals("") && Pin.equals("")) {

						Intent intent = new Intent(
								"android.media.action.IMAGE_CAPTURE");
						startActivityForResult(intent, 0);
						finish();

					} else {

						startActivity(new Intent(this, PinActivity.class));
						save("camera", "true");
						finish();

					}

					onCan();
				} else if ("torch".equals(action)) {

					if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

						Log.e("err", "Device has no camera!");
						return;
					}

					if (isLighOn) {
						
						Toast.makeText(this, "Torch off", Toast.LENGTH_SHORT).show();

						try {
							p.setFlashMode(Parameters.FLASH_MODE_OFF);
							mcam.setParameters(p);
							mcam.stopPreview();
							isLighOn = false;
						} catch (Exception e) {

							
						}

					} else {

						Toast.makeText(this, "Torch on", Toast.LENGTH_SHORT).show();
						try {

							mcam = Camera.open();
							p = mcam.getParameters();
							p.setFlashMode(Parameters.FLASH_MODE_TORCH);
							mcam.setParameters(p);
							mcam.startPreview();
							isLighOn = true;

						} catch (Exception e) {

						}

					}
				} else if ("general".equals(action)) {
					
					AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
					audiomanage.getRingerMode();
					audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					Toast.makeText(this, "General mode", Toast.LENGTH_SHORT).show();

				} else if ("silent".equals(action)) {
					
					AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
					audiomanage.getRingerMode();
					audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					Toast.makeText(this, "silent mode", Toast.LENGTH_SHORT).show();

				} else if ("vibration".equals(action)) {
					
					AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
					audiomanage.getRingerMode();
					audiomanage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
					Toast.makeText(this, "vibration mode", Toast.LENGTH_SHORT).show();

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
						onCan();
						finish();

					} else {

						startActivity(new Intent(this, PinActivity.class));

						save("browser", "true");
						onCan();
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
	public void onPause() {
		super.onPause();
		return;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		return;
	}

	@Override
	public void onStop() {
		super.onStop();
		return;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return keyCode == KeyEvent.KEYCODE_HOME
				|| keyCode == KeyEvent.KEYCODE_BACK;
	}

	public void onDestroy() {
		super.onDestroy();

		save("Locker", "0");

		if (img != "" || bmImg != null) {

			try {
				bmImg.recycle();
				bmImg = null;
			} catch (Exception e) {

			}
		}

		if (mHomeKeyLocker != null) {
			mHomeKeyLocker.unlock();
			mHomeKeyLocker = null;
		}
		
		System.gc();

		unregisterReceiver(batteryStatusReceiver);

	}

	public void onCan() {
		
		
		startService(new Intent(Lock.this, LockerService.class));
		finish();

		DataOutputStream dout;
		File mFile = new File(getBaseContext().getFilesDir().getPath() + "Lock");
		try {
			mFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String Com = "Last Unlocked : " + Time.getText().toString();
		try {
			dout = new DataOutputStream(new FileOutputStream(mFile));
			dout.writeBytes(Com);

			dout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
