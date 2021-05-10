package com.example.adminapp.viewProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.adminapp.addproduct.AddProduct;
import com.example.adminapp.addproduct.AndroidVersion;
import com.example.adminapp.filsort.Filter;
import com.example.adminapp.filsort.FilterActivity;
import com.example.adminapp.filsort.Preferences;
import com.example.adminapp.multipleaddproduct.NewImageGrid;
import com.example.adminapp.productgetset.Example;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewProduct extends AppCompatActivity {

    String getImagesURL = "http://103.150.187.59:54691/api/Product/getAllProducts/";
    RecyclerView list1;
    RadioGroup sortRG;
    FloatingActionButton fab;
    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };

    private final String android_image_urls[] = {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
            "http://api.learn2crack.com/android/images/marshmallow.png"
    };

    List<Example> items = new ArrayList<Example>();
    LinearLayout sortContainerBackLL, sortContainerLL;
    List<String> colorsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

//        urls = getIntent().getStringArrayListExtra("colorsList");
//        Log.e("urlss", String.valueOf(urls));

        list1 = (RecyclerView)findViewById(R.id.productRV);
        list1.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        list1.setLayoutManager(layoutManager);

        Example androidVersions = new Example();
//        androidVersions.setCategory(new Category(androidVersions.getCategory().getId(), androidVersions.getCategory().getCategoryValue()));
        NewPrdAdp adapter = new NewPrdAdp(getApplicationContext(), new Example[]{androidVersions});
        list1.setAdapter(adapter);

        getImage();

        fab = findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //working
                Intent i = new Intent(ViewProduct.this, AddProduct.class);
               // Intent i = new Intent(ViewProduct.this, NewImageGrid.class);
                startActivity(i);
                finish();
            }
        });

        if (!Preferences.filters.isEmpty()) {
            ArrayList<Example> filteredItems = new ArrayList<Example>();
            List<String> colors = Preferences.filters.get(Filter.INDEX_COLOR).getSelected();
            List<String> sizes = Preferences.filters.get(Filter.INDEX_SIZE).getSelected();
            List<String> prices = Preferences.filters.get(Filter.INDEX_PRICE).getSelected();
            for (Example item : items) {
                boolean colorMatched = true;
                if (colors.size() > 0 && !colors.contains(item.getPrdDesignRes().get(0).getProductColor())) {
                    colorMatched = false;
                }
                boolean sizeMatched = true;
                if (sizes.size() > 0 && !sizes.contains(item.getPrdDesignRes().get(0).getProductSize())) {
                    sizeMatched = false;
                }
                boolean priceMatched = true;
                if (prices.size() > 0 && !priceContains(prices, Double.valueOf(item.getCustomerPrice()))) {
                    priceMatched = false;
                }
                if (colorMatched && sizeMatched && priceMatched) {
                    filteredItems.add(item);
                }
            }
            items = filteredItems;
        }

        sortContainerBackLL = findViewById(R.id.sortContainerBackLL);
        sortContainerLL = findViewById(R.id.sortContainerLL);
        //sortContainerLL.setTranslationY(-sortContainerLL.getHeight());
        sortContainerBackLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortContainerLL.animate()
                        .translationY(-sortContainerLL.getHeight())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                sortContainerLL.animate().setListener(null);
                                sortContainerBackLL.setVisibility(View.GONE);
                            }
                        });
            }
        });

        Button sortB = findViewById(R.id.sortB);
        sortB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortContainerBackLL.setVisibility(View.VISIBLE);
                sortContainerLL.animate().translationY(0);
            }
        });

        Button filterB = findViewById(R.id.filterB);
        filterB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle b=new Bundle();
                Intent intent = new Intent(ViewProduct.this, FilterActivity.class);
//                b.putStringArrayList("colorsList", (ArrayList<String>) colorsList);
//                intent.putExtras(b);
                //intent.putStringArrayListExtra("", (ArrayList<String>) colorsList);
                startActivity(intent);

            }
        });

        sortRG = findViewById(R.id.sortRG);
