package com.onlineshop.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.onlineshop.R;
import com.onlineshop.model.Datum;
import com.onlineshop.model.MasterCartRequestModel;
import com.onlineshop.model.MasterProductRequestModel;
import com.onlineshop.network.ApiClient;
import com.onlineshop.network.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToCart {
    private static final String TAG = "AddToCart";
    SharedPreferences sp;
    Context context;
    public static final String PREFS = "PREFS";
    int count;
    private ApiInterface apiInterface;
    int id=0;
    private List<Datum> list;

    public AddToCart(Context context) {
        this.context=context;
        this.sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void addToCart(String pid,String qty) {
        apiInterface = ApiClient.getClient().create(ApiInterface .class);

        String loginid = sp.getString("loginid", null);
        count=Integer.parseInt(qty);
        count++;
        if ((!pid.equals("")) && (!qty.equals("")) && (!loginid.equals(null))) {
            addProductCard(loginid, pid, qty);
        }else {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Not Added To Cart");
            builder.setMessage("The Product That You want to add is not added");
            builder.show();
        }

    }

    private void addProductCard(String loginid, String pid, String qty) {
        Call<MasterCartRequestModel> call = apiInterface.getProductCart(id,loginid,pid,qty);

        call.enqueue(new Callback<MasterCartRequestModel>() {
            @Override
            public void onResponse(Call<MasterCartRequestModel> call, Response<MasterCartRequestModel> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "ddhhgd" + response.toString());
                    MasterCartRequestModel masterCartRequestModel = response.body();
                    if (masterCartRequestModel.getData() != null) {
                        int result= masterCartRequestModel.getData().get(0).getResult();

                            if (result == 1) {
                                Toast.makeText(context, "Product Added to cart successfully !!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context,  "Product AllReady Added to cart successfully !!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }



            @Override
            public void onFailure(Call<MasterCartRequestModel> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
