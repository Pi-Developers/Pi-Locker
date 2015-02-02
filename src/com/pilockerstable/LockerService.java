package com.pilockerstable;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.androidfire.logger.LoggerHelper;

/**
 * @author AndroidFire and SpaceCaker
 * 
 */

@SuppressWarnings("deprecation")
public class LockerService extends Service {

	static SharedPreferences spf;
	int mStatus;
	KeyguardLock mKeyguardLock;
	static Context c;

	/**
	 * <p>
	 * Methods of onBind Not Important for LockerService
	 * </p>
	 */
	@Override
	public IBinder onBind(Intent arg0) {

		return null;
	}

	/**
	 * <p>
	 * mStaus is Variable of int we need to find status of locker to make it
	 * enable or disable {@link onCreate}{@link mStatus}
	 * </p>
	 */
	
	@Override
	public void onCreate() {

		LoggerHelper.dg(getBaseContext(), "Pi Locker Service has Started");

		mStatus = Settings.System.getInt(getBaseContext().getContentResolver(),
				"PiLocker", 0);

		if (mStatus == 0) {
			stopSelf();
			LoggerHelper.er(getBaseContext(), "Pi Locker is Disabled");
			return;
		}
		if (mStatus == 1) {
			try {
			mKeyguardLock = ((KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE)).newKeyguardLock("PiLocker");
			mKeyguardLock.disableKeyguard();
			IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
			intentFilter.setPriority(999);
            registerReceiver(mReceiver, intentFilter);
			} catch (Exception e) {

				unregisterReceiver(mReceiver);

			}
			
		} else if (mStatus == 0) {

			unregisterReceiver(mReceiver);
			mKeyguardLock.reenableKeyguard();
			
		}
		registerReceiver(mRestore, new IntentFilter("com.androidfire.Restore_Pi"));
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			TelephonyManager ts = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			int callState = ts.getCallState();
			if (callState == TelephonyManager.CALL_STATE_IDLE) {

				startActivity(new Intent(LockerService.this, Lock.class)
						.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

				LoggerHelper.dg(getBaseContext(), "Locker Service Has Started");
			}

		}

	};
	private BroadcastReceiver mRestore = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			try{
				
			unregisterReceiver(mReceiver);
			mKeyguardLock.reenableKeyguard();
			
			}catch(Exception e){
				
				
			}
		}

	};

	/**
	 * <p>
	 * Methods to Destroy service any time
	 * </p>
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

}
