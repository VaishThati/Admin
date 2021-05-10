package com.example.adminapp.productgetset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductColor {

    @SerializedName("productColor_ID")
    @Expose
    private Integer productColorID;
    @SerializedName("productColorValue")
    @Expose
    private String productColorValue;

    public Integer getProductColorID() {
        return productColorID;
    }

    public void setProductColorID(Integer productColorID) {
        this.productColorID = productColorID;
    }

    public String getProductColorValue() {
        return productColorValue;
    }

    public void setProductColorValue(String productColorValue) {
        this.productColorValue = productColorValue;
    }

}