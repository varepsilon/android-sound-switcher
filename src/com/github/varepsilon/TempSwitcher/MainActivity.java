/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.varepsilon.TempSwitcher;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * This class provides a basic demonstration of how to write an Android
 * activity. Inside of its window, it places a single view: an EditText that
 * displays and edits some internal text.
 */
public class MainActivity extends Activity {
    
    static final private int BACK_ID = Menu.FIRST;
    static final private int CLEAR_ID = Menu.FIRST + 1;
    
    static final private int DEFAULT_HOUR = 1;
    static final private int DEFAULT_MIN = 30;

    private TextView mTimeDisplay;
    private Button mChangeTime;
    private Button mApply;
    
    private Context mContext;

    private int mHour;
    private int mMinute;

    static final int TIME_DIALOG_ID = 0;
    
    public MainActivity() {
    }

    /** Called with the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        
        mHour = DEFAULT_HOUR;
        mMinute = DEFAULT_MIN;
        setContentView(R.layout.main_activity);

        // capture our View elements
        mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
        mChangeTime = (Button) findViewById(R.id.changeTime);
        mApply = (Button) findViewById(R.id.apply);

        // add a click listener to the button
        mChangeTime.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
        mApply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, AlarmReceiver.class);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(
						mContext, AlarmReceiver.REQUEST_CODE, intent, 0);
				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
				alarmManager.set(AlarmManager.RTC_WAKEUP,
						System.currentTimeMillis() + getMilliseconds(mHour, mMinute),
						pendingIntent
				);
				Toast.makeText(mContext, "Alarm set", Toast.LENGTH_LONG).show();
			}
		});
        // display the current time
        updateDisplay();
    }
    
    // updates the time we display in the TextView
    private void updateDisplay() {
        mTimeDisplay.setText(
            new StringBuilder()
                    .append(pad(mHour)).append(":")
                    .append(pad(mMinute)));
    }
    
    private static int getMilliseconds(int hour, int min, int sec) {
    	return ((hour * 60 + min) * 60 + sec) * 1000;
    }
    
    private static int getMilliseconds(int hour, int min) {
    	return getMilliseconds(hour, min, 0);
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    
    // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                updateDisplay();
            }
        };
        
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    mTimeSetListener, mHour, mMinute, true);
        }
        return null;
    }

    /**
     * Called when the activity is about to start interacting with the user.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Called when your activity's options menu needs to be created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // We are going to create two menus. Note that we assign them
        // unique integer IDs, labels from our string resources,
        menu.add(0, BACK_ID, 0, R.string.back);
        menu.add(0, CLEAR_ID, 0, R.string.clear);

        return true;
    }

    /**
     * Called right before your activity's option menu is displayed.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        return true;
    }

    /**
     * Called when a menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case BACK_ID:
            finish();
            return true;
        case CLEAR_ID:
            mHour = DEFAULT_HOUR;
            mMinute = DEFAULT_MIN;
            updateDisplay();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A call-back for when the user presses the back button.
     */
    OnClickListener mBackListener = new OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    };

}
