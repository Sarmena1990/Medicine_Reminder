package com.example.mathsucks1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    Button scan ;
    Button list;
    Button logout;
    Intent intent;
    ArrayList<Medicin> listMO = new ArrayList<>();
    ArrayList<Users> listUO = new ArrayList<>();
    ArrayList<Users> userList = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    AlarmManager alarmManager;
    Users users = new Users();
    Map<String, ArrayList<Medicin>> map = new HashMap<>();
    String name;

    private TextView textViewUsername, textViewUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadDataUser();
        loadDataListMO();
        loadDataAlarm();
        loadDataUsers();
        loadDataM();

//        System.out.println("sizeofuserlist"+userList.size());
//
//        if(userList.size()>1 && !userList.get(0).getUserName().equals(userList.get(1).getUserName()))
//        cancelA1(userList.get(1).getUserName());


        textViewUsername = (TextView) findViewById(R.id.textViewUsername );
        textViewUserEmail = (TextView) findViewById(R.id.textViewUseremail);

        textViewUserEmail.setText(userList.get(0).getEmail());
        textViewUsername.setText(userList.get(0).getUserName());

        name = userList.get(0).getUserName();
        names.add(name);
        System.out.println("nameO: "+names.get(0));
        saveDataS();


        if(map.containsKey(name))
        loadAlarms();







        scan = (Button)findViewById(R.id.Scan);

        scan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                Intent intent = new Intent(Home.this, TextRecognition.class);
                startActivity(intent);
            }
        });

        list = (Button)findViewById(R.id.List);
        list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                Intent intent = new Intent(Home.this, MedicineL.class);
                startActivity(intent);
            }
        });


        logout = (Button)findViewById(R.id.Logout);

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Home.this);

                builder1.setMessage("Do you want to Log Out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelA();
                                userList.clear();
                                saveDataUser();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();



            }
        });

    }

    private void loadAlarms() {
        intent = new Intent(Home.this, DisplayNotification.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        listMO = map.get(name);

       if(listMO.size()>=1) {
           for (int i = 0; i < listMO.size(); i++) {

               System.out.println(listMO.get(i).getInfo());
               intent.putExtra("name", listMO.get(i).getName());
               intent.putExtra("info", listMO.get(i).getInfo());
               intent.putExtra("requestN", listMO.get(i).getRequestN());


               PendingIntent pendingIntent1 = PendingIntent.getBroadcast(Home.this, listMO.get(i).getRequestN(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


               alarmManager.cancel(pendingIntent1);
               alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, listMO.get(i).getTime(), 60*250 / listMO.get(i).getCount(), pendingIntent1);
           }
       }

    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson =new Gson();
        String json = gson.toJson(listMO);
        editor.putString("task listN", json);
        editor.apply();
    }

    private void loadDataListMO(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedPreferences.getString("task listN",null);
        Type type = new TypeToken<ArrayList<Medicin>>() {}.getType();
        listMO = gson.fromJson(json, type);

        if(listMO == null){
            listMO = new ArrayList<>();
        }


    }

    private void saveDataUsers(){
        SharedPreferences sharedPreferences = getSharedPreferences("Users", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson =new Gson();
        String json = gson.toJson(listUO);
        editor.putString("task listN", json);
        editor.apply();
    }

    private void loadDataUsers(){
        SharedPreferences sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedPreferences.getString("task A",null);
        Type type = new TypeToken<ArrayList<Users>>() {}.getType();
        listUO = gson.fromJson(json, type);



    }

    private void loadDataUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserName", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedPreferences.getString("task B",null);
        Type type = new TypeToken<ArrayList<Users>>() {}.getType();
        userList = gson.fromJson(json, type);
        if(userList == null){
            userList = new ArrayList<>();
        }


    }

    private void saveDataUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserName", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson =new Gson();
        String json = gson.toJson(userList);
        editor.putString("task B", json);
        editor.apply();
    }

    private void saveDataS(){
        SharedPreferences sharedPreferences = getSharedPreferences("name", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson =new Gson();
        String json = gson.toJson(names);
        editor.putString("task1", json);
        editor.apply();
    }

    private void loadDataS(){
        SharedPreferences sharedPreferences = getSharedPreferences("name", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedPreferences.getString("task1",null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        names = gson.fromJson(json, type);

        if(names == null){
            names = new ArrayList<>();
        }


    }

    private void loadDataAlarm(){
        SharedPreferences sharedPreferences = getSharedPreferences("Alarm manager", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedPreferences.getString("task A",null);
        Type type = new TypeToken<AlarmManager>() {}.getType();
        alarmManager = gson.fromJson(json, type);



    }

    private void loadDataM(){
        SharedPreferences sharedPreferences = getSharedPreferences("Map", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedPreferences.getString("task A",null);
        Type type = new TypeToken<Map<String, ArrayList<Medicin> >>() {}.getType();
        map = gson.fromJson(json, type);
        if(map == null){
            map = new HashMap<>();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelectd(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    public void cancelA(){
        intent = new Intent(Home.this, DisplayNotification.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        listMO = map.get(name);

        for(int i=0; i<listMO.size();i++){

            System.out.println(listMO.get(i).getInfo());
            intent.putExtra("name", listMO.get(i).getName());
            intent.putExtra("info", listMO.get(i).getInfo());
            intent.putExtra("requestN", listMO.get(i).getRequestN());


            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(Home.this,listMO.get(i).getRequestN(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


            alarmManager.cancel(pendingIntent1);

        }
        startActivity(new Intent(this, LogIn.class) );
    }

    public void cancelA1(String username){
        intent = new Intent(Home.this, DisplayNotification.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        listMO = map.get(username);

        for(int i=0; i<listMO.size();i++){

            System.out.println(listMO.get(i).getInfo());
            intent.putExtra("name", listMO.get(i).getName());
            intent.putExtra("info", listMO.get(i).getInfo());
            intent.putExtra("requestN", listMO.get(i).getRequestN());


            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(Home.this,listMO.get(i).getRequestN(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


            alarmManager.cancel(pendingIntent1);

        }
    }


//    public void l() {
//
//        SharedPrefManager.getInstance(this).logout();
//        finish();
//        startActivity(new Intent(this, LogIn.class));
//
//    }

//    private boolean equalO(ArrayList<Users> listUO, Users u) {
//        boolean result = false;
//        if(listUO.size()==0)
//            return result;
//        for(int i=0; i<listUO.size();i++){
//            if(listUO.get(i).getName().equals(u.getName())){
//                result =true;
//            }
//        }
//        return result;
//    }
}
