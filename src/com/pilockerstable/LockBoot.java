package com.pilockerstable;



/**
 * @author AndroidFire
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.WindowManager;


public class LockBoot extends BroadcastReceiver {
	int mStatus;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub

		mStatus = Settings.System.getInt(arg0.getContentResolver(),
				"PiLocker", 0);
		/**
		 * <p> mStatus is int but we have make mStatus into boolean using 0)==1; </p>
		 */
		
		if (mStatus == 1 ) {
			Intent i = new Intent(arg0, Lock.class);
	       	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	       	i.addFlags(WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW);
	       	arg0.startActivity(i);
			arg0.startService(new Intent(arg0,LockerService.class));
	       	

	       	/**
	       	 * If mStatus is true or Pi Locker is on It will display Lock after boot and startService
	       	 */
		}
		else if (mStatus == 0 ) {
			Intent i1 = new Intent(arg0, LockerService.class);
	       	arg0.stopService(i1);
	       	/**
	       	 * <p>It will Stop the Service if disable the pi-locker</p>
	       	 */
		}
		
	}

}
