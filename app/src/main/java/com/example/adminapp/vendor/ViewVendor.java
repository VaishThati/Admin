package com.example.adminapp.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ViewVendor extends AppCompatActivity {

    String viewVendorUrl = "http://103.150.187.59:54691/Vendor/List";
    RecyclerView list1;
    TextView novendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vendor);

        list1 = findViewById(R.id.vendorRV);
        list1.setLayoutManager(new LinearLayoutManager(this));
        novendor = findViewById(R.id.noVendor);
        //novendor.setVisibility(View.INVISIBLE);
        findComponents();
    }
    private void findComponents(){
        // String URL = "http://192.168.0.106:8080/api/employees";
        String URL = "http://103.150.187.59:54691/Vendor/List";
        Log.e("url", URL);
        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("VendorResponse", response);
                if (response.equals("[]")){
                    novendor.setText("No Vendors added yet!");
                }
                else {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    VendorGetSet[] users = gson.fromJson(response, VendorGetSet[].class);
                    VendorAdapter adapter = new VendorAdapter(ViewVendor.this, users);
                    //list1.setAdapter(new VendorAdapter(ViewVendor.this,users));
                    list1.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Log.e("error1", String.valueOf(error));
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    Log.e("error2", String.valueOf(error));
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }

                Toast.makeText(ViewVendor.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}