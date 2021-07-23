package com.onlineshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MasterRequestModel {


    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<MasterResponceModel> masterResponceModels = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MasterResponceModel> getMasterResponceModels() {
        return masterResponceModels;
    }

    public void setMasterResponceModels(List<MasterResponceModel> masterResponceModels) {
        this.masterResponceModels = masterResponceModels;
    }
}
