package com.example.adminapp.viewProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.adminapp.R;

public class ListViewProduct extends AppCompatActivity {

    RecyclerView recyclerView;
    String getImagesURL = "http://103.150.187.59:54691/api/Product/getAllProducts/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_product);
        recyclerView = findViewById(R.id.ListviewproductRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}