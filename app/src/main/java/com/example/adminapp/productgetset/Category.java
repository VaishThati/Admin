package com.example.adminapp.productgetset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

//    public Category(int id, String value) {
//        this.id = id;
//        this.categoryValue = value;
//    }

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("category_value")
    @Expose
    public String categoryValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

}