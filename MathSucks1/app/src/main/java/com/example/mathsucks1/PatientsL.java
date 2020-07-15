package com.example.mathsucks1;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PatientsL extends AppCompatActivity {


    ArrayList<String> listN;

    ArrayList<Medicin> listMO;

    ArrayAdapter<String> adapter;

    SwipeMenuListView listView;

    Medicin medicin = new Medicin();

    Intent intent;

    Button addPatient;

    Button home;

    EditText input;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textrecognition);

        listView = (SwipeMenuListView) findViewById(R.id.listView);

        listN = new ArrayList<>();



        loadData();

        adapter = new ArrayAdapter<String>(PatientsL.this,android.R.layout.simple_list_item_multiple_choice, listN);

        intent = new Intent(PatientsL.this, DisplayNotification.class);


        if(getIntent().getExtras() != null) {
            medicin.setName(getIntent().getExtras().getString("name"));

            medicin.setInfo(getIntent().getExtras().getString("info"));

            medicin.setCount(getIntent().getExtras().getInt("count"));




            if (!equalO(listMO, medicin)) {

                intent.putExtra("name", medicin.getName());
                intent.putExtra("info", medicin.getInfo());
                intent.putExtra("requestN", medicin.getRequestN());
                intent.putExtra("Alarm Id", "alarm on");



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



        addPatient.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view) {

            }
        });


        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                Intent intent = new Intent(PatientsL.this, Home.class);
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
                        startActivity(new Intent(PatientsL.this, MedicineL.class));

                        break;
                    case 1:
                        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(PatientsL.this,listMO.get(position).getRequestN(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        listMO.remove(position);
                        listN.remove(position);
                        saveData();
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);

                        break;

                }
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


}
