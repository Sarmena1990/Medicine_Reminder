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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    Users users = new Users();
    ArrayList<Users> userList = new ArrayList<>();
    Map<String, ArrayList<Users>> accounts = new HashMap<>();

    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



//        if(SharedPrefManager.getInstance(this).isLoggedIn()){
//            finish();
//            startActivity(new Intent(this, Home.class));
//            return;
//        }
        loadDataM();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewLogin = (TextView) findViewById(R.id.textViewLogin);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);

        textViewLogin.setOnClickListener(this);


    }
    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();


        if(!email.isEmpty() && !username.isEmpty() && !password.isEmpty()) {


            if (!accounts.containsKey(username)) {


                users.setUserName(username);
                users.setPassword(password);
                users.setEmail(email);

                System.out.println("register");
                System.out.println("username" + users.getUserName());
                System.out.println("password" + users.getPassword());
                System.out.println("email" + users.getEmail());

                userList.add(users);

                accounts.put(username, userList);
                saveDataM();
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);

                builder.setMessage("The UserName Already Exist try again")
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
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);

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


//        progressDialog.setMessage("Registering user...");
//        progressDialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                Constants.URL_REGISTER,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
//                        }catch(JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                     @Override
//                    public void onErrorResponse(VolleyError error) {
//                         progressDialog.hide();
//                         Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
//
//                     }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("username", username);
//                params.put("email", email);
//                params.put("password", password);
//                return params;
//            }
//        };
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View view) {

       if(view == buttonRegister)
           registerUser();

       if(view == textViewLogin)
           startActivity(new Intent(this, LoginActivity.class) );


    }

    private void saveDataM(){
        SharedPreferences sharedPreferences = getSharedPreferences("Accounts", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson =new Gson();
        String json = gson.toJson(accounts);
        editor.putString("task D", json);
        editor.apply();
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

}
