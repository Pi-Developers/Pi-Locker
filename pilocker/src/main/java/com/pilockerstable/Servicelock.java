package com.pilockerstable;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class Servicelock extends Service {
	BroadcastReceiver mReceiver;

	static SharedPreferences spf;
	static Context c;
	@SuppressWarnings("deprecation")
	KeyguardManager.KeyguardLock k1;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

		c = getApplicationContext();
		spf = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = spf.edit();
		edit.putString("on", "true");
		edit.commit();

		//KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		//k1 = km.newKeyguardLock("IN");

		//k1.disableKeyguard();

		//IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		//filter.addAction(Intent.ACTION_SCREEN_OFF);
		//mReceiver = new Receiver();
		//registerReceiver(mReceiver, filter);
		super.onCreate();
	}



	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDestroy() {
		unregisterReceiver(mReceiver);
		k1.reenableKeyguard();

		super.onDestroy();
		
		
	}

}
