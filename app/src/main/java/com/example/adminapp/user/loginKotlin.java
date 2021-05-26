package com.example.adminapp.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminapp.MainActivity;
import com.example.adminapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class loginKotlin extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "userlogindetails";
    EditText email, password;
    Button login;
    TextView forgotPassword, newUser;
    String loginUrl = "http://103.150.187.59:54691/api/Users/authenticate";
    String mResponse;
    int vendorid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //final SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
        //working
        SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
        if (settings.getString("logged", "").toString().equals("logged")){
            Intent intent = new Intent(loginKotlin.this, MainActivity.class);
            startActivity(intent);
        }
//        if (settings.toString().equals("logged")){
//            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(i);
//            finish();
//        }

        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.cirLoginButton);
        forgotPassword = findViewById(R.id.forgotPasswordText);
        newUser = findViewById(R.id.newUserText);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("") || email.getText().toString().equalsIgnoreCase(null)){
                    Toast.makeText(loginKotlin.this, "Please enter your Username", Toast.LENGTH_SHORT).show();
                    email.setError("Enter Username");
                    email.requestFocus();
                    return;
                }
                if (password.getText().toString().equals("")){
                    Toast.makeText(loginKotlin.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    password.setError("Enter Password");
                    password.requestFocus();
                    return;
                }
                else {
                    loginUser();
                }
            }
        });

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        mResponse = m.getString("Response", "");
        //  Log.e("mResponseDash", mResponse);

    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    //imp(used to store user id which is used later in various activities)
    private void sharedResponse(int response) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response", String.valueOf(response));
        editor.commit();
    }

    private void loginUser() {
        HashMap<String, String> param = new HashMap<>();
        param.put("password", password.getText().toString());
        param.put("UserName", email.getText().toString());
        // String URL = getString(R.string.APIPath) + "Goal/AddGoal";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest stringRequest = new JsonObjectRequest(loginUrl,new JSONObject(param) , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("login", String.valueOf(response));
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    vendorid = jsonObject.getInt("id");
                    Log.e("vendoridresp", String.valueOf(vendorid));
                    sharedResponse(vendorid);
                    //working code
                    SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("logged", "logged").apply();
                    editor.commit();
                    Intent intent = new Intent(loginKotlin.this, MainActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Log.e("mesg", String.valueOf(error));
                } else if (error instanceof ServerError) {
//                    message = "The server could not be found. Please try again after some time!!";
                    Log.e("servererror", String.valueOf(error));
                    String body;
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            Log.e("body", body);
                            String invalid = "{\"message\":\"Username or password is incorrect\"}";
                            if (body.equals(invalid)){
                                message = "Invalid Username or Password";
                            }
                            else {
                                message = "The server could not be found. Please try again after some time!!";
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                else if (error.networkResponse.data.equals("Username or password is incorrect")){
                    message = "Invalid username";
                }
                Toast.makeText(loginKotlin.this, message, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        //requestQueue.add(stringRequest);
        int socketTimeout = 1000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}