package com.example.mathsucks1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import static android.content.Context.ALARM_SERVICE;

public class Medicin  {

    private String name;
    private String info;
    private int count;
    private int requestN;
    private long nTime;

//    public  AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

    public Medicin() {
        this.name = "";
        this.info = "";
        this.count = 0;
        this.requestN = 0;
        this.nTime = 0;

//        this.notification();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRequestN() {
        return requestN;
    }

    public void setRequestN(int requestN) {
        this.requestN = requestN;
    }

    public long getnTime() {
        return nTime;
    }

    public void setnTime(int nTime) {
        this.nTime = nTime;
    }


//    public void notification() {
//
//        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        if(medicin.getInfo().contains("one"))
//            this.count=1;
//
//        Intent intent = new Intent(Medicin.this, DisplayNotification.class);
//
//        intent.putExtra("name", medicin.getName());
//        intent.putExtra("info", medicin.getInfo());
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(Medicin.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (AlarmManager.INTERVAL_FIFTEEN_MINUTES / count), AlarmManager.INTERVAL_FIFTEEN_MINUTES / count, pendingIntent);
//
//    }
}
