package com.example.adminapp.productgetset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StichingType {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("stiching_value")
    @Expose
    public String stichingValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStichingValue() {
        return stichingValue;
    }

    public void setStichingValue(String stichingValue) {
        this.stichingValue = stichingValue;
    }

}