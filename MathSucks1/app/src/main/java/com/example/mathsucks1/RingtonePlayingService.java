package com.example.mathsucks1;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;
    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "simplified coding";
    private static final String CHANNEL_DESC = "simplified coding Notification";
    AlarmManager alarmManager;

    Intent intent;

    PendingIntent pendingIntent;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i("LocalService", "Received start id" + startId + ": "+intent);
        createNC();

        String state = intent.getExtras().getString("Alarm Id");
//        String drugN = intent.getExtras().getString("name");
//        String drugI = intent.getExtras().getString("info");
//        int reqN = intent.getExtras().getInt("requestN");


        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        intent = new Intent(RingtonePlayingService.this,Action.class);
//
//            intent.putExtra("name", drugN);
//            intent.putExtra("info", drugI);
//            intent.putExtra("requestN", reqN);

//        media_song = MediaPlayer.create(this, R.raw.ringtone);
//        media_song.start();

        System.out.println("in Service");
        System.out.println("state " + state);


        assert state != null;
        switch (state){
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }



        if(!this.isRunning && startId == 1){

            media_song = MediaPlayer.create(this, R.raw.ringtone);
            media_song.start();

            this.isRunning = true;
            this.startId = 0;

//            pendingIntent = PendingIntent.getBroadcast(RingtonePlayingService.this, reqN, intent, PendingIntent.FLAG_UPDATE_CURRENT);





        }else if(this.isRunning && startId == 0){

             media_song.stop();
             media_song.reset();

             this.isRunning = false;
             this.startId = 0;


        }
        else{

        }






        return START_NOT_STICKY;
    }

    public void onDestroy(){

        super.onDestroy();
        this.isRunning = false;
    }
    public void createNC() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }
}
