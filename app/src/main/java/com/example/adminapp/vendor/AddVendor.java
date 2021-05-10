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


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddVendor extends AppCompatActivity {

    EditText name, email, phone, website, store, gst, supplier_code;
    Button registerVendor, viewVendor;
    String nameText, emailText, phoneText, websiteText, storeText, gstText, suppliercodeText;
    private RequestQueue requestQueue;
    public String mResponse;

    ProgressDialog pd;
    String vendorUrl = "http://103.150.187.59:54691/Vendor/Add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vendor);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.mobile);
        website = findViewById(R.id.website);
        store = findViewById(R.id.store);
        gst = findViewById(R.id.gstin);
        supplier_code = findViewById(R.id.suppliercode);
        registerVendor = findViewById(R.id.buttonRegister);
        viewVendor = findViewById(R.id.buttonViewVendor);

        registerVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerVendor();
            }
        });

        viewVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewVendor();
            }
        });
    }

    public void registerVendor() {
        nameText = name.getText().toString();
        emailText = email.getText().toString();
        phoneText = phone.getText().toString();
        websiteText = website.getText().toString();
        storeText = store.getText().toString();
        gstText = gst.getText().toString();
        suppliercodeText = supplier_code.getText().toString();

        if (emailText.isEmpty() || phoneText.isEmpty() || nameText.isEmpty() || websiteText.isEmpty() || storeText.isEmpty()
        || gstText.isEmpty()) {
            Toast.makeText(this, "Enter All Details", Toast.LENGTH_SHORT).show();
        } else {
            pd = new ProgressDialog(this);
            pd.setMessage("Loading...");
            pd.show();
            loginUser();
//            String data =  "{"+
//                    "\"vendor_name\"" + "\"" + name.getText().toString() + "\","+
//                    "\"email\"" + "\"" + email.getText().toString() + "\""+
//                    "\"website\"" + "\"" + websiteText + "\","+
//                    "\"gstin\"" + "\"" + gstText + "\","+
//                    "\"is_deleted\"" + "\"" + "1" + "\","+
//                    "\"store_name\"" + "\"" + storeText + "\","+
//                    "\"supplier_code\"" + "\"" + suppliercodeText + "\","+
//                    "}";
//            Submit(data);
        }
    }

    public void viewVendor() {
        Intent i = new Intent(AddVendor.this, ViewVendor.class);
        startActivity(i);
        finish();
    }

    private void loginUser() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("vendor_name", name.getText().toString());
        headers.put("email", email.getText().toString());
        headers.put("website", websiteText);
        headers.put("gstin", gstText);
        headers.put("is_deleted", "false");
        headers.put("store_name", storeText);
        headers.put("supplier_code", suppliercodeText);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest stringRequest = new JsonObjectRequest(vendorUrl,new JSONObject(headers) , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("vendorresp", String.valueOf(response));
                pd.dismiss();
                Toast.makeText(AddVendor.this, "Vendor Addded", Toast.LENGTH_SHORT).show();
                name.setText("");
                email.setText("");
                website.setText("");
                gst.setText("");
                phone.setText("");
                store.setText("");
                supplier_code.setText("");

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
                    pd.dismiss();
                    // Toast.makeText(VendorActivity.this, "Vendor Addded", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    phone.setText("");
                    email.setText("");
                    website.setText("");
                    gst.setText("");
                    store.setText("");
                    supplier_code.setText("");
//                    message = "Parsing error! Please try again after some time!!";
                    message = "Vendor Addded";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(AddVendor.this, message, Toast.LENGTH_SHORT).show();
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
}