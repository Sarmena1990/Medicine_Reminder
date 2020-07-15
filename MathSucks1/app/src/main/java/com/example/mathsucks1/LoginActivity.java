package com.example.mathsucks1;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;
    ArrayList<Users> userList = new ArrayList<>();
    Map<String, ArrayList<Users>> accounts = new HashMap<>();
    ArrayList<Users> userName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        if(SharedPrefManager.getInstance(this).isLoggedIn()){
//            finish();
//            startActivity(new Intent(this, Home.class));
//            return;
//        }

        loadDataM();

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait...");

        buttonLogin.setOnClickListener(this);

    }
    private void userLogin(){
         final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

//        System.out.println("login");
//        System.out.println(username);
//        System.out.println(password);
//        System.out.println("password"+accounts.get(username).get(0).getPassword());
//        System.out.println("email"+accounts.get(username).get(0).getEmail());
        if(!username.isEmpty() && !password.isEmpty()) {
            if (accounts.containsKey(username) && accounts.get(username).get(0).getPassword().equals(password)) {
                userList.add(accounts.get(username).get(0));
                System.out.println("password" + userList.get(0).getPassword());
                saveDataUser();
                startActivity(new Intent(this, Home.class));
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                builder.setMessage("The UserName or Password is Wrong try again")
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

            builder.setMessage("None of the fields can be blank. Please fill them all.")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }




//
//        progressDialog.show();
//
//        StringRequest stringRequest= new StringRequest(
//                Request.Method.POST,
//                Constants.URL_LOGIN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            if(!obj.getBoolean("error")){
//                                SharedPrefManager.getInstance(getApplicationContext())
//                                        .userLogin(
//                                          obj.getInt("id"),
//                                          obj.getString("username"),
//                                          obj.getString("email")
//                                        );
//                                startActivity(new Intent(getApplicationContext(),Home.class));
//                                finish();
//                            }else{
//                                Toast.makeText(
//                                        getApplicationContext(),
//                                        obj.getString("message"),
//                                        Toast.LENGTH_LONG
//                                ).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Toast.makeText(
//                                getApplicationContext(),
//                                error.getMessage() ,
//                                Toast.LENGTH_LONG
//                        ).show();
//
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("username", username);
//                params.put("password", password);
//                return params;
//            }
//        };
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest );
    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogin){
            userLogin();
        }

    }

    private void loadDataM(){
        SharedPreferences sharedPreferences = getSharedPreferences("Accounts", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedPreferences.getString("task D",null);
        Type type = new TypeToken<Map<String, ArrayList<Users> >>() {}.getType();
        accounts = gson.fromJson(json, type);
        if(accounts == null){
            accounts = new HashMap<>();
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
}
