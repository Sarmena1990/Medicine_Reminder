package com.example.mathsucks1;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class Action1 extends BroadcastReceiver {

    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "simplified coding";
    private static final String CHANNEL_DESC = "simplified coding Notification";
    AlarmManager alarmManager;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {

        MusicControl.getInstance(context).stopMusic();

    }


}