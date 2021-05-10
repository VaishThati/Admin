package com.example.adminapp.multipleaddproduct;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminapp.R;
import com.example.adminapp.addproduct.AddProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewAddProductDemo extends AppCompatActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks{

    ApiService apiService;
    Uri picUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    FloatingActionButton fabCamera, fabUpload;
    Bitmap mBitmap;
    TextView textView;
    byte[] byteArray;
    FrameLayout frameLayout;

    EditText description, csc, vsc, tmr, cmr, cp, traderpr, custpr;
    Button calculate, addProduct;

    String getExistingdataUrl = "http://103.150.187.59:54691/api/Product/existingData";
    String getVendorList = "http://103.150.187.59:54691/Vendor/List";
    String calculateSc = "http://103.150.187.59:54691/api/Product/CalculateSellingCost";
    String addProductHeader = "http://103.150.187.59:54691/api/Product/AddProductHeader";

    String Category, category_Value, stitchtype, stitchValue, fabric, fabricValue, vendor, vendorvalue;
    int id, stitchid, fabricID, vendorid;
    ArrayList<String> ConfessName, stitchName, fabricName, vendorName;
    ArrayList<Integer> ConfessIDArray, stitchArray, fabricArray, vendorArray;
    Spinner catSpinner, stitchSpinner, fabricSpinner, vendorSpinner;
    List<Integer> list2 = new ArrayList<Integer>();
    List<Integer> list3 = new ArrayList<Integer>();
    List<Integer> list4 = new ArrayList<Integer>();
    List<Integer> list5 = new ArrayList<Integer>();
    public int confessintId, stitchintId, fabricintId, vendorintId;
    ProgressDialog pd;
    int customerPrice, traderPrice, productidsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

//        fabCamera = findViewById(R.id.fab);
//        fabUpload = findViewById(R.id.fabUpload);
//        textView = findViewById(R.id.textView);
//        frameLayout = findViewById(R.id.frameLayout);
//
//
//        fabCamera.setOnClickListener(this);
//        fabUpload.setOnClickListener(this);
//
//
//        askPermissions();


        ConfessName = new ArrayList<String>();
        ConfessIDArray = new ArrayList<Integer>();

        stitchName = new ArrayList<String>();
        stitchArray = new ArrayList<Integer>();

        fabricName = new ArrayList<String>();
        fabricArray = new ArrayList<Integer>();

        vendorName = new ArrayList<String>();
        vendorArray = new ArrayList<Integer>();

        description = findViewById(R.id.editTextDesc);
        csc = findViewById(R.id.editTextcsc);
        vsc = findViewById(R.id.editTextvsc);
        tmr = findViewById(R.id.editTexttmr);
        cmr = findViewById(R.id.editTextcmr);
        cp = findViewById(R.id.editTextcp);
        traderpr = findViewById(R.id.etTraderPr);
        custpr = findViewById(R.id.etCustPr);
        custpr.setEnabled(false);
        traderpr.setEnabled(false);
        catSpinner = findViewById(R.id.category_spinner);
        stitchSpinner = findViewById(R.id.stitch_spinner);
        fabricSpinner = findViewById(R.id.fabric_spinner);
        vendorSpinner = findViewById(R.id.vendor_spinner);

        getCategoryList(getExistingdataUrl);
        getVendorList(getVendorList);

        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int x = catSpinner.getSelectedItemPosition();
                confessintId = list2.get(x);
                Log.e("confessintID", String.valueOf(confessintId));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        stitchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int x = stitchSpinner.getSelectedItemPosition();
                stitchintId = list3.get(x);
                Log.e("stitchintId", String.valueOf(stitchintId));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fabricSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int x = fabricSpinner.getSelectedItemPosition();
                fabricintId = list4.get(x);
                Log.e("fabricintId", String.valueOf(fabricintId));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vendorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int x = vendorSpinner.getSelectedItemPosition();
                vendorintId = list5.get(x);
                Log.e("vendorintId", String.valueOf(vendorintId));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (csc.getText().toString().isEmpty() || vsc.getText().toString().isEmpty() || tmr.getText().toString().isEmpty() ||
                        cmr.getText().toString().isEmpty() || cp.getText().toString().isEmpty()){
                    Toast.makeText(NewAddProductDemo.this, "Please enter all the required fields!", Toast.LENGTH_LONG).show();
                }
                else {
                    pd = new ProgressDialog(NewAddProductDemo.this);
                    pd.setMessage("Loading...");
                    pd.show();
                    calculateSP();
                    custpr.setEnabled(true);
                    traderpr.setEnabled(true);
                }
            }
        });

        addProduct = findViewById(R.id.addPrBtn);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.getText().toString().isEmpty()){
                    Toast.makeText(NewAddProductDemo.this, "Enter the description", Toast.LENGTH_SHORT).show();
                    description.requestFocus();
                }
                else {
                    //addProduct();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onProgressUpdate(int percentage) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void uploadStart() {

    }


    private void getCategoryList(String confessionList_url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(confessionList_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String responseOrg) {
                Log.e("respn", responseOrg);
                try{
                    JSONObject jsonObject = new JSONObject(responseOrg);
                    Category = jsonObject.getString("listOfCategories");
                    Log.e("category", Category);

                    stitchtype = jsonObject.getString("productStichingTypes");
                    Log.e("productStichingTypes", stitchtype);

                    fabric = jsonObject.getString("prodctFabrics");
                    Log.e("prodctFabrics", fabric);

                    JSONArray jsonArray = new JSONArray(Category);
                    Log.e("jsonaarya", String.valueOf(jsonArray));
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject abc = jsonArray.getJSONObject(i);
                        category_Value = abc.getString("category_value");
                        Log.e("category_Value", category_Value);
                        id = abc.getInt("id");
                        Log.e("id", String.valueOf(id));
                        ConfessName.add(category_Value);
                        ConfessIDArray.add(id);
                        list2.addAll(Collections.singleton(abc.getInt("id")));
                    }

                    JSONArray jsonArray1 = new JSONArray(stitchtype);
                    Log.e("jsonaarya1", String.valueOf(jsonArray1));
                    for (int i = 0;i<jsonArray1.length();i++){
                        JSONObject abc1 = jsonArray1.getJSONObject(i);
                        stitchValue = abc1.getString("stiching_value");
                        Log.e("stiching_value", stitchValue);
                        stitchid = abc1.getInt("id");
                        Log.e("id", String.valueOf(stitchid));
                        stitchName.add(stitchValue);
                        stitchArray.add(stitchid);
                        list3.addAll(Collections.singleton(abc1.getInt("id")));
                    }

                    JSONArray jsonArray3 = new JSONArray(fabric);
                    Log.e("jsonaarya3", String.valueOf(jsonArray3));
                    for (int i = 0;i<jsonArray3.length();i++){
                        JSONObject abc1 = jsonArray3.getJSONObject(i);
                        fabricValue = abc1.getString("value");
                        Log.e("value", fabricValue);
                        fabricID = abc1.getInt("id");
                        Log.e("id", String.valueOf(fabricID));
                        fabricName.add(fabricValue);
                        fabricArray.add(fabricID);
                        list4.addAll(Collections.singleton(abc1.getInt("id")));
                    }
                    catSpinner.setAdapter(new ArrayAdapter<String>(NewAddProductDemo.this, android.R.layout.simple_spinner_dropdown_item, ConfessName));
                    stitchSpinner.setAdapter(new ArrayAdapter<String>(NewAddProductDemo.this, android.R.layout.simple_spinner_dropdown_item, stitchName));
                    fabricSpinner.setAdapter(new ArrayAdapter<String>(NewAddProductDemo.this, android.R.layout.simple_spinner_dropdown_item, fabricName));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
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
                Toast.makeText(NewAddProductDemo.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 1000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


    private void getVendorList(String confessionList_url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(confessionList_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String responseOrg) {
                Log.e("respn", responseOrg);
                try{
                    JSONArray jsonArray = new JSONArray(responseOrg);
                    Log.e("vendorarray", String.valueOf(jsonArray));
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject abc = jsonArray.getJSONObject(i);
                        vendorvalue = abc.getString("vendor_name");
                        Log.e("vendor_name", vendorvalue);
                        id = abc.getInt("id");
                        Log.e("id", String.valueOf(id));
                        vendorName.add(vendorvalue);
                        vendorArray.add(id);
                        list5.addAll(Collections.singleton(abc.getInt("id")));
                    }
                    vendorSpinner.setAdapter(new ArrayAdapter<String>(NewAddProductDemo.this, android.R.layout.simple_spinner_dropdown_item, vendorName));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
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
                Toast.makeText(NewAddProductDemo.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 1000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void calculateSP() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("costprice", cp.getText().toString());
        headers.put("prdStichingType", String.valueOf(stitchintId));
        headers.put("customerShippingCost", csc.getText().toString());
        headers.put("vendorShippingCost", vsc.getText().toString());
        headers.put("traderMarginRupees", tmr.getText().toString());
        headers.put("customerMarginRupees", cmr.getText().toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest stringRequest = new JsonObjectRequest(calculateSc, new JSONObject(headers) , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("calculate", String.valueOf(response));
                pd.dismiss();
                Toast.makeText(NewAddProductDemo.this, "Selling Price Calculated", Toast.LENGTH_SHORT).show();
//                cp.setText("");
//                csc.setText("");
//                vsc.setText("");
//                tmr.setText("");
//                cmr.setText("");
                try {
                    customerPrice = response.getInt("customerPrice");
                    traderPrice = response.getInt("traderPrice");
                    Log.e("tp", String.valueOf(traderPrice));
                    custpr.setText(String.valueOf(customerPrice));
                    traderpr.setText(String.valueOf(traderPrice));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                    message = "Parsing error! Please try again after some time!!";
                    //          message = "Vendor Addded";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(NewAddProductDemo.this, message, Toast.LENGTH_SHORT).show();
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
