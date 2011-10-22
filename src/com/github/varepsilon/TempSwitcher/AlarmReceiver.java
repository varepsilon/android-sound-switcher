package com.github.varepsilon.TempSwitcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {

	public static final int REQUEST_CODE = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		switchOff(context);
	}

	public static void switchOn(Context context) {
		Toast.makeText(context, R.string.start_message, Toast.LENGTH_LONG).show();

	}
	
	public static void switchOff(Context context) {
		Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();	
	}
}
