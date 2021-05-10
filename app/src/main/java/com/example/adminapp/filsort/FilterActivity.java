package com.example.adminapp.filsort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
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
import com.example.adminapp.productgetset.Example;
import com.example.adminapp.viewProduct.NewPrdAdp;
import com.example.adminapp.viewProduct.ViewProduct;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    RecyclerView filterRV;
    RecyclerView filterValuesRV;
    FilterAdapter filterAdapter;
    String getImagesURL = "http://103.150.187.59:54691/api/Product/getAllProducts/";
    List<String> colorsList = new ArrayList<String>();
    List<String> sizeList = new ArrayList<String>();
    List<String> priceList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getImage();
        initControls();
    }

    private void initControls() {
        filterRV = findViewById(R.id.filterRV);
        filterValuesRV = findViewById(R.id.filterValuesRV);
        filterRV.setLayoutManager(new LinearLayoutManager(this));
        filterValuesRV.setLayoutManager(new LinearLayoutManager(this));

        List<String> colors = Arrays.asList(new String[]{"Red", "Green", "Blue", "White"});
        if (!Preferences.filters.containsKey(Filter.INDEX_COLOR)) {
            Preferences.filters.put(Filter.INDEX_COLOR, new Filter("Color", colorsList, new ArrayList()));
        }
        List<String> sizes = Arrays.asList(new String[]{"10", "12", "14", "16", "18", "20"});
        if (!Preferences.filters.containsKey(Filter.INDEX_SIZE)) {
            Preferences.filters.put(Filter.INDEX_SIZE, new Filter("Size", sizeList, new ArrayList()));
        }
        List<String> prices = Arrays.asList(new String[]{"0-100", "101-200", "201-300"});
        if (!Preferences.filters.containsKey(Filter.INDEX_PRICE)) {
            Preferences.filters.put(Filter.INDEX_PRICE, new Filter("Price", priceList, new ArrayList()));
        }

        filterAdapter = new FilterAdapter(getApplicationContext(), Preferences.filters, filterValuesRV);
        filterRV.setAdapter(filterAdapter);

        Button clearB = findViewById(R.id.clearB);
        clearB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.filters.get(Filter.INDEX_COLOR).setSelected(new ArrayList());
                Preferences.filters.get(Filter.INDEX_SIZE).setSelected(new ArrayList());
                Preferences.filters.get(Filter.INDEX_PRICE).setSelected(new ArrayList());
                startActivity(new Intent(FilterActivity.this, ViewProduct.class));
            }
        });

        Button applyB = findViewById(R.id.applyB);
        applyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle b=new Bundle();
                startActivity(new Intent(FilterActivity.this, ViewProduct.class));
//                b.putStringArrayList("colorsList", (ArrayList<String>) colorsList);
//                intent.putExtras(b);
            }
        });
    }

    private void getImage(){
        Log.e("getproductlisturl", getImagesURL);
        StringRequest request = new StringRequest(getImagesURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("productlistresp", response);
                try {
                    JSONArray abc = new JSONArray(response);
                    Log.e("abc", String.valueOf(abc));
                    for (int i=0;i<abc.length();i++){
                        JSONObject jsonObject = abc.getJSONObject(i);
                        String desc = jsonObject.getString("description");
                        String custPrice = jsonObject.getString("customerPrice");
                        priceList.add(custPrice);
                        String pdr = jsonObject.getString("prdDesignRes");
                        Log.e("pdr", pdr);
                        JSONArray pa = new JSONArray(pdr);
                        for (int k=0;k<pa.length();k++){
                            JSONObject j = pa.getJSONObject(k);
                            String pc = j.getString("productColor");
                            Log.e("pc", pc);
                            JSONObject pcj = new JSONObject(pc);
                            String pcv = pcj.getString("productColorValue");
                            Log.e("pcv", pcv);
                            colorsList.add(pcv);
                            String ps = j.getString("productSize");
                            Log.e("ps", ps);
                            JSONObject psj = new JSONObject(ps);
                            String psv = psj.getString("value");
                            sizeList.add(psv);
                            Log.e("psv", psv);
                        }
                        Log.e("colorsList", colorsList.toString());
                        Log.e("sizeList", sizeList.toString());
                        Log.e("priceList", priceList.toString());
                    }
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
                Toast.makeText(FilterActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(FilterActivity.this);
        queue.add(request);
    }
}
