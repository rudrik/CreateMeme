package com.apptechx.creatememe.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils extends Application {

	// This variable store the context of Application
	public static Context applicationContext;

	// private context made because no one can use ot store in this context
	private Context context = null;
	private String LOG_TAG = "JOBAKA_LOG";
	private SharedPreferences preferences = null;
	private SharedPreferences.Editor editor = null;

	/*
	 * Parameterized Constructor made because getting fresh context every time
	 * and to make methods easy.
	 */
	public Utils(Context con) {
		context = con;
		preferences = PreferenceManager.getDefaultSharedPreferences(con);
		editor = preferences.edit();
		// LOG_TAG = context.getPackageName().toString();
	}

	/**
	 * @param message
	 *            Pass message to show user
	 * @return It will return long toast message whatever you pass in your
	 *         application
	 */
	public void Toast(String message) {
		final String onTimeMsg = message;
		((Activity) context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, onTimeMsg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * @param buttonName
	 *            set yes no or cancel
	 * @param message
	 *            message in alert box
	 *
	 * @return AlertBox to use as user message
	 */
	public void showAlertMessage(String buttonName, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setPositiveButton(buttonName,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	/**
	 * @param emailAddress
	 *            Passyour emiailaddress string to check
	 * @return It will return true if email address is valid or false in case
	 *         email is not valid
	 */
	public boolean isEmailValid(String emailAddress) {
		String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
				+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
				+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
				+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

		Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(emailAddress);

		if (matcher.matches())
			return true;
		else
			return false;
	}

	/**
	 * @return It will check your Internet connection.True if any net connected.
	 */
	public boolean isNetConnected() {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	/**
	 * @return This method returns system current time , change format as per
	 *         your reuirement , Locale is also set as english so take care of
	 *         that also.It is HH:mm 24 hour format
	 */
	public String getCurrentTime() {
		return new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
				.format(new Date());
	}

	public String getCurrentTimePath() {
		return new SimpleDateFormat("HHmmss", Locale.ENGLISH)
				.format(new Date());
	}

	/**
	 * @return This method returns system current date in dd/MM/yyyy format
	 */
	public String getCurrentDate() {
		return new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
				.format(new Date());
	}

	/**
	 * Use to save string preferences
	 */
	public void setScriptPrefrences(String key, String msg) {
		editor.putString(key, msg);
		editor.commit();

	}

	/**
	 * Use to get string preference
	 */
	public String getScriptPrefrences(String key) {
		return preferences.getString(key, "");
	}


    /**
     * set string Preference
     */
    public void setPreference(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }


    /**
	 * get string preferences
	 */
	public String getPreference(String key) {
		return preferences.getString(key, "");
	}

    /**
     * get string preferences
     */
    public int getIntPreference(String key) {
        return preferences.getInt(key, 0);
    }


    /**
     * Use to set boolean preference
     */
    public void setIntPrefrences(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

	/**
	 * Use to set boolean preference
	 */
	public void setBoolPrefrences(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * use to get boolean preference
	 */
	public boolean getBoolPref(String key) {
		return preferences.getBoolean(key, false);
	}

	/**
	 * @return This method kills all processes of application which is running
	 *         in back ground , we can also use
	 *         android.os.Process.killProcess(pid) to exit from application
	 */
	public void killProcess(Context context) {
		int pid = 0;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> pids = am
				.getRunningAppProcesses();
		for (int i = 0; i < pids.size(); i++) {
			ActivityManager.RunningAppProcessInfo info = pids.get(i);
			if (info.processName.equalsIgnoreCase("com.dbb.activity")) {
				pid = info.pid;
			}
		}
		android.os.Process.killProcess(pid);
	}

	/**
	 * @return true is tablet resolution is there
	 */
	public boolean isTablet() {
		boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
		boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
		return (xlarge || large);
	}

	/* All logs */
	public void log(String message) {
		Log.d(LOG_TAG, message);
	}

	public void infoLog(String message) {
		Log.i(LOG_TAG, message);
	}

	public void errorLog(String message) {
		Log.e(LOG_TAG, message);
	}

	public void verboseLog(String message) {
		Log.v(LOG_TAG, message);
	}

	public void warnLog(String message) {
		Log.w(LOG_TAG, message);
	}

	public boolean isUsingMobileData() {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo mobileInfo = connectivity
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (mobileInfo.getState() == NetworkInfo.State.CONNECTED
				|| mobileInfo.getState() == NetworkInfo.State.CONNECTING) {
			return true;
		}
		return false;
	}

	/**
	 * @return IMEI NO
	 */
	public String getImeiNo() {
		TelephonyManager teleManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return teleManager.getDeviceId();
	}

	public boolean isUsingWiFi() {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiInfo = connectivity
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiInfo.getState() == NetworkInfo.State.CONNECTED
				|| wifiInfo.getState() == NetworkInfo.State.CONNECTING) {
			return true;
		}

		return false;
	}

	public boolean isMemorycard() {

		Boolean isSDPresent = android.os.Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED);

		if (isSDPresent) {
			// yes SD-card is present
			return true;
		} else {
			// Sorry
			return false;
		}

	}
}
