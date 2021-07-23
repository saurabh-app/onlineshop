package com.onlineshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MasterProductRequestModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ProductResponcemodel> productResponcemodel = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProductResponcemodel> getProductResponcemodel() {
        return productResponcemodel;
    }

    public void setProductResponcemodel(List<ProductResponcemodel> productResponcemodel) {
        this.productResponcemodel = productResponcemodel;
    }
}
