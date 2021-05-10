package com.example.adminapp.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateVendor extends AppCompatActivity {

    EditText name, email, phone, website, store, gst, supplier_code;
     int vendorId;
    String updateVurl = "http://103.150.187.59:54691/Vendor/Update";
    Button update;
    String uvurl;
    String vname, vemail, vstore, vsite, vgst, vsc;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vendor);
        pd = new ProgressDialog(this);
        name = findViewById(R.id.nameu);
        email = findViewById(R.id.emailu);
          //  phone = itemView.findViewById(R.id.mobileu);
        website = findViewById(R.id.websiteu);
        store = findViewById(R.id.storeu);
        gst = findViewById(R.id.gstinu);
        supplier_code = findViewById(R.id.suppliercodeu);

        Intent mIntent = getIntent();
        vendorId = Integer.parseInt(mIntent.getStringExtra("vendorId"));
        vname = mIntent.getStringExtra("vendorName");
        vemail = mIntent.getStringExtra("vendorEmail");
        vstore = mIntent.getStringExtra("vendorStore");
        vsite = mIntent.getStringExtra("vendorSite");
        vgst = mIntent.getStringExtra("vendorgst");
        vsc = mIntent.getStringExtra("vendorsc");

        name.setText(vname);
        email.setText(vemail);
        email.setEnabled(false);
        store.setText(vstore);
        website.setText(vsite);
        gst.setText(vgst);
        supplier_code.setText(vsc);

        uvurl = "http://103.150.187.59:54691/Vendor/Update?id=" + vendorId;
       // updateVendor(uvurl);
        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                pd.setMessage("Please wait while the details are updated!");
                updateVendor(uvurl);
                //updateVendorString(uvurl);
            }
        });

    }

    private void updateVendor(String url) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("id", String.valueOf(vendorId));
        headers.put("vendor_name", name.getText().toString());
        //headers.put("email", email.getText().toString());
        headers.put("website", website.getText().toString());
        headers.put("gstin", gst.getText().toString());
        headers.put("is_deleted", "false");
        headers.put("store_name", store.getText().toString());
        headers.put("supplier_code", supplier_code.getText().toString());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(url,new JSONObject(headers) , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("vendorresp", String.valueOf(response));
                pd.dismiss();
                Toast.makeText(UpdateVendor.this, "Vendor is Updated", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Log.e("mesg", String.valueOf(error));
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
//                    message = "Parsing error! Please try again after some time!!";
                    message = "Vendor is Updated";
                    Log.e("asbd,kan;sad", String.valueOf(error));
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(UpdateVendor.this, message, Toast.LENGTH_SHORT).show();
                Log.e("error", String.valueOf(error));
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

        requestQueue.add(stringRequest);
    }


    //don't use
    private void updateVendorString(String url) {
//        HashMap<String, String> headers = new HashMap<>();
//        headers.put("id", String.valueOf(vendorId));
//        headers.put("vendor_name", name.getText().toString());
//        //headers.put("email", email.getText().toString());
//        headers.put("website", website.getText().toString());
//        headers.put("gstin", gst.getText().toString());
//        headers.put("is_deleted", "false");
//        headers.put("store_name", store.getText().toString());
//        headers.put("supplier_code", supplier_code.getText().toString());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("vendorresp", String.valueOf(response));
                pd.dismiss();
                Toast.makeText(UpdateVendor.this, "Vendor Updated", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
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
                    Log.e("asbd,kan;sad", String.valueOf(error));
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(UpdateVendor.this, message, Toast.LENGTH_SHORT).show();
                Log.e("error", String.valueOf(error));
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

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("id", String.valueOf(vendorId));
                headers.put("vendor_name", name.getText().toString());
                //headers.put("email", email.getText().toString());
                headers.put("website", website.getText().toString());
                headers.put("gstin", gst.getText().toString());
                headers.put("is_deleted", "false");
                headers.put("store_name", store.getText().toString());
                headers.put("supplier_code", supplier_code.getText().toString());
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }
}