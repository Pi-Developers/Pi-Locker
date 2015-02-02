package com.pilockerstable;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DeviceAdmin extends DeviceAdminReceiver {
	 
    @Override
    public void onDisabled(Context context, Intent intent) {
        Toast.makeText(context, "Pi Device Admin Disabled",  Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, "Pi Device Admin is now enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Log", "MyDevicePolicyReciever Received: " + intent.getAction());
        super.onReceive(context, intent);
    }
}