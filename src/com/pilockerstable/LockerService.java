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
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * @author AndroidFire and SpaceCaker
 * 
 */

@SuppressWarnings("deprecation")
public class LockerService extends Service {

	
	static SharedPreferences spf;
	static Context c;
	int mStatus;
	String on;
	KeyguardLock mKeyguardLock;

	
	@Override
	public IBinder onBind(Intent arg0) {

		return null;
		
	}

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {

	        return START_STICKY;
	    }

	
	/**
	 * 
	 * mStaus is Variable of int we need to find status of locker to make it
	 * enable or disable 
	 * 
	 **/
	
	@Override
	public void onCreate() {


		mStatus = Settings.System.getInt(getBaseContext().getContentResolver(),"PiLocker", 0);

		if (mStatus == 0) {
			
			stopSelf();
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

			try {

			unregisterReceiver(mReceiver);
			mKeyguardLock.reenableKeyguard();
			
			
			} catch(Exception e){
				
				e.printStackTrace();
				
			}
			
		}
		
		try{
			
			registerReceiver(mRestore, new IntentFilter("com.androidfire.Restore_Pi"));

		}catch(Exception e){
			
			e.printStackTrace();
			
		}
	}

	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			int callState = ts.getCallState();
			
			if (callState == TelephonyManager.CALL_STATE_IDLE) {

				startActivity(new Intent(LockerService.this, Lock.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

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
				
				e.printStackTrace();
			}
		}

	};

	
	/**
	 * 
	 * Methods to Destroy service any time
	 * 
	 **/
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
		
		
		/**
		 * 
		 * make sure service still running 
		 *
		 */
		spf = PreferenceManager.getDefaultSharedPreferences(this);
		on = spf.getString("on", "true");
		
		if(on.equals("true")){
			
			startService(new Intent(LockerService.this , LockerService.class));
			
		}
	

	}

}
