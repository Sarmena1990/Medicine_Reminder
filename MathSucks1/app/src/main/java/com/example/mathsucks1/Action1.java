package com.example.mathsucks1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class Action1 extends BroadcastReceiver {

    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "simplified coding";
    private static final String CHANNEL_DESC = "simplified coding Notification";
    AlarmManager alarmManager;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        String drugN = intent.getExtras().getString("name");
        String drugI = intent.getExtras().getString("info");
        int reqN = intent.getExtras().getInt("requestN");

      Intent  intent1 = new Intent("com.example.mathsucks1.DisplayNotification");
        intent1.putExtra("name", drugN);
        intent1.putExtra("info", drugI);
        intent1.putExtra("requestN", reqN);
      intent1.putExtra("Alarm Id", "alarm off");
      context.sendBroadcast(intent1);




        


    }

}