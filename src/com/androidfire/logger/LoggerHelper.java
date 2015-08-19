package com.androidfire.logger;

import android.content.Context;
import android.util.Log;

/**
 * This class is made to log easily from other other class
 */
public class LoggerHelper {
	static String name;
	
	public static String getAppName(Context context) {
		  int stringId = context.getApplicationInfo().labelRes;
		    return context.getString(stringId);
	}
	public static String dg(Context ctx ,String msg) {
		Log.d(getAppName(ctx), msg);
		return msg;
	}
	public static String er (Context ctx,String msg) {
		Log.e(getAppName(ctx), msg);
		return msg;
	}
	public static String io (Context ctx,String msg) {
		Log.i(getAppName(ctx), msg);
		return msg;
	}
}