//        sortRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.sortByName:
//                        Example[] itemsArr1 = new Example[items.size()];
//                        itemsArr1 = items.toArray(itemsArr1);
//                        Arrays.sort(itemsArr1, Example.nameComparator);
//                        items = Arrays.asList(itemsArr1);
////                        mAdapter = new ItemAdapter(getApplicationContext(), items);
////                        mRecyclerView.setAdapter(mAdapter);
//                        Example[] users = new Example[items.size()];
//                       // list1.setAdapter(new NewPrdAdp(ViewProduct.this, users));
//                        break;
//                    case R.id.sortBySize:
//                        Example[] itemsArr2 = new Example[items.size()];
//                        itemsArr2 = items.toArray(itemsArr2);
//                        Arrays.sort(itemsArr2, Example.sizeComparator);
//                        items = Arrays.asList(itemsArr2);
////                        mAdapter = new ItemAdapter(getApplicationContext(), items);
////                        mRecyclerView.setAdapter(mAdapter);
//                        Example[] users1 = new Example[items.size()];
//                     //   list1.setAdapter(new NewPrdAdp(ViewProduct.this, users1));
//                        break;
//                    case R.id.sortByPrice:
//                        Example[] itemsArr3 = new Example[items.size()];
//                        itemsArr3 = items.toArray(itemsArr3);
//                        Arrays.sort(itemsArr3, Example.priceComparator);
//                        items = Arrays.asList(itemsArr3);
////                        mAdapter = new ItemAdapter(getApplicationContext(), items);
////                        mRecyclerView.setAdapter(mAdapter);
//                        Example[] users2 = new Example[items.size()];
//                    //    list1.setAdapter(new NewPrdAdp(ViewProduct.this, users2));
//                        break;
//
//                }
//                sortContainerLL.animate()
//                        .translationY(-sortContainerLL.getHeight())
//                        .setListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                sortContainerLL.animate().setListener(null);
//                                sortContainerBackLL.setVisibility(View.GONE);
//                            }
//                        });
//            }
//        });
    }

    private boolean priceContains(List<String> prices, Double price) {
        boolean flag = false;
        for (String p : prices) {
            String tmpPrices[] = p.split("-");
            if (price >= Double.valueOf(tmpPrices[0]) && price <= Double.valueOf(tmpPrices[1])) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.productRV);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<AndroidVersion> androidVersions = prepareData();
        ProductAdapter adapter = new ProductAdapter(getApplicationContext(),androidVersions);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<AndroidVersion> prepareData(){

        ArrayList<AndroidVersion> android_version = new ArrayList<>();
        for(int i=0;i<android_version_names.length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(android_version_names[i]);
            androidVersion.setAndroid_image_url(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
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
                            Log.e("psv", psv);
//                            items.add(new Example(desc, pcv, Integer.valueOf(psv), Double.valueOf(custPrice)));
//                            for (int l=0;l<items.size();l++) {
//                                Log.e("itemsss", items.get(l).getVendor().vendorName.toString());
////                                items.add(new Item(items.get(l).getName(), items.get(l).getColor(), items.get(l).getSize(),
////                                        items.get(l).getPrice()));
//                            }
                        }
                        Log.e("colorsList", colorsList.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Example[] users = gson.fromJson(response, Example[].class);
                list1.setAdapter(new NewPrdAdp(ViewProduct.this, users));

                sortRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.sortByName:
                                Example[] itemsArr1 = new Example[users.length];
                                itemsArr1 = users;
                                Arrays.sort(itemsArr1, Example.nameComparator);
                                items = Arrays.asList(itemsArr1);
//                        mAdapter = new ItemAdapter(getApplicationContext(), items);
//                        mRecyclerView.setAdapter(mAdapter);
                                //Example[] users = gson.fromJson(response, Example[].class);
                                list1.setAdapter(new NewPrdAdp(ViewProduct.this, users));
                                break;
                            case R.id.sortBySize:
//                                Example[] itemsArr2 = new Example[items.size()];
//                                itemsArr2 = items.toArray(itemsArr2);
                                Example[] itemsArr2 = new Example[users.length];
                                itemsArr2 = users;
                                Arrays.sort(itemsArr2, Example.sizeComparator);
                                items = Arrays.asList(itemsArr2);
//                        mAdapter = new ItemAdapter(getApplicationContext(), items);
//                        mRecyclerView.setAdapter(mAdapter);
//                                Example[] users1 = new Example[items.size()];
//                                //   list1.setAdapter(new NewPrdAdp(ViewProduct.this, users1));
                               // Example[] users1 = gson.fromJson(response, Example[].class);
                                list1.setAdapter(new NewPrdAdp(ViewProduct.this, users));
                                break;
                            case R.id.sortByPrice:
//                                Example[] itemsArr3 = new Example[items.size()];
//                                itemsArr3 = items.toArray(itemsArr3);
                                Example[] itemsArr3 = new Example[users.length];
                                itemsArr3 = users;
                                Arrays.sort(itemsArr3, Example.priceComparator);
                                items = Arrays.asList(itemsArr3);
//                        mAdapter = new ItemAdapter(getApplicationContext(), items);
//                        mRecyclerView.setAdapter(mAdapter);
//                                Example[] users2 = new Example[items.size()];
//                                //    list1.setAdapter(new NewPrdAdp(ViewProduct.this, users2));
                               // Example[] users2 = gson.fromJson(response, Example[].class);
                                list1.setAdapter(new NewPrdAdp(ViewProduct.this, users));
                                break;

                        }
                        sortContainerLL.animate()
                                .translationY(-sortContainerLL.getHeight())
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        sortContainerLL.animate().setListener(null);
                                        sortContainerBackLL.setVisibility(View.GONE);
                                    }
                                });
                    }
                });
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
                Toast.makeText(ViewProduct.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(ViewProduct.this);
        queue.add(request);
    }
}