package com.example.mathsucks1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class TextRecognition extends AppCompatActivity {

    TextView totalTextView;
    EditText percentageTxt;
    EditText numberTxt;
    Set<String> fullTextSet = new HashSet<>();
    Set<String> set = new HashSet<>();
    public static Set<String> dataset1 = new HashSet<>();
    Set<String> set2 = new HashSet<>();
    String text;
    String drugname = "";
    String drugInfo = "";
    int count =0;


    boolean Run = false;

    fetch dataSet = new fetch();


    Button textbutton;

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataSet.execute();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        textbutton =(Button) findViewById(R.id.textbutton);
        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Log.w("TextRecognition", "Detector dependencies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(TextRecognition.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);

                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();

                    if (items.size() != 0) {
                        if (Run == false) {
                            textView.post(new Runnable() {
                                @Override
                                public void run() {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (int i = 0; i < items.size(); ++i) {

                                        TextBlock item = items.valueAt(i);
                                        stringBuilder.append(item.getValue());
                                        stringBuilder.append("\n");


                                    }
                                    textView.setText(stringBuilder.toString());
                                    text = stringBuilder.toString();
                                }
                            });

                            if(text !=null) {
                                String[] arrOfStr1 = text.split("\\n");
                                for (int i = 0; i < arrOfStr1.length; i++) {

                                    String[] arrOfStr2 = arrOfStr1[i].split("[ ,#%&();/]");
                                    for (int j = 0; j < arrOfStr2.length; j++) {
                                        set.add(arrOfStr2[j].toUpperCase());
                                        if (dataset1.contains(arrOfStr2[j].toUpperCase())) {
                                            drugname = arrOfStr2[j].toUpperCase();
                                        }
                                    }
                                    if (set.contains("TAKE") && (set.contains("ONE") || set.contains("1")) && drugInfo.isEmpty()) {
                                        drugInfo += "Take one ";
                                        count=1;
                                    }
                                    else if (set.contains("TAKE") && (set.contains("TWO") || set.contains("2"))&& drugInfo.isEmpty()) {
                                        drugInfo += "Take two ";
                                        count=2;
                                    }
                                    else if (set.contains("TAKE") && (set.contains("THREE")|| set.contains("3")) && drugInfo.isEmpty()) {
                                        drugInfo += "Take three ";
                                        count=3;
                                    }
                                }


                            }


//                            fullTextSet.add(text);
//                            if(text !=null) {
//                                String[] arrOfStr = text.split("[ ,\\n;]");
//                                for (int i = 0; i < arrOfStr.length; i++) {
//                                    set.add(arrOfStr[i].toUpperCase());
//                                    if (dataset1.contains(arrOfStr[i].toUpperCase())) {
//                                        drugname = arrOfStr[i].toUpperCase();
//                                    }
//                                }
//                            }
                            if (set.contains("TAKE") && set.contains("TABLET")
                                    && set.contains("EVERY") && set.contains("DAY")) {
                                Run = true;
                                drugInfo+="tablet every day";
                            }
                            else if(set.contains("TAKE") && set.contains("TABLET")
                                    && set.contains("IN") && set.contains("THE")&& set.contains("MORNING")) {
                                Run = true;
                                drugInfo += "tablet in the morning";
                            }
                            else if(set.contains("TAKE") && set.contains("CAPSULE")
                                    && set.contains("FOUR") && set.contains("TIMES")&& set.contains("A") && set.contains("DAY")) {
                                Run = true;
                                drugInfo += "capsule four times a day";
                                count=4;
                            }
//                            System.out.println("The first Activity:");
//                            System.out.println(set);
//                            System.out.println("The dataset Activity:");
//                            System.out.println(dataset1);
//                        }
                        } else {

                            Run = false;
                            set.clear();
//                            fullTextSet.clear();
                            Intent intent = new Intent(TextRecognition.this, MedicineL.class);
                            intent.putExtra("name", drugname);
                            intent.putExtra("info", drugInfo);
                            intent.putExtra("count",count);
                            startActivity(intent);



                        }
                    }
                }
            });
        }


//        textbutton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void  onClick(View view){
//                Thread thread;
//                thread = new Thread();
//
//                startActivity(new Intent(getApplicationContext(),MedicineL.class));
//            }
//        });
//
//
//    }

//    public Set<String> getSet(){
//        return set;
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    }
}
