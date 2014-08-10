package com.pilockerstable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;

public class Receiver  extends BroadcastReceiver  {
SharedPreferences spf;
String	locker,on;
	 public static boolean wasScreenOn = true;
	@Override
	public void onReceive(Context context, Intent intent) {

		
		spf = PreferenceManager.getDefaultSharedPreferences(context);
	  	locker = spf.getString("Locker", "");
	  	on = spf.getString("on", "");
	  	
		
		
		 if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

		
            wasScreenOn=false;
     		Lock.isLighOn = false;
     	
    	
		  	
			
		  	
		  	
		 if(on.equals("true")){

             Intent intent11 = new Intent(context,Lock.class);
             intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             context.startActivity(intent11);
             
           

             
		 }
		 else{
	 
		 }
		 
		 if(locker.equals("1")){
			 
			  Intent intent11 = new Intent(context,Lock.class);
	             intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	             context.startActivity(intent11);
		 }
		 }
		

      else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) )
    	  
       {
    	  
    	
  	if (on.equals("true")){
  		
  		context.startService(new Intent(context,Servicelock.class));
       	Intent i = new Intent(context, Lock.class);
       	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        
  	}
  	else{
  		
  	}

       }
    }
}