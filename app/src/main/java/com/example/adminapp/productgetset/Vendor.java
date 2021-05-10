package com.example.adminapp.productgetset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vendor {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("vendor_name")
    @Expose
    public String vendorName;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("supplier_code")
    @Expose
    private String supplierCode;
    @SerializedName("gstin")
    @Expose
    private String gstin;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("isDeleted")
    @Expose
    private Object isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Object isDeleted) {
        this.isDeleted = isDeleted;
    }

}