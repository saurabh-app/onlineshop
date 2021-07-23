package com.onlineshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductResponcemodel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName("product_price")
    @Expose
    private Integer productPrice;
    @SerializedName("product_off_price")
    @Expose
    private Integer productOffPrice;
    @SerializedName("product_save_price")
    @Expose
    private Integer productSavePrice;
    @SerializedName("is_valid")
    @Expose
    private Integer isValid;
    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("create_by")
    @Expose
    private String createBy;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("update_by")
    @Expose
    private Object updateBy;
    @SerializedName("update_date")
    @Expose
    private String updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
