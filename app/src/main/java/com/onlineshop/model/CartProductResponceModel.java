package com.onlineshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartProductResponceModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobileno")
    @Expose
    private String mobileno;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_price")
    @Expose
    private Integer productPrice;
    @SerializedName("product_off_price")
    @Expose
    private Integer productOffPrice;
    @SerializedName("product_save_price")
    @Expose
    private Integer productSavePrice;
    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("product_image")
    @Expose
    private String productImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductOffPrice() {
        return productOffPrice;
    }

    public void setProductOffPrice(Integer productOffPrice) {
        this.productOffPrice = productOffPrice;
    }

    public Integer getProductSavePrice() {
        return productSavePrice;
    }

    public void setProductSavePrice(Integer productSavePrice) {
        this.productSavePrice = productSavePrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
