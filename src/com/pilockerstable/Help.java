package com.pilockerstable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Help extends Activity {

	ImageView imghelp;
    Button next ;
	int x=1;   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		imghelp = (ImageView) findViewById(R.id.imghelp);	
		next =(Button)  findViewById(R.id.nextbutton);
		
	   next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				x++;
				
				
				if (x==2)
				{
					imghelp.setImageResource(R.drawable.two);
				}
				
				if (x==3)
				{
					imghelp.setImageResource(R.drawable.three);
				}
				
				if (x==4)
				{
					imghelp.setImageResource(R.drawable.four);
				}

				if (x==5)
				{
					imghelp.setImageResource(R.drawable.five);
				}
				
				if (x==6)
				{
					imghelp.setImageResource(R.drawable.six);
				}
				
				if (x==7)
				{
					imghelp.setImageResource(R.drawable.seven);
				}
				
				if (x==8)
				{
					imghelp.setImageResource(R.drawable.eight);
				}
				
				if (x==9)
				{
					imghelp.setImageResource(R.drawable.nine);
					next.setText("Finish");
					
				}
				
				if (x==10)
				{
					finish();
				}
					
				
			}
		});
		 
		
		 
	}

	
}
