package com.github.varepsilon.TempSwitcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {

	public static final int REQUEST_CODE = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();	
	}

	public static boolean switchOffSound() {
		return false;
	}
	
	public static boolean switchOnSound() {
		return false;
	}
}
