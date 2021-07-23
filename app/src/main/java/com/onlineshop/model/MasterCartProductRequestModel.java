package com.onlineshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MasterCartProductRequestModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<CartProductResponceModel> cartProductResponceModelList = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CartProductResponceModel> getCartProductResponceModelList() {
        return cartProductResponceModelList;
    }

    public void setCartProductResponceModelList(List<CartProductResponceModel> cartProductResponceModelList) {
        this.cartProductResponceModelList = cartProductResponceModelList;
    }
}
