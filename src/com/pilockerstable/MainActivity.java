package com.pilockerstable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.almas.ShortcutSettings;

public class MainActivity extends ActionBarActivity {

	/**
	 * @author MohamedRashad
	 * 
	 */
	public final int CROP_FROM_CAMERA = 0;
	private static int RESULT_LOAD_IMAGE = 1;
	static String on, se;
	private Uri mImageCaptureUri;
	static SharedPreferences spf;
	boolean admin;
	int colors, height, width, size;
	String XYZ;
	Context context = this;
	CheckBox start, secret, skip, DoubleTap, enter, ges_hide;
	Button screentext, background, screentextcolor, aboutpi, help, donate, pin,
			button2, locknow, shortb;
	String picturePath, load, xx, DD, srt, skips, tap, lock, Pin, Pass;
	FileOutputStream out;
	EditText input1;
	DisplayMetrics displaymetrics = new DisplayMetrics();
	ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
	Uri selectedImage;
	Cursor cursor;
	DevicePolicyManager policyManager;
	ComponentName adminReceiver;

	String extStorageDirectory = Environment.getExternalStorageDirectory()
			.toString();
	File file = new File(extStorageDirectory, "pilocker_wallpaper.PNG");
	String jjk;
	android.support.v7.app.ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(0xff00BCD4));
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(Html
				.fromHtml("<font color='#ffffff'> <b> Pi Locker </b> </font>"));

		setContentView(R.layout.activity_main);

		FontsOverride.setDefaultFont(this, "DEFAULT", "Roboto-Regular.ttf");
		FontsOverride.setDefaultFont(this, "MONOSPACE", "Roboto-Regular.ttf");
		FontsOverride.setDefaultFont(this, "SANS_SERIF", "Roboto-Regular.ttf");

		start = (CheckBox) findViewById(R.id.start);

		for (int i = 0; i < 4; i++) {
			String s = Settings.System.getString(context.getContentResolver(),
					"PiSC" + i);

			if (s == null | s == "") {
				Settings.System.putString(context.getContentResolver(), "PiSC"
						+ i, "com.pilockerstable");
			}
		}

		secret = (CheckBox) findViewById(R.id.checkBox1);
		screentext = (Button) findViewById(R.id.screentext);
		background = (Button) findViewById(R.id.background);
		screentextcolor = (Button) findViewById(R.id.screentextcolor);
		help = (Button) findViewById(R.id.help);
		aboutpi = (Button) findViewById(R.id.aboutpi);

		pin = (Button) findViewById(R.id.button1);
		shortb = (Button) findViewById(R.id.shortcut);
		button2 = (Button) findViewById(R.id.button2);
		locknow = (Button) findViewById(R.id.button3);
		skip = (CheckBox) findViewById(R.id.checkBox3);
		DoubleTap = (CheckBox) findViewById(R.id.checkBox4);

		policyManager = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		adminReceiver = new ComponentName(context, DeviceAdmin.class);
		admin = policyManager.isAdminActive(adminReceiver);

		loadon();
		loadX();

		shortb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this,
						ShortcutSettings.class));
			}
		});

		XYZ = getString("XYZ");
		jjk = getString("UsesGestures");

		if (XYZ.equals("true")) {

		} else {

		}

		locknow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (skip.equals("1")) {

					startActivity(new Intent(MainActivity.this,
							PinActivity.class));
					finish();

				} else {

					startActivity(new Intent(MainActivity.this, Lock.class));
					finish();

				}

			}
		});

		pin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				AlertDialog.Builder alert = new AlertDialog.Builder(context);

				final String getPas = getString("pass");
				alert.setMessage("Please write here\n\nYou should only write numbers here or the app will misbehave.");

				if (getPas != "") {
					alert.setTitle("Enter Old Pin");
				} else {
					alert.setTitle("Enter New Pin");
				}

				input1 = new EditText(context);

				alert.setView(input1);
				alert.setPositiveButton("Confirm",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int whichButton) {
								String p = input1.getEditableText().toString();
								if (p.contains("a") || p.contains("b")
										|| p.contains("c") || p.contains("d")
										|| p.contains("e") || p.contains("f")
										|| p.contains("g") || p.contains("h")
										|| p.contains("i") || p.contains("j")
										|| p.contains("k") || p.contains("l")
										|| p.contains("m") || p.contains("n")
										|| p.contains("o") || p.contains("p")
										|| p.contains("r") || p.contains("s")
										|| p.contains("t") || p.contains("u")
										|| p.contains("v") || p.contains("w")
										|| p.contains("q") || p.contains("x")
										|| p.contains("y") || p.contains("z")) {
									
									Toast.makeText(
											context,
											"The pin only can hold numbers from 0 to 9 .. we told you this",
											Toast.LENGTH_LONG)
											.show();
									

								} else {
									if (p.trim().length() < 4) {

										Toast.makeText(
												context,
												"Pin Must be atleast 4 Characters, try again",
												Toast.LENGTH_SHORT).show();

									} else if (p.trim().length() >= 4
											&& p.trim().length() <= 12) {

										if (getPas != "") {

											dialog.cancel();

											AlertDialog.Builder builderfinal = new AlertDialog.Builder(
													MainActivity.this);
											builderfinal
													.setTitle("Enter Your New Password");
											final EditText ed = new EditText(
													context);
											ed.setInputType(InputType.TYPE_CLASS_NUMBER);
											builderfinal.setView(ed);
											builderfinal
													.setNegativeButton(
															"No",
															new DialogInterface.OnClickListener() {

																public void onClick(
																		DialogInterface dialog,
																		int whichButton) {

																	dialog.cancel();

																}
															});
											builderfinal
													.setPositiveButton(
															"Confirm",
															new DialogInterface.OnClickListener() {

																public void onClick(
																		DialogInterface dialog,
																		int whichButton) {
																	String b = ed
																			.getText()
																			.toString();

																	if (b.trim()
																			.length() > 12) {

																		Toast.makeText(
																				context,
																				"The password must be less than 12 characters",
																				Toast.LENGTH_SHORT)
																				.show();
																	} else if (b
																			.trim()
																			.length() >= 4
																			&& b.trim()
																					.length() <= 12) {
																		save("pass",
																				"");

																		save("pin",
																				b);
																	} else if (b
																			.trim()
																			.length() < 4) {

																		Toast.makeText(
																				context,
																				"Pin Must be atleast 4 Characters, try again",
																				Toast.LENGTH_SHORT)
																				.show();
																	}
																}
															});

											builderfinal.show();
										} else if (getPas == "") {

											save("pass", "");
											save("pin", p);

											Toast.makeText(context,
													"Password Updated",
													Toast.LENGTH_SHORT).show();

											skip.setEnabled(true);
										}

									} else if (p.trim().length() > 12) {

										Toast.makeText(
												context,
												"The password must be less than 12 characters",
												Toast.LENGTH_SHORT).show();

									}

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
						.setMessage(
								"Are you sure you want to reset security pin/password? \nthis will leave your phone UNSECURED")
						.setPositiveButton("Reset",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										save("pin", "");

										skip.setEnabled(false);

									}
								})

						.setNegativeButton("NO",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).setIcon(R.drawable.ic_launcher).show();

			}
		});

		skip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (skip.isChecked()) {

					save("skip", "1");

				} else {

					save("skip", "0");

				}

			}
		});

		DoubleTap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (DoubleTap.isChecked()) {

					if (admin) {

						save("tap", "enable");

					} else {

						Toast.makeText(
								context,
								"Device admin is not enabled!\nEnable from\nSetting > Security > Device admin\nin order to use this feature",
								Toast.LENGTH_LONG).show();
						DoubleTap.setChecked(false);
					}

				} else {

					save("tap", "disable");

				}

			}
		});

		secret.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (secret.isChecked()) {

					save("secret", "true");
					new AlertDialog.Builder(MainActivity.this)
							.setTitle("Secret Emergency unlock")
							.setMessage(
									"This Feature was made to make you unlock screen when you are in hurry, also a hidden way to unlock if you are bored of unlock gesture\n\n\nUsage:\n\n-Press on the clock text.\n-Voila\n\n\nNote:\nThis will not work when the pin security is active")
							.setPositiveButton("Activate",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

											save("emergency", "true");
											save("emergencyb", "true");
										}
									})

							.setIcon(R.drawable.ic_launcher).show();

				} else {
					save("secret", "true");
					save("emergency", "false");
					save("emergencyb", "false");
				}

			}
		});

		aboutpi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("About Pi Developers ")
						.setMessage(
								"Pi Developers\nCreativity is our middle name.\n\nWe are team of young developers trying to indrotude new ideas to android users and make them use the most of their phones.\n\nOur goal is to provide same experince on all devices possible, we support all major android versions (3.3.x - 5.x)\n\nTeam members:\n\n-Mohamed Rashad\n-Mahmoud Saleh\n-Ahmad Sameh\n-Mostafa Samy\n-Mohamed Ashraf\n-Yassmin Gouda\n-Mahmoud El-Tarouty\n-Mostafa Amr\n-Israa Shams\n-Robin (AndroidFire)\n\n\nCheck our Blog 'Pidevelopers.blogspot.com'\nOR\nFollow us on Twitter @Pi_Developers\nOr\nCheck our website 'pi-developers.github.io'")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).setIcon(android.R.drawable.ic_dialog_alert)
						.show();
			}
		});

		help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, Help.class));
			}
		});

		screentextcolor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (start.isChecked()) {

					AmbilWarnaDialog dialog = new AmbilWarnaDialog(
							MainActivity.this, 0xffffffff,
							new OnAmbilWarnaListener() {

								@Override
								public void onOk(AmbilWarnaDialog dialog,
										int color) {

									save("color", color + "");

								}

								@Override
								public void onCancel(AmbilWarnaDialog dialog) {
								}
							});

					dialog.show();

				} else

				{
					Toast.makeText(getApplicationContext(),
							"You have to start Pi Locker first",
							Toast.LENGTH_SHORT).show();
				}
			}

		});

		background.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (start.isChecked()) {

					AlertDialog.Builder alert = new AlertDialog.Builder(context);
					alert.setTitle("Backround")
							.setMessage("Select Background type")
							.setPositiveButton("Picture",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {

											Intent i = new Intent(
													Intent.ACTION_PICK,
													android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
											startActivityForResult(i,
													RESULT_LOAD_IMAGE);

										}
									})

							.setNeutralButton("Reset",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {

											save("colorbg", "");
											save("img", "");
											save("color", "");

										}
									});
					AlertDialog alertDialog = alert.create();
					alertDialog.show();

				} else {
					Toast.makeText(getApplicationContext(),
							"You have to start Pi Locker first",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		screentext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (start.isChecked()) {

					AlertDialog.Builder alert = new AlertDialog.Builder(context);
					alert.setTitle("Personal Text");
					alert.setMessage("Please write here");
					final EditText input1 = new EditText(context);
					alert.setView(input1);

					alert.setPositiveButton("Set text",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									srt = input1.getEditableText().toString();
									save("text", srt);
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

				} else {
					Toast.makeText(getApplicationContext(),
							"You have to start Pi Locker first",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		start.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (start.isChecked()) {

					Settings.System.putInt(getContentResolver(), "PiLocker", 1);
					startService(new Intent(MainActivity.this,
							LockerService.class));
					save("on", "true");
					secret.setEnabled(true);
					skip.setEnabled(true);
					DoubleTap.setEnabled(true);

				} else {

					Settings.System.putInt(getContentResolver(), "PiLocker", 0);
					stopService(new Intent(MainActivity.this,
							LockerService.class));
					startService(new Intent(MainActivity.this,
							LockerService.class));
					save("on", "false");
					secret.setEnabled(false);
					skip.setEnabled(false);
					DoubleTap.setEnabled(false);
					Intent i = new Intent();
					i.setAction("com.androidfire.Restore_Pi");
					sendBroadcast(i);

				}
			}
		});

	}

	public void save(String key, String value) {
		spf = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = spf.edit();
		edit.putString(key, value);
		edit.commit();

	}

	public String getString(String key) {
		spf = PreferenceManager.getDefaultSharedPreferences(this);
		return spf.getString(key, "");

	}

	public void loadon() {

		spf = PreferenceManager.getDefaultSharedPreferences(this);
		on = spf.getString("on", "");
		xx = spf.getString("xx", "");
		se = spf.getString("emergencyb", "false");
		skips = spf.getString("skip", "0");
		tap = spf.getString("tap", "disable");
		lock = spf.getString("lock", "");

		Pass = spf.getString("pass", "");
		Pin = spf.getString("pin", "");

		if (Pass.equals("") && Pin.equals("")) {

			skip.setEnabled(false);

		}

		if (tap.equals("enable")) {
			DoubleTap.setChecked(true);

		} else {
			DoubleTap.setChecked(false);
		}

		if (skips.equals("1")) {
			skip.setChecked(true);

		} else {
			skip.setChecked(false);
		}

		if (se.equals("true")) {
			secret.setChecked(true);

		} else {
			secret.setChecked(false);
		}

		if (on.equals("false")) {
			secret.setEnabled(false);
			skip.setEnabled(false);
			DoubleTap.setEnabled(false);

		}
		if (on.equals("true")) {
			start.setChecked(true);
			startService(new Intent(MainActivity.this, LockerService.class));

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

	private void doCrop(String filePath) {

		filePath = picturePath;

		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;

		try {

			mImageCaptureUri = Uri.fromFile(new File(filePath));

			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setType("image/*");
			List<ResolveInfo> list = getPackageManager().queryIntentActivities(
					intent, 0);

			size = list.size();

			if (size == 0) {

				Toast.makeText(this, "Can not find gallery app",
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
				intent.putExtra("crop", "true");

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {

			selectedImage = data.getData();

			String[] filePathColumn = {

			MediaStore.Images.Media.DATA

			};

			cursor = getContentResolver().query(selectedImage, filePathColumn,
					null, null, null);
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

						try {

							out = new FileOutputStream(file);
							photo.compress(Bitmap.CompressFormat.PNG, 100, out);
						}

						catch (Exception e) {
						} finally {

							try {
								out.close();
							} catch (Throwable ignore) {
							}
						}

						save("img",
								getString(R.string._sdcard_pilocker_wallpaper_png));
						save("colorbg", "");

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;

			}

		}

	}
}