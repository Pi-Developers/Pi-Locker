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
import android.annotation.TargetApi;
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
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GestureDetectorCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "SimpleDateFormat", "HandlerLeak" })
@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.KITKAT)
public class Lock extends Activity implements OnGesturePerformedListener {

	private Handler mainhandler;
	private HomeKeyLocker mHomeKeyLocker;
	private GestureLibrary gLibrary;

	public static boolean isLighOn = false;
	public static Camera mcam;

	boolean admin;
	boolean mobileDataEnabled = false;
	boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
	boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);

	int col, colbg;
	int iii = 0;
	int i = 0;

	static TextView battery, Date, Time, text, carrier, data, msgs, calls,
			bluetooth, wifi, sound, whats,pmm;
	static GestureOverlayView gOverlay;
	static TableLayout r0;
	static Bitmap bmImg;

	String  Pin, load2, txt, img, color, colorbg, XYZ, carrierName, E,
			proximity, tap, skips;

	Context context;
	AudioManager audiomanage;
	Parameters p;
	GestureDetectorCompat gDetector;
	PackageManager pm;
	SharedPreferences sse;
	WindowManager.LayoutParams params;
	TelephonyManager manager;
	Calendar c = Calendar.getInstance();
	SimpleDateFormat sdf;
	Intent closeDialog;
	Runnable runnable;
	DevicePolicyManager policyManager;
	ComponentName adminReceiver;
	Window window;
	WindowManager wmanager;
	customViewGroup view;
	BluetoothAdapter mBluetoothAdapter;
	WifiManager twifi;
	ConnectivityManager cm;
	AudioManager am;
	TableLayout tab, r1;
	ArrayList<String> al;
	TextView textview;
	Spanned update;
	View v1,v2;

	
	@SuppressLint({ "SimpleDateFormat", "HandlerLeak", "DefaultLocale" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		al = new ArrayList<String>();

		// loads shared preferences
		loadlock();

		
		// loads the configuration of the immersive mode and statusbar blocker
		fullscreen();

		// Set the content view
		setContentView(R.layout.activity_lock);

		// Initialize all content
		CreateTheLayout();

		// Control behavior
		ifConditionsControlBehavior();

		// Stop the locker service
		stopService(new Intent(Lock.this, LockerService.class));

		// Battery percentage
		registerReceiver(batteryStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		tab = (TableLayout) findViewById(R.id.r1);

		TableRow tr = new TableRow(getApplicationContext());

		tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
		textview = new TextView(getApplicationContext());
		textview.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
		textview.setTextSize(15);
		textview.setTextColor(Integer.parseInt(color));
		tr.addView(textview);
		tab.addView(tr);

		LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));

		// Set The Time and Date
		sdf = new SimpleDateFormat("h:mm");
		SimpleDateFormat sdf1 = new SimpleDateFormat("a");

		String timeid = sdf1.format(c.getTime());


		String time = sdf.format(c.getTime());

		Time.setText(time);
		pmm.setText(timeid);
		
		sdf = new SimpleDateFormat(" EE, d  MMM yyyy");
		String date = sdf.format(c.getTime());
		Date.setText(date);

		// All Processes That Repeats every period of time

		mainhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				AudioIcon();
				ConnectivityIcon();
				callsCount();
				messagesCount();
				dateAndTime();

				if (!Pin.equals("")) {

					Intent closeDialog = new Intent(
							Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
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

		
		//Removes All Notifications
		gOverlay.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {

				tab.removeAllViews();

				
				return false;
			}
		});
		
		

		// This listener controls the double click to sleep
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

							// user should activate the device admin
							policyManager.lockNow();

						} else {

						}

						iii = 0;

					}

				}

			}

		});

		// This one controls the option of the emergency unlock
		// only works if no pin is set
		Time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (E.equals("true") && Pin.equals("")) {

					onCan();

				} else {

				}

			}
		});

	}

	
	/**
	 * 
	 * All these are the methods controlling this class
	 * 
	 **/

	private BroadcastReceiver onNotice = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			try {

				String title = intent.getStringExtra("title");
				String text = intent.getStringExtra("text");
				String pack = intent.getStringExtra("p");
				boolean x = intent.getBooleanExtra("c", false) ;
				boolean y = intent.getBooleanExtra("o" , false);

				
				if (pack.contains("whatsapp")) {

					whats.setText("  Unread Whatsapp Messages");

				} else if(x == true || y == true) {
					
					//Do nothing
					
				}else{

					update = Html.fromHtml("<br><b>" + title + "</b><br>" + text);

					textview.append(update);

					if (color != "") {

						col = Integer.parseInt(color);

						textview.setTextColor(col);

					}

					
				}

			}

			catch (Exception e) {

			}

		}
	};

	// Get the battery percentage
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

				battery.setText(level + "% ");
				battery.setTextColor(Color.GREEN);

			} else if (level < 20) {

				battery.setTextColor(Color.RED);
				battery.setText(level + "% ");
			}

			else {

				battery.setText(level + "% ");
				battery.setTextColor(Integer.parseInt(color));

			}

		}

	};

	// Set The Time and Date
	@SuppressLint("SimpleDateFormat")
	public void dateAndTime() {

		c = Calendar.getInstance();

		sdf = new SimpleDateFormat(" h:mm");
		SimpleDateFormat sdf1 = new SimpleDateFormat(" a");

		String timeid = sdf1.format(c.getTime());


		String time = sdf.format(c.getTime());

		Time.setText(time);
		pmm.setText(timeid);
		
		sdf = new SimpleDateFormat(" EE, d  MMM yyyy");
		String date = sdf.format(c.getTime());
		Date.setText(date);

	}

	// Get unread calls and set them
	public void callsCount() {

		int i = 0;

		final String[] projection = null;
		final String selection = null;
		final String[] selectionArgs = null;
		final String sortOrder = android.provider.CallLog.Calls.DATE + " DESC";
		
		Cursor cursor = null;

		try {

			cursor = context.getContentResolver().query(Uri.parse("content://call_log/calls"), projection, selection, selectionArgs, sortOrder);

			while (cursor.moveToNext()) {

				String callType = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE));

				String isCallNew = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NEW));

				if (Integer.parseInt(callType) == android.provider.CallLog.Calls.MISSED_TYPE && Integer.parseInt(isCallNew) > 0) {

					i++;

				}
			}
			
		} catch (Exception ex) {

		} finally {

			calls.setText("  " + i + " Missed Calls");

			if (i == 0) {

				calls.setText("  No Missed Calls");

			}

			cursor.close();
		}

	}

	
	// This get the messages count and set it
	public void messagesCount() {

		String[] id = { "count(_id)", };
		
		try{
			
		Cursor ccccc = getContentResolver().query(Uri.parse("content://sms/inbox"), id , "read = 0", null, null);
		ccccc.moveToFirst();
		
		int unreadMessagesCount = ccccc.getInt(0);

		
		if (unreadMessagesCount == 0) {

			msgs.setText("  No Unread Messages");

		} else {

			msgs.setText("  " + unreadMessagesCount + " Unread Messages");
			ccccc.close();
			
		   }

		}catch(NullPointerException e){
			
			e.printStackTrace();
				
		}
	}

	// Controls the audio icon state
	public void AudioIcon() {

		switch (am.getRingerMode()) {

		case AudioManager.RINGER_MODE_SILENT:
			sound.setText("Silent");
			break;

		case AudioManager.RINGER_MODE_VIBRATE:
			sound.setText("Vibrate");
			break;

		case AudioManager.RINGER_MODE_NORMAL:
			sound.setText("");
			break;

		}

	}

	// Controls connectivity icons
	public void ConnectivityIcon() {

		// This controls bluetooth
		if (mBluetoothAdapter == null) {

		} else {

			if (!mBluetoothAdapter.isEnabled()) {

				bluetooth.setTextColor(Color.GRAY);

			} else {
				bluetooth.setTextColor(Integer.parseInt(color));

			}
		}

		// ThisControls wifi
		if (twifi.isWifiEnabled()) {

			if (color != null || color != "") {

				wifi.setTextColor(Integer.parseInt(color));

			} else {

				wifi.setTextColor(Color.WHITE);

			}

		} else {

			wifi.setTextColor(Color.GRAY);

		}

		// This controls data connection
		try {

			Class<?> cmClass = Class.forName(cm.getClass().getName());

			Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
			method.setAccessible(true); // Make the method callable
			mobileDataEnabled = (Boolean) method.invoke(cm); // get the setting for "mobile data"

			if (mobileDataEnabled == true && !twifi.isWifiEnabled()) {

				data.setTextColor(Integer.parseInt(color));

			} else {

				data.setTextColor(Color.GRAY);

			}

		} catch (Exception e) {

		}

	}

	// Alot of if conditions on the onCreate
	public void ifConditionsControlBehavior() {

		// Sets carrier name
		if (carrierName.equals(null) || carrierName.isEmpty()
				|| carrierName.equals("")) {

			carrier.setText("No Service");
		}

		// Set the home key blocker
		if (hasBackKey && hasHomeKey) {

			mHomeKeyLocker = new HomeKeyLocker();
			mHomeKeyLocker.lock(this);

		}

		// If user chose to skip the gesture screen
		if (skips.equals("1")) {

			if (Pin != null || Pin != "") {

				startActivity(new Intent(Lock.this, PinActivity.class));
				finish();

			}

		}

		// check gesture lib
		if (!gLibrary.load()) {

			onCan();
		}

		// sets the custom text
		if (txt == null || txt == "") {

		} else {

			text.setText(txt);
		}

		// Sets the theme color
		if (color != "") {

			col = Integer.parseInt(color);

			battery.setTextColor(col);
			Date.setTextColor(col);
			Time.setTextColor(col);
			text.setTextColor(col);
			gOverlay.setGestureColor(col);
			carrier.setTextColor(col);
			data.setTextColor(col);
			wifi.setTextColor(col);
			msgs.setTextColor(col);
			calls.setTextColor(col);
			bluetooth.setTextColor(col);
			sound.setTextColor(col);
			whats.setTextColor(col);
			v1.setBackgroundColor(col);
			v2.setBackgroundColor(col);
		}

		// Set background image if found else; sets custom color or none
		if (img != "" || img != null) {

			bmImg = BitmapFactory.decodeFile(img);

			BitmapDrawable background = new BitmapDrawable(bmImg);
			r0.setBackgroundDrawable(background);

		} else if (colorbg != "") {

			colbg = Integer.parseInt(colorbg);
			r0.setBackgroundColor(colbg);

		}

	}

	// Inialize all objects
	@SuppressWarnings("static-access")
	public void CreateTheLayout() {

		context = this;

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		twifi = (WifiManager) getSystemService(Lock.WIFI_SERVICE);
		cm = (ConnectivityManager) context
				.getSystemService(context.CONNECTIVITY_SERVICE);
		am = (AudioManager) getSystemService(context.AUDIO_SERVICE);

		manager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
		carrierName = manager.getNetworkOperatorName();
		carrier = (TextView) findViewById(R.id.textView2);
		carrier.setText(carrierName.toUpperCase());

		pm = context.getPackageManager();

		gLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		gOverlay = (GestureOverlayView) findViewById(R.id.gestureOverlayView1);
		gOverlay.addOnGesturePerformedListener(this);

		battery = (TextView) findViewById(R.id.battery);
		Date = (TextView) findViewById(R.id.date);
		Time = (TextView) findViewById(R.id.time);
		text = (TextView) findViewById(R.id.texts);
		data = (TextView) findViewById(R.id.textView4);
		msgs = (TextView) findViewById(R.id.textView7);
		calls = (TextView) findViewById(R.id.textView5);
		bluetooth = (TextView) findViewById(R.id.textView3);
		wifi = (TextView) findViewById(R.id.textView1);
		sound = (TextView) findViewById(R.id.textView6);
		whats = (TextView) findViewById(R.id.textView8);
		pmm = (TextView) findViewById(R.id.pm);


		policyManager = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		adminReceiver = new ComponentName(context, DeviceAdmin.class);
		admin = policyManager.isAdminActive(adminReceiver);

		r0 = (TableLayout) findViewById(R.id.r0);
		r0.setDrawingCacheEnabled(true);
		r0.buildDrawingCache();

		v1 = (View)findViewById(R.id.v1);
		v2 = (View)findViewById(R.id.v2);
	}

	// Loads prefrences
	public void loadlock() {

		sse = PreferenceManager.getDefaultSharedPreferences(this);

		save("Locker", "1");

		txt = sse.getString("text", "");
		img = sse.getString("img", "");
		color = sse.getString("color", String.valueOf(Color.WHITE));
		colorbg = sse.getString("colorbg", "");
		E = sse.getString("emergency", "");
		XYZ = sse.getString("XYZ", "");
		tap = sse.getString("tap", "disable");
		img = sse.getString("img", "");
		Pin = sse.getString("pin", "");
		skips = sse.getString("skip", "0");

	}

	// Controls gestures action
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

		ArrayList<Prediction> predictions = gLibrary.recognize(gesture);

		if (predictions.size() > 0) {

			if (predictions.get(0).score > 3.0) {

				String action = predictions.get(0).name;

				if ("unlock".equals(action)) {
					
					if (hasBackKey && hasHomeKey) {

						mHomeKeyLocker.unlock();
					}

					if (Pin.equals("")) {

						onCan();

					} else {

						startActivity(new Intent(this, PinActivity.class));

						onCan();
						finish();

					}

				} else if ("camera".equals(action)) {

					if (hasBackKey && hasHomeKey) {

						mHomeKeyLocker.unlock();

					}

					if (Pin.equals("")) {

						Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
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

					am.getRingerMode();
					am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					Toast.makeText(this, "General mode", Toast.LENGTH_SHORT).show();

				} else if ("silent".equals(action)) {

					am.getRingerMode();
					am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					Toast.makeText(this, "silent mode", Toast.LENGTH_SHORT).show();

				} else if ("vibration".equals(action)) {

					am.getRingerMode();
					am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
					Toast.makeText(this, "vibration mode", Toast.LENGTH_SHORT).show();

				} else if ("wifi".equals(action)) {

					if (twifi.isWifiEnabled() == false) {

						twifi.setWifiEnabled(true);
						Toast.makeText(this, "Wifi on", Toast.LENGTH_SHORT).show();

					} else if (twifi.isWifiEnabled() == true) {

						twifi.setWifiEnabled(false);
						Toast.makeText(this, "Wifi off", Toast.LENGTH_SHORT).show();

					}

				} else if ("bt".equals(action)) {

					BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
					
					if (mBluetoothAdapter.isEnabled() == false) {
						
						mBluetoothAdapter.enable();
						Toast.makeText(this, "Bluetooth on", Toast.LENGTH_SHORT).show();

					} else if (mBluetoothAdapter.isEnabled() == true) {

						mBluetoothAdapter.disable();
					}

					Toast.makeText(this, "Bluetooth off", Toast.LENGTH_SHORT).show();

				} else if ("browser".equals(action)) {

					if (hasBackKey && hasHomeKey) {

						mHomeKeyLocker.unlock();

					}

					if ( Pin.equals("")) {

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

	// The method the controls the shared prefrences and saves the settings
	public void save(String key, String value) {

		Editor edit = sse.edit();
		edit.putString(key, value);
		edit.commit();

	}

	// Configuration of the immersive mode
	public void fullscreen() {

		//Apllies Immersive mode
		window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
				| View.INVISIBLE );

		
		
		
		wmanager = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));

		// statusbar blocker configuration
		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
		localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		localLayoutParams.gravity = Gravity.TOP;
		localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
				// this is to enable the notification to recieve touch events
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
				// Draws over status bar
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

		// set the size of statusbar blocker
		localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		localLayoutParams.height = (int) (35 * getResources().getDisplayMetrics().scaledDensity);
		localLayoutParams.format = PixelFormat.TRANSLUCENT;

		view = new customViewGroup(this);
		view.setBackgroundColor(0x33000000);
		wmanager.addView(view, localLayoutParams);

	}

	
	// Block Back button (even soft one)
	@Override
	public void onBackPressed() { 
		
		super.onBackPressed();
		return;
	}

	
	// Blocks the hardware buttons
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		return keyCode == KeyEvent.KEYCODE_HOME
				|| keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_APP_SWITCH
				|| keyCode == KeyEvent.KEYCODE_SOFT_LEFT
				|| keyCode == KeyEvent.KEYCODE_SOFT_RIGHT;
		
	}
	
	

	// Clears the preferences and all customizations
	public void onDestroy() {

		super.onDestroy();

		//Save the Lock State
		save("Locker", "0");

		//Remove the bitsmp cache of background
		if (img != "" || bmImg != null) {

			try {

				bmImg.recycle();
				bmImg = null;

			} catch (Exception e) {
				
				e.printStackTrace();
				
		     }

		}

		//Remove The Home Key Lock
		if (mHomeKeyLocker != null && hasBackKey && hasHomeKey) {

			mHomeKeyLocker.unlock();
			mHomeKeyLocker = null;
		}

		//Clear Ram
		System.gc();

		//Unregister Receiver 
		unregisterReceiver(batteryStatusReceiver);
		
		//Remove Custom StatusBar
		wmanager.removeView(view);

		
		if(Pin.equals("")){
			
		    try {

			    android.os.Process.killProcess(android.os.Process.myPid());

		    } catch (Exception e) {
		    	
			    System.exit(0);


		      }
		
		   }
	}

	// Clears the preferences and all customizations
	//To Avoid Crashes 
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
	
	/**
	 * 
	 * To Avoid Screen Rotation and Force Closes
	 * 
	 * */
	@Override
	public void onConfigurationChanged(Configuration myConfig){
	    super.onConfigurationChanged(myConfig);
	    
	    int orient = getResources().getConfiguration().orientation;
	    
	    switch(orient){
	    
	       case Configuration.ORIENTATION_LANDSCAPE:
	          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	                    break;
	                    
	       case Configuration.ORIENTATION_PORTRAIT:
	          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	                    break;
	                    
	       default:
	          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	          
	    }
	}

}