package com.pilockerstable;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class test extends Activity {

	
	SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);
	Boolean KEY1;
 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 save("KEY1", true);
		load();
		
		
		//That's it, TA DA :D
		
	}
	
	
	//This method to save values
	public void save(String key, boolean value) {
		Editor edit = spf.edit();
		edit.putBoolean(key, value);
		edit.commit();

	}

	
	//EXAMPLE
	
	
	
	
//////////////////////////////////////////////////////////////////////////////
	
	
	public void load() {

		Servicelock.spf = PreferenceManager.getDefaultSharedPreferences(this);
		
		KEY1 = spf.getBoolean("KEY1", false); //We put false as a default value if you are using app for first time

	}
	
	
          //Load the method on the oncreate 
	
	
	
	
	
	
}
