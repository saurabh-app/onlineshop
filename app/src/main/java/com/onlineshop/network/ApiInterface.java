package com.onlineshop.network;

import com.onlineshop.model.MasterCartProductRequestModel;
import com.onlineshop.model.MasterCartRequestModel;
import com.onlineshop.model.MasterProductRequestModel;
import com.onlineshop.model.MasterRegisteredRequestModel;
import com.onlineshop.model.MasterRequestModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @POST(EndApi.LOGIN)
    @FormUrlEncoded
    Call<MasterRequestModel> getLogins(@Field("mobileno") String username, @Field("password") String password);

    @POST(EndApi.REGISTER)
    @FormUrlEncoded
    Call<MasterRegisteredRequestModel> OnsaveRegister(@Field("name") String name,
                                                      @Field("password") String password,
                                                      @Field("email") String email,
                                                      @Field("mobileno") String mobile,
                                                      @Field("address") String address);
    @GET(EndApi.PRODUCTLIST)
    Call<MasterProductRequestModel> getproductlist();

    @GET(EndApi.PRODUCTDETAIL)
    Call<MasterProductRequestModel> getProductDetails(@Query("id") String p_id);

    @POST(EndApi.ADDCART)
    @FormUrlEncoded
    Call<MasterCartRequestModel> getProductCart(@Field("id") int id,
                                                @Field("username") String loginid,
                                                @Field("product_id") String pid,
                                                @Field("qty") String qty);
    @GET(EndApi.CARTDATA)
    Call<MasterCartProductRequestModel> getproductCart(@Query("username") String login_id);
}