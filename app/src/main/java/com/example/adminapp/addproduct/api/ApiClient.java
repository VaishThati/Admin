package com.example.adminapp.addproduct.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
//    String imageaddurl = "http://103.150.187.59:54691/api/Product/AddProductDesignWithImages";

    public static final String BASE_URL = "http://103.150.187.59:54691/api/Product/AddProductDesignWithImages/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

