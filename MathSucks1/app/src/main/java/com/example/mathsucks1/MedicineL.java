package com.example.mathsucks1;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MedicineL extends AppCompatActivity {

    private static final String TAG = "MedicineL";
    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "simplified coding";
    private static final String CHANNEL_DESC = "simplified coding Notification";


    ArrayList<String> listN;

    ArrayList<String> listI;

    ArrayList<Medicin> listMO;

    ArrayAdapter<String> adapter;

    SwipeMenuListView listView;

    String title = "MathSucks1";

    String content ;

    Button camera ;

    Button home;

    Medicin medicin = new Medicin();

//    private int notificationId = 123;

//    Random r = new Random();
//
//    int m =r.nextInt();
    int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

    AlarmManager alarmManager;

    Intent intent;
    Intent intent1;

    PendingIntent pendingIntent;
//    PendingIntent pendingIntent1;
//    MediaPlayer media_song;

//    Bundle extras = intent.getExtras();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textrecognition);

//        String drugN = getIntent().getExtras().getString("name");
//
//        String drugI = getIntent().getExtras().getString("info");



        camera=(Button)findViewById(R.id.Camera) ;

        home=(Button)findViewById(R.id.Home) ;

        listView = (SwipeMenuListView) findViewById(R.id.listView);

        listN = new ArrayList<>();



          loadData();
//        loadDataI();


//      To connect the listeview with sepecified arraylist
        adapter = new ArrayAdapter<String>(MedicineL.this,android.R.layout.simple_list_item_multiple_choice, listN);

//        adapter.notifyDataSetChanged();
//        listView.setAdapter(adapter);



        createNC();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        intent = new Intent(MedicineL.this, DisplayNotification.class);
//        intent1 = new Intent(MedicineL.this, Action1.class);
        if(getIntent().getExtras() == null){
        intent.putExtra("name", medicin.getName());
        intent.putExtra("info", medicin.getInfo());
        intent.putExtra("requestN", medicin.getRequestN());
        }

        pendingIntent = PendingIntent.getBroadcast(MedicineL.this, m, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        pendingIntent1 = PendingIntent.getBroadcast(MedicineL.this, m, intent1, PendingIntent.FLAG_UPDATE_CURRENT);




        if(getIntent().getExtras() != null) {
            medicin.setName(getIntent().getExtras().getString("name"));

            medicin.setInfo(getIntent().getExtras().getString("info"));

            medicin.setCount(getIntent().getExtras().getInt("count"));

            medicin.setRequestN(m);

//            medicin.setnTime((int) (SystemClock.elapsedRealtime() + (1 * 60 * 250)));


//            System.out.println("the count: " + getIntent().getExtras().getInt("count"));
//
//            System.out.println("the count2: " + medicin.getCount());


            if (!equalO(listMO, medicin)) {

                intent.putExtra("name", medicin.getName());
                intent.putExtra("info", medicin.getInfo());
                intent.putExtra("requestN", medicin.getRequestN());
                intent.putExtra("Alarm Id", "alarm on");

                pendingIntent = PendingIntent.getBroadcast(MedicineL.this, medicin.getRequestN(), intent, PendingIntent.FLAG_UPDATE_CURRENT);




                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (60*250/medicin.getCount()), 60*250/medicin.getCount(), pendingIntent);



                listMO.add(medicin);
                saveData();
            }

//            System.out.println("the size of list is: " + listMO.size());

        }


        for(int i=0;i<listMO.size();i++) {
            listN.add(listMO.get(i).getName());
        }
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);



        camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                Intent intent = new Intent(MedicineL.this, TextRecognition.class);
                startActivity(intent);
            }
        });


        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                Intent intent = new Intent(MedicineL.this, Home.class);
                startActivity(intent);

//                SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.clear();
//                editor.commit();
//                finish();
            }
        });


//        Calendar calendar = Calendar.getInstance();






        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(170);
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
                openItem.setIcon(R.drawable.ic_info);
                // add to menu
                menu.addMenuItem(openItem);


                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_remove);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MedicineL.this);

                        builder.setMessage(listMO.get(position).getInfo())
                                .setCancelable(false)
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        break;
                    case 1:
                        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(MedicineL.this,listMO.get(position).getRequestN(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.cancel(pendingIntent1);
                        listMO.remove(position);
                        listN.remove(position);
                        saveData();
//                       saveDataI();
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);

                        break;

                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

    }

    private boolean equalO(ArrayList<Medicin> listMO, Medicin medicin) {
        boolean result = false;
        for(int i=0; i<listMO.size();i++){
            if(listMO.get(i).getName().equals(medicin.getName()) && listMO.get(i).getInfo().equals(medicin.getInfo())){
                result =true;
            }
        }
        return result;
    }


    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson =new Gson();
        String json = gson.toJson(listMO);
        editor.putString("task listN", json);
        editor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedPreferences.getString("task listN",null);
        Type type = new TypeToken<ArrayList<Medicin>>() {}.getType();
        listMO = gson.fromJson(json, type);

        if(listMO == null){
            listMO = new ArrayList<>();
        }


    }



   public void createNC() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription(CHANNEL_DESC);
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
        }

    }

    public void displayNotification(){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(MedicineL.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MedicineL.this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MedicineL.this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_medicine)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MedicineL.this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }


//    private void saveDataI(){
//        SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences1", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson =new Gson();
//        String json = gson.toJson(listI);
//        editor.putString("task listI", json);
//        editor.apply();
//    }
//    private void loadDataI(){
//        SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences1", MODE_PRIVATE);
//        Gson gson =new Gson();
//        String json = sharedPreferences.getString("task listI",null);
//        Type type = new TypeToken<ArrayList<String>>() {}.getType();
//        listI = gson.fromJson(json, type);
//
//        if(listI == null){
//            listI = new ArrayList<>();
//        }
//
//
//    }
}

