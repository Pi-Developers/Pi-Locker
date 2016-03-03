package com.pilockerstable;

/**
 * 
 * @author AndroidFire
 * 
 **/


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.WindowManager;


public class LockBoot extends BroadcastReceiver {
	
	int mStatus;
	
	
	@Override
	public void onReceive(Context context, Intent arg1) {

		
		mStatus = Settings.System.getInt(context.getContentResolver(), "PiLocker", 0);
		
		/**
		 * 
		 * mStatus is int but we have make mStatus into boolean using 0==1;
		 * 
		 **/
		
		if (mStatus == 1 ) {
			
			Intent i = new Intent(context, Lock.class);
			
	       	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	       	i.addFlags(WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW);
	       	
	       	context.startActivity(i);
	       	context.startService(new Intent(context ,LockerService.class));
	       	
	       	/**
	       	 * 
	       	 * If mStatus is true or Pi Locker is on It will display Lock after boot and startService
	       	 * 
	       	 **/
	       	
		} else if (mStatus == 0 ) {
			
			Intent i1 = new Intent(context, LockerService.class);
			context.stopService(i1);
	       	
	       	/**
	       	 * 
	       	 * It will Stop the Service if disable the pi-locker
	       	 * 
	       	 **/
		}
		
	}

}
