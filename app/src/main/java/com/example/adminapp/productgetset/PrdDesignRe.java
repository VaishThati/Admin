package com.example.adminapp.productgetset;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrdDesignRe {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("productColor")
    @Expose
    public ProductColor productColor;
    @SerializedName("productSize")
    @Expose
    private Object productSize;
    @SerializedName("image_Urls")
    @Expose
    private List<ImageUrl> imageUrls = null;
    @SerializedName("quantity")
    @Expose
    public Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductColor getProductColor() {
        return productColor;
    }

    public void setProductColor(ProductColor productColor) {
        this.productColor = productColor;
    }

    public Object getProductSize() {
        return productSize;
    }

    public void setProductSize(Object productSize) {
        this.productSize = productSize;
    }

    public List<ImageUrl> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<ImageUrl> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}