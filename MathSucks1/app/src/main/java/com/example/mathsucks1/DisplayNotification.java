package com.example.mathsucks1;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class DisplayNotification extends BroadcastReceiver {

    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "simplified coding";
    private static final String CHANNEL_DESC = "simplified coding Notification";
    MediaPlayer media_song;
    String state;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {

//        state = intent.getExtras().getString("Alarm Id");
//        System.out.println("State: "+state);

        Home home = new Home();


         media_song = MediaPlayer.create(context, R.raw.guitar);
//        if(intent.getExtras() !=null) {



        String drugN = intent.getExtras().getString("name");
        String drugI = intent.getExtras().getString("info");
        int reqN = intent.getExtras().getInt("requestN");
//        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


        Intent cancelA = new Intent(context, Action1.class);
        cancelA.putExtra("name", drugN);
        cancelA.putExtra("info", drugI);
        cancelA.putExtra("requestN", reqN);
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
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_snooze,"Snooze Alarm",actionI)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

//        if(intent.getExtras().getString())
//        media_song.start();
        MusicControl.getInstance(context).playMusic();
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(reqN, builder.build());
        media_song.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!mp.isPlaying()) {
                    mp.release();
//                    state ="alarm on";
                }
                else {
                    mp.stop();
                    mp.release();
                }

            }
        });
    }

    private void stop() {
        media_song.stop();
    }


}