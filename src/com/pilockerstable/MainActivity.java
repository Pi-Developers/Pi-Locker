package com.pilockerstable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	static SharedPreferences spf;
	CheckBox start, secret;
	Button screentext, background, screentextcolor, aboutpi, about, help,
			donate, set, button1, button2;
	Context context = this;
	private static int RESULT_LOAD_IMAGE = 1;
	int colors;
	String picturePath, load;
	static String on, se;
	private Uri mImageCaptureUri;
	String xx, DD;
	public final int CROP_FROM_CAMERA = 0;
	FileOutputStream out;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		start = (CheckBox) findViewById(R.id.start);
		secret = (CheckBox) findViewById(R.id.checkBox1);
		screentext = (Button) findViewById(R.id.screentext);
		background = (Button) findViewById(R.id.background);
		screentextcolor = (Button) findViewById(R.id.screentextcolor);
		help = (Button) findViewById(R.id.help);
		aboutpi = (Button) findViewById(R.id.aboutpi);
		about = (Button) findViewById(R.id.about);
		donate = (Button) findViewById(R.id.donate);
		set = (Button) findViewById(R.id.set);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		loadon();		
        loadX();

       
        
        
        set.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("Enter New Password");
				final EditText input1 = new EditText(context);
				alert.setView(input1);
				alert.setPositiveButton("Confirm",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								
								String p = input1.getEditableText().toString();
								
							if (	p.trim().length() < 4 ){
								Toast.makeText(context, "Pin Must be atleast 4 Characters, try again", Toast.LENGTH_SHORT).show();								
								
							}else if (p.trim().length() >= 4 && p.trim().length() <= 12){
								
								
                                save("pass", p);
                                save("pin", "");
                                Toast.makeText(context, "Password Updated", Toast.LENGTH_SHORT).show();;
							}else if (p.trim().length() > 12 ){
								
                                Toast.makeText(context, "The password must be less than 12 characters", Toast.LENGTH_SHORT).show();;

								
							}
							
							
							
							}
						});
				
				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
								
							}
						});
				AlertDialog alertDialog = alert.create();
				alertDialog.show();

						
			}
		});
        
        
        
        
        
        
        
        
        
        
        button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("Enter New Pin");
				final EditText input1 = new EditText(context);
				alert.setView(input1);
				alert.setPositiveButton("Confirm",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String pin = input1.getEditableText().toString();
								
						if (		pin.trim().length() < 4){
							
                            Toast.makeText(context, "Pin Must be atleast 4 numbers, try again", Toast.LENGTH_SHORT).show();

							
						}else if (	pin.trim().length() >= 4 && 	pin.trim().length() <= 12){
								
								
                                save("pin", pin);
                                save("pass" , "");
                                Toast.makeText(context, "PIN updated", Toast.LENGTH_SHORT).show();
							}
							
							else if (	pin.trim().length() > 12 ){
								
	                            Toast.makeText(context, "Pin Must be less than 12 numbers, try again", Toast.LENGTH_SHORT).show();
							}
							}							
						});
			
				
				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();

								
							}
						});
				AlertDialog alertDialog = alert.create();
				alertDialog.show();

						
			}
		});   
        
        

            button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				new AlertDialog.Builder(MainActivity.this)
           	    .setTitle("Reset Pin/Password")
           	    .setMessage("Are you sure you want to reset security pin/password? \nthis will leave your phone UNSECURED")
           	    .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
           	        public void onClick(DialogInterface dialog, int which) { 
           	        	

           	         save("pin", ""); 
           	         save("pass", "");
           	         
           	         }
           	     })
           	     .setNegativeButton("NO", new DialogInterface.OnClickListener() {
           	        public void onClick(DialogInterface dialog, int which) { 
           	        	

    					
                  }
           	     })
           	    .setIcon(R.drawable.ic_launcher)
           	     .show();
						
			}
		});   
        
          
           
            secret.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
		    	  

				if (secret.isChecked()){
					
					 save("secret" ,"true");
					new AlertDialog.Builder(MainActivity.this)
	           	    .setTitle("Secret Emergency unlock")
	           	    .setMessage("This Feature was made to make you unlock screen when you are in hurry, also a hidden way to unlock if you are bored of unlock gesture\n\n\nHow to use?\n\nSimple, Press on the clock text.")
	           	    .setPositiveButton("Activate", new DialogInterface.OnClickListener() {
	           	        public void onClick(DialogInterface dialog, int which) { 
	           	        	
	           	        	save("emergency" ,"true");
	                  }
	           	     })
	           	    .setIcon(R.drawable.ic_launcher)
	           	     .show();
					
				
				}
				else{
					 save("secret" ,"true");
					save("emergency" ,"false"); 
				}
				
			}
		});
            
            donate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					 new AlertDialog.Builder(MainActivity.this)
	            	    .setTitle("Donate")
	            	    .setMessage("We don't get paid for our work, and we dond mind, but we would appericiate it if you liked our works and bought us a cup of coffee :)")
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
            
            about.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {


					
					 new AlertDialog.Builder(MainActivity.this)
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
            
            aboutpi.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					 new AlertDialog.Builder(MainActivity.this)
					 .setTitle("About Pi Developers ")
	            	    .setMessage("Who are pi developers?\n\nWe are a Team from Egypt we generally work on android platform.We try to make new innovative ideas for users or refining existing ideas.work for free, we are still growing and expanding. you can visit our official website pi-developers.tk to stay tuned with us or follow us on social media (facebook & twitter)  ")
	            	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	            	        public void onClick(DialogInterface dialog, int which) { 
                       }
	            	     })
	            	    .setIcon(android.R.drawable.ic_dialog_alert)
	            	     .show();
				}
			});
              
            help.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					startActivity( new Intent(MainActivity.this , Help.class));
				}
			});
      
	    	screentextcolor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				if (start.isChecked())
				{

				AmbilWarnaDialog dialog = new AmbilWarnaDialog(
						MainActivity.this, 0xffffffff,
						new OnAmbilWarnaListener() {
							@Override
							public void onOk(AmbilWarnaDialog dialog, int color) {
								
								Servicelock.save("color", color + "");
						
							}

							@Override
							public void onCancel(AmbilWarnaDialog dialog) {
							}
						});

				dialog.show();

			}
				else
				{
					Toast.makeText(getApplicationContext(), "You have to start Pi Locker first", Toast.LENGTH_SHORT).show();
				}
			}
			
		});

	    	background.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(start.isChecked())
				{

					AlertDialog.Builder alert = new AlertDialog.Builder(context);
					alert.setTitle("Backround")
					.setMessage("Select Background type")
					.setPositiveButton("Picture",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									Intent i = new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

									startActivityForResult(i, RESULT_LOAD_IMAGE);
								}
							})
					.setNeutralButton("Reset" ,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Servicelock.save("colorbg","");
									Servicelock.save("img", "");
									Servicelock.save("color", "");
								}
											})
					.setNegativeButton("Color",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									
									
									AmbilWarnaDialog dialogs = new AmbilWarnaDialog(
											MainActivity.this, 0xff0000ff,
											new OnAmbilWarnaListener() {
												@Override
												public void onOk(AmbilWarnaDialog dialog, int color) {
													
													Servicelock.save("colorbg", color + "");
													Servicelock.save("img", "");

													
												}

												@Override
												public void onCancel(AmbilWarnaDialog dialog) {
												}
											});

									dialogs.show();
									
								}
							});
					AlertDialog alertDialog = alert.create();
					alertDialog.show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "You have to start Pi Locker first", Toast.LENGTH_SHORT).show();
				}
			}
		});

	     	screentext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (start.isChecked())
				{

				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("Set Custom text");
				alert.setMessage("Please write here");
				final EditText input = new EditText(context);
				alert.setView(input);
				alert.setPositiveButton("Set text",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String srt = input.getEditableText().toString();
								Servicelock.save("text", srt);
							}
						});
				
				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						});
				AlertDialog alertDialog = alert.create();
				alertDialog.show();

				}
				else
				{
					Toast.makeText(getApplicationContext(), "You have to start Pi Locker first", Toast.LENGTH_SHORT).show();
				}
			}
		});

	    	start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (start.isChecked()) {
					startService(new Intent(MainActivity.this,Servicelock.class));
					save("on" , "true");
					
				} else {
					stopService(new Intent(MainActivity.this, Servicelock.class));
					save("on" , "false");
					
					
				}
			}
		});

	    	

	}

	public void onRadioButtonClicked(View view) {

		boolean checked = ((RadioButton) view).isChecked();

		switch (view.getId()) {
		case R.id.radio0:
			if (checked) {

				save("XYZ", "false");

			} else {

			}

			break;
		case R.id.radio1:
			if (checked) {

				save("XYZ", "true");

			} else {

			}

			break;

		}
	}

	// ////////////////////////////////////////////

	public void save(String key, String value) {
		spf = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = spf.edit();
		edit.putString(key, value);
		edit.commit();

	}

	public void loadon() {

		spf = PreferenceManager.getDefaultSharedPreferences(this);
		on = spf.getString("on", "");
		xx = spf.getString("xx", "");
		se = spf.getString("secret", "");



		if (se.equals("true")) {
			secret.setChecked(true);

		} else {
			secret.setChecked(false);
		}

		if (on.equals("false")) {
			start.setChecked(false);
			
		}
		if (on.equals("true")) {
			start.setChecked(true);
			startService(new Intent(MainActivity.this, Servicelock.class));

		}

	}

	public void loadX() {

		if (xx.equals("one")) {
		} else {
			Intent i = new Intent(MainActivity.this, Help.class);
			startActivity(i);
			xx = "one";
			save("xx", "one");

		}

	}

	// /////////////////////////////////////////

	private void doCrop(String filePath) {
		filePath = picturePath;
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		try {
			mImageCaptureUri = Uri.fromFile(new File(filePath));

			final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setType("image/*");
			List<ResolveInfo> list = getPackageManager().queryIntentActivities(
					intent, 0);

			int size = list.size();
			if (size == 0) {
				Toast.makeText(this, "Can not find image crop app",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				intent.setData(mImageCaptureUri);
				intent.putExtra("outputX", width);
				intent.putExtra("outputY", height);
				intent.putExtra("aspectX", width);
				intent.putExtra("aspectY", height);
				intent.putExtra("scaleUpIfNeeded", true);
				intent.putExtra("scale", true);
				intent.putExtra("return-data", true);

				if (size == 1) {
					Intent i = new Intent(intent);
					ResolveInfo res = list.get(0);
					i.setComponent(new ComponentName(
							res.activityInfo.packageName, res.activityInfo.name));
					startActivityForResult(i, CROP_FROM_CAMERA);
				}

				else {
					for (ResolveInfo res : list) {
						final CropOption co = new CropOption();
						co.title = getPackageManager().getApplicationLabel(
								res.activityInfo.applicationInfo);
						co.icon = getPackageManager().getApplicationIcon(
								res.activityInfo.applicationInfo);
						co.appIntent = new Intent(intent);
						co.appIntent.setComponent(new ComponentName(
								res.activityInfo.packageName,
								res.activityInfo.name));
						cropOptions.add(co);
					}

					CropOptionAdapter adapter = new CropOptionAdapter(
							getApplicationContext(), cropOptions);
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Choose Crop App");
					builder.setAdapter(adapter,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int item) {
									startActivityForResult(
											cropOptions.get(item).appIntent,
											CROP_FROM_CAMERA);
								}
							});

					builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
						public void onCancel(DialogInterface dialog) {
							if (mImageCaptureUri != null) {
								try {
									getContentResolver().delete(
											mImageCaptureUri, null, null);
									mImageCaptureUri = null;
								} catch (Exception e) {

								}

							}
						}
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
		} catch (Exception ex) {
		}
	}

	public class CropOption {
		public CharSequence title;
		public Drawable icon;
		public Intent appIntent;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex);
			Toast.makeText(getApplicationContext(), "Wallpaper saved",
					Toast.LENGTH_SHORT).show();
			doCrop(picturePath);

		} else {

			switch (requestCode) {

			case CROP_FROM_CAMERA:
				if (data == null) {
					return;
				}
				final Bundle extras = data.getExtras();
				if (extras != null) {
					try {

						Bitmap photo = extras.getParcelable("data");

						String extStorageDirectory = Environment
								.getExternalStorageDirectory().toString();
						File file = new File(extStorageDirectory,
								"pilocker_wallpaper.PNG");

						try {
							out = new FileOutputStream(file);
							photo.compress(Bitmap.CompressFormat.PNG, 100, out);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								out.close();
							} catch (Throwable ignore) {
							}
						}

						Servicelock.save("img",
								"/sdcard/pilocker_wallpaper.PNG");
						Servicelock.save("colorbg", "");

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;

			}

		}

	}
}
