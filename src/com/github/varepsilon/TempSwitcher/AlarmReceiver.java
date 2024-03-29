package com.github.varepsilon.TempSwitcher;

import com.github.varepsilon.TempSwitcher.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {

	public static final int REQUEST_CODE = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		switchOnSound(context);
		Toast.makeText(context, R.string.finish_message, Toast.LENGTH_LONG).show();
	}

	public static void switchOnSound(Context context) {
		setRingerMode(context, AudioManager.RINGER_MODE_NORMAL);
	}
	
	public static void switchOffSound(Context context) {
		setRingerMode(context, AudioManager.RINGER_MODE_VIBRATE);
	}
	
	public static void setRingerMode(Context context, int mode) {
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setRingerMode(mode);
	}
}
