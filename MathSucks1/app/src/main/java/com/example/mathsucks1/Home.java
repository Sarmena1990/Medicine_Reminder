package com.example.mathsucks1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button scan ;
    Button list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
    }
}
