package com.example.mathsucks1;

public class Medicin  {

    private String name;
    private String info;
    private int count;
    private int requestN;
    private long time;

//    public  AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

    public Medicin() {
        this.name = "";
        this.info = "";
        this.count = 0;
        this.requestN = 0;
        this.time = 0;


//        this.notification();
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
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


}
