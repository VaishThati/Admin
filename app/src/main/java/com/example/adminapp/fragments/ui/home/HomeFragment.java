package com.example.adminapp.fragments.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.adminapp.R;
import com.example.adminapp.vendor.AddVendor;
import com.example.adminapp.viewProduct.ViewProduct;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    String getImagesURL = "http://103.150.187.59:54691/api/Product/getAllProducts";
    RecyclerView list1;
   // SwipeRefreshLayout swipeRefreshLayout;
    CardView vendor, product;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        findComponents(root);
        //return inflater.inflate(R.layout.fragment_home, null);
        return root;
    }


    public void findComponents(View view){
        vendor = view.findViewById(R.id.vendorCard);
        product = view.findViewById(R.id.productCard);
        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddVendor.class);
                startActivity(i);
            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getContext(), AddImages.class);
//                Intent i = new Intent(getContext(), NewAddImage.class);
//                Intent i = new Intent(getContext(), AddProduct.class);
                Intent i = new Intent(getContext(), ViewProduct.class);
                startActivity(i);
            }
        });
//        list1 = view.findViewById(R.id.imageRecycleView);
//        list1.setLayoutManager(new LinearLayoutManager(getContext()));
//        swipeRefreshLayout = view.findViewById(R.id.ImageSwipeRefresh);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                getImage();
//            }
//        });
       // getImage();
    }


    /*
    private void getImage(){
        Log.e("notificationurl", getImagesURL);
        StringRequest request = new StringRequest(getImagesURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("VendorResponse", response);
//                GsonBuilder gsonBuilder = new GsonBuilder();
//                Gson gson = gsonBuilder.create();
//                ImageGetSet[] users = gson.fromJson(response, ImageGetSet[].class);
//                list1.setAdapter(new ImageAdapter(getContext(),users));
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
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
    */
}