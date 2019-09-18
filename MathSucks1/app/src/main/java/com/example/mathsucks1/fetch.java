package com.example.mathsucks1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class fetch extends AsyncTask<Void,Void,Void> {

    String data = "";

    String dataParsed = "";

    String singleParsed = "";

    Set<String> set = new HashSet<String>();
    //This is for separating each word into one index in the array
//    String[] objects = new String[set.size()];


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

//        System.out.println("JSon");
//        System.out.println(set);
//
//        if(set.contains("ASPIRIN")) {
//            System.out.println("aspirin exist");
//        }
//        set.toArray(objects);
//        final ArrayList<String> list = new ArrayList<String>(Arrays.asList(objects));



//        MedicineL.printSet.setText(this.set.toString());

        Iterator<String> i =set.iterator();
        while (i.hasNext())
            TextRecognition.dataset1.add(i.next());


    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            URL url = new URL("https://api.myjson.com/bins/1dakyz");

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

            InputStream inputStream = httpsURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";

            while (line !=null){

                line = bufferedReader.readLine();
                data = data+line;
            }

            JSONArray JA = new JSONArray(data);

            for(int i=0;i<JA.length();i++){

                JSONObject JO = (JSONObject) JA.get(i);

                singleParsed = JO.get("term") +"";


                set.add(singleParsed.toUpperCase());

            }




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    public Set<String> getSet(){
        return set;
    }

}
