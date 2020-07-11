package com.example.mathsucks1;

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

public class Action extends BroadcastReceiver {

    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "simplified coding";
    private static final String CHANNEL_DESC = "simplified coding Notification";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {

                String drugN = intent.getExtras().getString("name");
                String drugI = intent.getExtras().getString("info");
                int reqN = intent.getExtras().getInt("requestN");
                int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


                Intent cancelA = new Intent(context, DisplayNotification.class);
                PendingIntent actionI = PendingIntent.getBroadcast(context,reqN,cancelA,PendingIntent.FLAG_UPDATE_CURRENT);



        // Create an explicit intent for an Activity in your app
        Intent intent1 = new Intent(context, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_medicine)
                .setContentTitle(drugN)
                .setContentText(drugI)
                .setColor(Color.BLUE)
                .setSound(Uri.parse("android.resource://"
                + context.getPackageName() + "/" + R.raw.ringtone))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_snooze,"Snooze Alarm",actionI)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(reqN, builder.build());


    }

}