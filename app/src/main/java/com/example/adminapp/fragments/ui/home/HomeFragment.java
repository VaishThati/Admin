package com.example.adminapp.fragments.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.adminapp.R;
import com.example.adminapp.vendor.AddVendor;
import com.example.adminapp.viewProduct.ViewProduct;


public class HomeFragment extends Fragment {

    CardView vendor, product;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        findComponents(root);
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
    }

}