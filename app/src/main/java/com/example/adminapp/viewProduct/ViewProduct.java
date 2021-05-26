package com.example.adminapp.viewProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Size;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.adminapp.MainActivity;
import com.example.adminapp.R;
import com.example.adminapp.addproduct.AddProduct;
import com.example.adminapp.addproduct.AndroidVersion;
import com.example.adminapp.filsort.Filter;
import com.example.adminapp.filsort.FilterActivity;
import com.example.adminapp.filsort.Preferences;
import com.example.adminapp.multipleaddproduct.NewImageGrid;
import com.example.adminapp.productgetset.Example;
import com.example.adminapp.productgetset.PrdDesignRe;
import com.example.adminapp.productgetset.ProductSize;
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
    List<Example> items = new ArrayList<Example>();
    LinearLayout sortContainerBackLL, sortContainerLL;
    List<String> colorsList = new ArrayList<String>();
    Example[] ashutosh;
    private NewPrdAdp adapter;
    private List<Example> exampleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        list1 = (RecyclerView)findViewById(R.id.productRV);
        list1.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        list1.setLayoutManager(layoutManager);

        exampleList = new ArrayList<>();
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

        /*
        if (!Preferences.filters.isEmpty()) {
            ArrayList<Example> filteredItems = new ArrayList<Example>();
            List<String> colors = Preferences.filters.get(Filter.INDEX_COLOR).getSelected();
            List<String> sizes = Preferences.filters.get(Filter.INDEX_SIZE).getSelected();
            List<String> prices = Preferences.filters.get(Filter.INDEX_PRICE).getSelected();
            for (Example item : items) {
                boolean colorMatched = true;
//                if (colors.size() > 0 && !colors.contains(item.getPrdDesignRes().get(0).getProductColor())) {
//                    colorMatched = false;
//                }
                if (colors.size() > 0 && checkforPcolor(item.getPrdDesignRes(), colors)) {
                    colorMatched = true;
                }
                boolean sizeMatched = true;
//                if (sizes.size() > 0 && !sizes.contains(item.getPrdDesignRes().get(0).getProductSize())) {
//                    sizeMatched = false;
//                }
                if (sizes.size() > 0 && checkforPD(item.getPrdDesignRes(), sizes)) {
                    sizeMatched = true;
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
        */

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
                Intent intent = new Intent(ViewProduct.this, FilterActivity.class);
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

    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        //return true;
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                        }
                        Log.e("colorsList", colorsList.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Example[] users = gson.fromJson(response, Example[].class);
                adapter = new NewPrdAdp(ViewProduct.this, users);
                list1.setAdapter(adapter);

                if (!Preferences.filters.isEmpty()) {
                    ArrayList<Example> filteredItems = new ArrayList<Example>();
                    List<String> colors = Preferences.filters.get(Filter.INDEX_COLOR).getSelected();
                    List<String> sizes = Preferences.filters.get(Filter.INDEX_SIZE).getSelected();
                    List<String> prices = Preferences.filters.get(Filter.INDEX_PRICE).getSelected();
                    for (Example item : users) {
                        boolean colorMatched = false;
//                        if (colors.size() > 0 && !colors.contains(item.getPrdDesignRes().get(0).getProductColor())) {
//                            colorMatched = true;
//                        }
                        if (colors.size() > 0 && checkforPcolor(item.getPrdDesignRes(), colors)) {
                            colorMatched = true;
                        }
                        boolean sizeMatched = false;
//                        if (sizes.size() > 0 && sizes.contains(item.getPrdDesignRes().get(0).getProductSize())) {
//                            sizeMatched = true;
//                        }
                        if (sizes.size() > 0 && checkforPD(item.getPrdDesignRes(), sizes)) {
                            sizeMatched = true;
                        }
                        boolean priceMatched = false;
//                        if (prices.size() > 0 && !priceContains(prices, Double.valueOf(item.getCustomerPrice()))) {
//                            priceMatched = true;
//                        }
//                        if (sizes.size() > 0 && checkforPrice(item.getCustomerPrice(), sizes)) {
//                            sizeMatched = true;
//                        }
                        if (colorMatched || sizeMatched || priceMatched) {
                            filteredItems.add(item);
                        }
                    }
                    items = filteredItems;
                    ashutosh = new Example[filteredItems.size()];
                    for (int u=0;u<users.length;u++){
                        users[u] = null;
                    }
                    for (int i=0;i<items.size();i++){
                        ashutosh[i] = (Example) items.get(i);
                    }
                    list1.setAdapter(new NewPrdAdp(ViewProduct.this, ashutosh));
                    sortRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            switch (checkedId) {
                                case R.id.sortByName:
                                    Example[] itemsArr1 = new Example[users.length];
                                    itemsArr1 = ashutosh;
                                    Arrays.sort(itemsArr1, Example.nameComparator);
                                    items = Arrays.asList(itemsArr1);
                                    list1.setAdapter(new NewPrdAdp(ViewProduct.this, ashutosh));
                                    break;
                                case R.id.sortBySize:
                                    Example[] itemsArr2 = new Example[users.length];
                                    itemsArr2 = ashutosh;
                                    Arrays.sort(itemsArr2, Example.sizeComparator);
                                    items = Arrays.asList(itemsArr2);
                                    list1.setAdapter(new NewPrdAdp(ViewProduct.this, ashutosh));
                                    break;
                                case R.id.sortByPrice:
                                    Example[] itemsArr3 = new Example[users.length];
                                    itemsArr3 = ashutosh;
                                    Arrays.sort(itemsArr3, Example.priceComparator);
                                    items = Arrays.asList(itemsArr3);
                                    list1.setAdapter(new NewPrdAdp(ViewProduct.this, ashutosh));
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
                else {
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
                                    list1.setAdapter(new NewPrdAdp(ViewProduct.this, users));
                                    break;
                                case R.id.sortBySize:
                                    Example[] itemsArr2 = new Example[users.length];
                                    itemsArr2 = users;
                                    Arrays.sort(itemsArr2, Example.sizeComparator);
                                    items = Arrays.asList(itemsArr2);
                                    list1.setAdapter(new NewPrdAdp(ViewProduct.this, users));
                                    break;
                                case R.id.sortByPrice:
                                    Example[] itemsArr3 = new Example[users.length];
                                    itemsArr3 = users;
                                    Arrays.sort(itemsArr3, Example.priceComparator);
                                    items = Arrays.asList(itemsArr3);
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


                /*
                sortRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.sortByName:
                                Example[] itemsArr1 = new Example[users.length];
                                itemsArr1 = users;
                                Arrays.sort(itemsArr1, Example.nameComparator);
                                items = Arrays.asList(itemsArr1);
                                list1.setAdapter(new NewPrdAdp(ViewProduct.this, users));
                                break;
                            case R.id.sortBySize:
                                Example[] itemsArr2 = new Example[users.length];
                                itemsArr2 = users;
                                Arrays.sort(itemsArr2, Example.sizeComparator);
                                items = Arrays.asList(itemsArr2);
                                list1.setAdapter(new NewPrdAdp(ViewProduct.this, users));
                                break;
                            case R.id.sortByPrice:
                                Example[] itemsArr3 = new Example[users.length];
                                itemsArr3 = users;
                                Arrays.sort(itemsArr3, Example.priceComparator);
                                items = Arrays.asList(itemsArr3);
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
                */
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

    public boolean checkforPD(List<PrdDesignRe> prdDesignRes, List<String> sizes) {
        //prddesignres?->
        List<String> abc = new ArrayList<>();
        for (PrdDesignRe item : prdDesignRes) {
            abc.add(item.getProductSize().getValue());
        }
        for (String s : sizes) {
            if (abc.contains(s)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public boolean checkforPcolor(List<PrdDesignRe> prdDesignRes, List<String> colors) {
        //prddesignres?->
        List<String> abc = new ArrayList<>();
        for (PrdDesignRe item : prdDesignRes) {
            abc.add(item.getProductColor().getProductColorValue());
        }
        for (String s : colors) {
            if (abc.contains(s)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public boolean checkforPrice(List<Example> example, List<String> prices) {
        //prddesignres?->
        List<String> abc = new ArrayList<>();
        for (Example item : example) {
            abc.add(item.getCustomerPrice().toString());
        }
        for (String s : prices) {
            if (abc.contains(s)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed(){
        Preferences.filters.clear();
        NavUtils.navigateUpFromSameTask(this);
    }


    //imp(used to store user id which is used later in various activities)
    private void sharedResponse(String response) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Arrayofcolor", String.valueOf(response));
        editor.commit();
    }
}