package com.onlineshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineshop.R;
import com.onlineshop.model.MasterProductRequestModel;
import com.onlineshop.model.ProductResponcemodel;
import com.onlineshop.network.ApiClient;
import com.onlineshop.network.ApiInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PREFS = "PREFS";
    TextView tvProductName, tvProductDesc, tvMRP, tvPrice, tvSaved, tvSavedPer, tvQty, addtocart, product_id, buy_now;
    ImageView ivProductImage, add, remove;
    String p_id;
    SharedPreferences sp;
    ScrollView view;
    private ProgressBar mProgressBar;
    AddToCart addToCart;
    private String TAG="ProductDetailActivity";
    private ApiInterface apiInterface;
    private List<ProductResponcemodel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        view = findViewById(R.id.product_page);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        addToCart=new AddToCart(ProductDetailActivity.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Bundle extras = getIntent().getExtras();
        p_id = extras.getString("p_id");
        Log.d(TAG,":ss"+p_id);

        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        addtocart = findViewById(R.id.addtocart);
        product_id = findViewById(R.id.product_id);

        tvProductName = findViewById(R.id.product_name);
        tvProductDesc = findViewById(R.id.product_desc);
        tvMRP = findViewById(R.id.mrp);
        tvPrice = findViewById(R.id.price);
        tvSaved = findViewById(R.id.saved);
        tvSavedPer = findViewById(R.id.saved_per);
        tvQty = findViewById(R.id.product_qty);
        ivProductImage = findViewById(R.id.product_img);
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        buy_now = findViewById(R.id.buynow);
        tvMRP.setPaintFlags(tvMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        final int[] number = {1};
        tvQty.setText("" + number[0]);

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginid = sp.getString("loginid", null);
                if (loginid != null) {
                    addToCart.addToCart(product_id.getText().toString(),tvQty.getText().toString());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
                    builder.setTitle("Heyy..")
                            .setMessage("To add this item in your cart you have to login first. Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(ProductDetailActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No Just Continue ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setCancelable(false);
                    builder.show();
                }
            }
        });
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginid = sp.getString("loginid", null);
                if (loginid != null) {
                    addToCart.addToCart(product_id.getText().toString(),tvQty.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MyCart.class);
                    startActivity(intent);
                    finish();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
                    builder.setTitle("Heyy..")
                            .setMessage("To add this item in your cart you have to login first. Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(ProductDetailActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No Just Continue ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setCancelable(false);
                    builder.show();
                }
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                number[0] = number[0] + 1;
                tvQty.setText("" + number[0]);

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number[0] == 1) {
                    tvQty.setText("" + number[0]);
                }

                if (number[0] > 1) {

                    number[0] = number[0] - 1;
                    tvQty.setText("" + number[0]);
                }
            }
        });

        getProductDeatails(p_id);


//        ProductDetail productDetail = new ProductDetail();
//        productDetail.execute();

    }

    private void getProductDeatails(final String p_id) {
        Call<MasterProductRequestModel> call = apiInterface.getProductDetails(p_id);

        call.enqueue(new Callback<MasterProductRequestModel>() {
            @Override
            public void onResponse(Call<MasterProductRequestModel> call, Response<MasterProductRequestModel> response) {
                if(response.isSuccessful()){
                    Log.d(TAG,"ddhhgd"+response.toString());
                    MasterProductRequestModel masterProductRequestModel = response.body();
                    if(masterProductRequestModel.getProductResponcemodel()!=null){
                        list=masterProductRequestModel.getProductResponcemodel();
                        for (int j = 0; j < list.size(); j++) {
                            String id= String.valueOf(list.get(j).getId());
                            String Name=list.get(j).getProductName();
                            String Description=list.get(j).getProductDescription();
                            String Image=list.get(j).getProductImage();
                            String price= String.valueOf(list.get(j).getProductPrice());
                            String priceoff= String.valueOf(list.get(j).getProductOffPrice());
                            String pricesave= String.valueOf(list.get(j).getProductSavePrice());


                            tvProductName.setText(Name);
                tvProductDesc.setText(Description);
                tvMRP.setText("\u20B9 " +price );
                tvPrice.setText("\u20B9 " + pricesave);
                product_id.setText(id);
                tvQty.setText("1");
//                Picasso.get().load(Image).placeholder(R.drawable.watermark_icon).into(ivProductImage);
                            Picasso.get().load("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                                    .placeholder(R.drawable.watermark_icon)
                                    .into(ivProductImage);
                double p_mrp = Double.parseDouble(price);
                double p_sp = Double.parseDouble(pricesave);
                double p_dp = (p_mrp - p_sp);
                double p_dp_p = p_dp / (p_mrp / 100);


                int p_dp_i = (int) p_dp;
                int p_dp_p_i = (int) p_dp_p;

                tvSaved.setText("\u20B9" + p_dp_i);
                tvSavedPer.setText("(" + p_dp_p_i + "%)");
                mProgressBar.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);

                        }
                    }
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Error! Please try again!"+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MasterProductRequestModel> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

//    class ProductDetail extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            String productUrl = getResources().getString(R.string.base_url) + "getProductDetail/" + p_id;
//
//            try {
//                URL url = new URL(productUrl);
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setDoOutput(true);
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                String result = "", line = "";
//                while ((line = bufferedReader.readLine()) != null) {
//                    result += line;
//                }
//                return result;
//            } catch (Exception e) {
//                return e.toString();
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
//            builder.setTitle("Received Message");
//
//            try {
//
//                JSONArray productDetailsArray = new JSONArray(s);
//                JSONObject json_data = new JSONObject();
//                json_data = productDetailsArray.getJSONObject(0);
//
//                tvProductName.setText(json_data.getString("name"));
//                tvProductDesc.setText(json_data.getString("description"));
//                tvMRP.setText("\u20B9 " + json_data.getString("mrp"));
//                tvPrice.setText("\u20B9 " + json_data.getString("selling_price"));
//                product_id.setText(json_data.getString("id"));
//                tvQty.setText("1");
//                Picasso.with(ProductDetailActivity.this).load(getResources().getString(R.string.img_base_url) + "product_images/" + json_data.getString("image")).placeholder(R.drawable.watermark_icon).into(ivProductImage);
//
//                double p_mrp = Double.parseDouble(json_data.getString("mrp"));
//                double p_sp = Double.parseDouble(json_data.getString("selling_price"));
//                double p_dp = (p_mrp - p_sp);
//                double p_dp_p = p_dp / (p_mrp / 100);
//
//
//                int p_dp_i = (int) p_dp;
//                int p_dp_p_i = (int) p_dp_p;
//
//                tvSaved.setText("\u20B9" + p_dp_i);
//                tvSavedPer.setText("(" + p_dp_p_i + "%)");
//                mProgressBar.setVisibility(View.GONE);
//                view.setVisibility(View.VISIBLE);
//
//            } catch (JSONException e) {
//                builder.setCancelable(true);
//                builder.setTitle("No Internet Connection");
//                builder.setMessage("Please Connect to internet");
//                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                builder.show();
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//
//    }
}
