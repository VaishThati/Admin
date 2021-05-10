package com.example.adminapp.vendor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorGetSet {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("vendor_name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private Integer mobile;
    @SerializedName("store_name")
    @Expose
    private String store;
    @SerializedName("supplier_code")
    @Expose
    private String supplierCode;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("gstin")
    @Expose
    private String gst;

    public Integer getVendorID() {
        return id;
    }

    public void setVendorID(Integer vendorID) {
        this.id = vendorID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
}
