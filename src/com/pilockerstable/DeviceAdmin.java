package com.pilockerstable;

import android.app.admin.DeviceAdminReceiver;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


/**
 * 
 * @author rashad
 * 
 **/

/*
 * Class to recieve when the device admin of pi locker is enabled/disabled
 * 
 */

public class DeviceAdmin extends DeviceAdminReceiver {
	 
	
    @Override
    public void onDisabled(Context context, Intent intent) {
    	
        Toast.makeText(context, "Pi Locker Device Admin Disabled",  Toast.LENGTH_SHORT).show();
    }

    
    @Override
    public void onEnabled(Context context, Intent intent) {
    	
        Toast.makeText(context, "Pi Locker Device Admin Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        
    }
}