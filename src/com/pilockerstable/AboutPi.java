package com.pilockerstable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class AboutPi extends Activity {
	LinearLayout AboutPiLock,AboutPiDevv,DonatePi;
	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.about_pi);
		AboutPiLock = (LinearLayout)findViewById(R.id.about_pi_lock);
		AboutPiDevv = (LinearLayout)findViewById(R.id.about_pi_devv);
		DonatePi = (LinearLayout)findViewById(R.id.donate_to_pi);
		AboutPiLock.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 new AlertDialog.Builder(AboutPi.this)
         	    .setTitle("About Pi locker")
         	    .setMessage(R.string.about)
         	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
         	        public void onClick(DialogInterface dialog, int which) { 
                   }
         	     })
         	    .setIcon(android.R.drawable.ic_dialog_alert)
         	     .show();
			}
		});
		AboutPiDevv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(AboutPi.this)
				 .setTitle("About Pi Developers ")
           	    .setMessage("Who are pi developers?\n\nWe are a Team from Egypt and India we generally work on android platform.We try to make new innovative ideas for users or refining existing ideas.work for free, we are still growing and expanding. you can visit our official website pi-developers.tk to stay tuned with us or follow us on social media (facebook & twitter)  ")
           	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
           	        public void onClick(DialogInterface dialog, int which) { 
                  }
           	     })
           	    .setIcon(android.R.drawable.ic_dialog_alert)
           	     .show();
			}
				
			
		});
		 DonatePi.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					 new AlertDialog.Builder(AboutPi.this)
	            	    .setTitle("Donate")
	            	    .setMessage("We don't get paid for our work, and we don't mind, but we would appericiate it if you liked our works and bought us a cup of coffee :)")
	            	    .setPositiveButton("Donate", new DialogInterface.OnClickListener() {
	            	        public void onClick(DialogInterface dialog, int which) { 
	            	        	
	            	        	String url = "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=VA2FZQ2PWPFQ8";
	        					Intent i = new Intent(Intent.ACTION_VIEW);
	        					i.setData(Uri.parse(url));
	        					startActivity(i);
                    }
	            	     })
	            	    .setIcon(R.drawable.ic_launcher)
	            	     .show();
				
				}
			});
	}
}