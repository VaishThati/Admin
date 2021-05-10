package com.example.adminapp.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    String registerUrl = "http://103.150.187.59:54691/api/Users/register";
    //    "{
//            ""userName"": ""string"",
//            ""firstName"": ""string"",
//            ""lastName"": ""string"",
//            ""email"": ""user@example.com"",
//            ""password"": ""string"",
//            ""confirmPassword"": ""string""
//}"
    EditText name, email, mobile, password;
    Button register;
    TextView existUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        mobile = findViewById(R.id.editTextMobile);
        password = findViewById(R.id.editTextPassword);
        existUser = findViewById(R.id.existUserText);
        register = findViewById(R.id.cirRegisterButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || name.getText().toString().equalsIgnoreCase(null)){
                    Toast.makeText(RegisterActivity.this, "Please enter your Username", Toast.LENGTH_SHORT).show();
                }
                if (email.getText().toString().equals("") || email.getText().toString().equalsIgnoreCase(null)){
                    Toast.makeText(RegisterActivity.this, "Please enter your Email", Toast.LENGTH_SHORT).show();
                }
                if (mobile.getText().toString().equals("")|| mobile.getText().toString().equalsIgnoreCase(null)){
                    Toast.makeText(RegisterActivity.this, "Please enter your Mobile", Toast.LENGTH_SHORT).show();
                }
                if (password.getText().toString().equals("")|| password.getText().toString().equalsIgnoreCase(null)){
                    Toast.makeText(RegisterActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser();
                }
            }
        });
        existUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void registerUser() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("userName", name.getText().toString());
        headers.put("firstName", name.getText().toString());
        headers.put("lastName", "Last Name");
        headers.put("email", email.getText().toString());
        headers.put("password", password.getText().toString());
        headers.put("confirmPassword", password.getText().toString());
        // String URL = getString(R.string.APIPath) + "Goal/AddGoal";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest stringRequest = new JsonObjectRequest(registerUrl,new JSONObject(headers) , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(RegisterActivity.this, "Registered Successfully! Please Login to continue..", Toast.LENGTH_SHORT).show();
                Log.e("register", String.valueOf(response));
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                //String invalidEmail = "\"Email\":[\n" + "\"The Email field is not a valid e-mail address.\"\n"+ "]";
                String invalidEmail = "\"The Email field is not a valid e-mail address.";
                String invalidPass = "\"The field Password must be a string or array type with a minimum length of '6'.";
                if (error instanceof NetworkError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
                    Log.e("mesg", String.valueOf(error));
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Log.e("servererror", String.valueOf(error));
                    String body;
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            Log.e("body", body);
                            String a = String.valueOf(body.toString().contains(invalidEmail));
                            Log.e("a", a);
                            if (body.contains(invalidEmail)){
                                message = "Please enter a Valid Email Address";
                                email.setError("Enter a valid email");
                                email.requestFocus();
                                return;
                            }
                            if (body.contains(invalidPass)){
                                message = "Password Should be a minimum of 6 Characters!";
                                password.setError("Password should be a minimum of 6 Characters!");
                                password.requestFocus();
                                return;
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
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
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


    /*
    private void registerUser() {
        // String URL = getString(R.string.APIPath) + "Goal/AddGoal";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, registerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("addgoalres", response);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Log.e("mesg", String.valueOf(error));
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("userName", name.getText().toString());
                headers.put("firstName", name.getText().toString());
                headers.put("lastName", "Last Name");
                headers.put("email", email.getText().toString());
                headers.put("password", password.getText().toString());
                headers.put("confirmPassword", password.getText().toString());
                Log.e("headers", String.valueOf(headers));
                return headers;
            }
            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        //requestQueue.add(stringRequest);
        int socketTimeout = 1000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
     */
}