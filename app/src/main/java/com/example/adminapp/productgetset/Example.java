package com.example.adminapp.productgetset;

import java.util.Comparator;
import java.util.List;

import com.example.adminapp.filsort.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {
    public static Integer INDEX_COLOR = 0;
    public static Integer INDEX_SIZE = 1;
    public static Integer INDEX_PRICE = 2;

    public static Comparator<Example> nameComparator = new Comparator<Example>() {
        @Override
        public int compare(Example o1, Example o2) {
//            return o1.getName().compareTo(o2.getName());
            return o1.getVendor().vendorName.compareTo(o2.getVendor().vendorName);
        }
    };
    private static int sc;
    public static Comparator<Example> sizeComparator = new Comparator<Example>() {
        @Override
        public int compare(Example o1, Example o2) {
//            return o1.getSize() -
            for (int i=0; i<o1.getPrdDesignRes().size(); i++){
                sc = o1.getPrdDesignRes().get(i).getProductSize().toString().compareTo(o2.getPrdDesignRes().get(i).getProductSize().toString());
            }
            return sc;
        }
    };
    public static Comparator<Example> priceComparator = new Comparator<Example>() {
        @Override
        public int compare(Example o1, Example o2) {
            return (int) (o1.getCustomerPrice() - o2.getCustomerPrice());
        }
    };

    @SerializedName("product_ID")
    @Expose
    public Integer productID;
    @SerializedName("category")
    @Expose
    public Category category;
    @SerializedName("prdDesignRes")
    @Expose
    public List<PrdDesignRe> prdDesignRes = null;
    @SerializedName("fabric")
    @Expose
    private Fabric fabric;
    @SerializedName("stichingType")
    @Expose
    private StichingType stichingType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_Urls")
    @Expose
    private List<ImageUrl_> imageUrls = null;
    @SerializedName("cost_Price")
    @Expose
    private Integer costPrice;
    @SerializedName("selling_price")
    @Expose
    private Integer sellingPrice;
    @SerializedName("vendor")
    @Expose
    private Vendor vendor;
    @SerializedName("addDate")
    @Expose
    private String addDate;
    @SerializedName("addTime")
    @Expose
    private AddTime addTime;
    @SerializedName("updateDate")
    @Expose
    private Object updateDate;
    @SerializedName("updateTime")
    @Expose
    private Object updateTime;
    @SerializedName("customerShippingCost")
    @Expose
    private Integer customerShippingCost;
    @SerializedName("vendorShippingCost")
    @Expose
    private Integer vendorShippingCost;
    @SerializedName("traderMarginRupees")
    @Expose
    private Integer traderMarginRupees;
    @SerializedName("traderPrice")
    @Expose
    public Integer traderPrice;
    @SerializedName("customerMarginRupees")
    @Expose
    private Integer customerMarginRupees;
    @SerializedName("customerPrice")
    @Expose
    public Integer customerPrice;
    @SerializedName("productImageDir")
    @Expose
    private String productImageDir;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    private String name;
    private String color;
    private Integer size;
    private Double price;

    public Example(String desc, String pcv, Integer valueOf, Double valueOf1) {
        this.name = desc;
        this.color = pcv;
        this.size = valueOf;
        this.price = valueOf1;
    }

    public Example() {

    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<PrdDesignRe> getPrdDesignRes() {
        return prdDesignRes;
    }

    public void setPrdDesignRes(List<PrdDesignRe> prdDesignRes) {
        this.prdDesignRes = prdDesignRes;
    }

    public Fabric getFabric() {
        return fabric;
    }

    public void setFabric(Fabric fabric) {
        this.fabric = fabric;
    }

    public StichingType getStichingType() {
        return stichingType;
    }

    public void setStichingType(StichingType stichingType) {
        this.stichingType = stichingType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImageUrl_> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<ImageUrl_> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public AddTime getAddTime() {
        return addTime;
    }

    public void setAddTime(AddTime addTime) {
        this.addTime = addTime;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCustomerShippingCost() {
        return customerShippingCost;
    }

    public void setCustomerShippingCost(Integer customerShippingCost) {
        this.customerShippingCost = customerShippingCost;
    }

    public Integer getVendorShippingCost() {
        return vendorShippingCost;
    }

    public void setVendorShippingCost(Integer vendorShippingCost) {
        this.vendorShippingCost = vendorShippingCost;
    }

    public Integer getTraderMarginRupees() {
        return traderMarginRupees;
    }

    public void setTraderMarginRupees(Integer traderMarginRupees) {
        this.traderMarginRupees = traderMarginRupees;
    }

    public Integer getTraderPrice() {
        return traderPrice;
    }

    public void setTraderPrice(Integer traderPrice) {
        this.traderPrice = traderPrice;
    }

    public Integer getCustomerMarginRupees() {
        return customerMarginRupees;
    }

    public void setCustomerMarginRupees(Integer customerMarginRupees) {
        this.customerMarginRupees = customerMarginRupees;
    }

    public Integer getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(Integer customerPrice) {
        this.customerPrice = customerPrice;
    }

    public String getProductImageDir() {
        return productImageDir;
    }

    public void setProductImageDir(String productImageDir) {
        this.productImageDir = productImageDir;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}