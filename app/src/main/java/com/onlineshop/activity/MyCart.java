package com.onlineshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineshop.R;
import com.onlineshop.adapter.Cart_Item_Adapter;
import com.onlineshop.adapter.ProductSuccessAdapter;
import com.onlineshop.home.HomeActivity;
import com.onlineshop.model.CartProductResponceModel;
import com.onlineshop.model.MasterCartProductRequestModel;
import com.onlineshop.model.MasterProductRequestModel;
import com.onlineshop.network.ApiClient;
import com.onlineshop.network.ApiInterface;

import java.util.List;


public class MyCart extends AppCompatActivity {
    public static final String PREFS = "PREFS";
    private static final String TAG = "MyCart";
    SharedPreferences sp;
    double savings = 0;
    double payable_amt = 0;
    TextView tvSavings, tvPayableAmt, start_shopping;
    Button proceed;
    LinearLayout l1, l2, empty;
    private ProgressBar mProgressBar;
    Toolbar mytoolbar;
    private ApiInterface apiInterface;
    private List<CartProductResponceModel> lists;
    private Cart_Item_Adapter mAdapter;
    RecyclerView cart_item_recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        getSupportActionBar().setTitle("My Cart");
//        mytoolbar = findViewById(R.id.mytoolbar);
//        setSupportActionBar(mytoolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("My Cart");

        cart_item_recyclerview = findViewById(R.id.recyclerview_item_products);
        cart_item_recyclerview.setNestedScrollingEnabled(true);

        cart_item_recyclerview.setLayoutManager(new LinearLayoutManager(MyCart.this));
        cart_item_recyclerview.scheduleLayoutAnimation();
        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String login_id = sp.getString("loginid", null);
        Log.d(TAG,"username"+login_id.toString());

        tvSavings = findViewById(R.id.total_discount);
        tvPayableAmt = findViewById(R.id.total_amount);
        proceed = findViewById(R.id.proceed);
        mProgressBar = findViewById(R.id.progressBar);
        l1 = findViewById(R.id.ll_item_products);
        l2 = findViewById(R.id.ll_item);
        empty = findViewById(R.id.empty_cart);
        start_shopping=findViewById(R.id.startshopping);

        start_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });



//        mProgressBar.setVisibility(View.VISIBLE);


            getCartData(login_id);

//        class CartItems extends AsyncTask<String, Void, String> {
//
//            @Override
//            protected String doInBackground(String... params) {
//                String cartProductsURL = getResources().getString(R.string.base_url) + "cartProducts/";
//                try {
//                    URL url = new URL(cartProductsURL);
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setRequestMethod("POST");
//                    httpURLConnection.setDoInput(true);
//                    httpURLConnection.setDoOutput(true);
//                    OutputStream outputStream = httpURLConnection.getOutputStream();
//                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                    String post_Data = URLEncoder.encode("login_id", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
//
//                    bufferedWriter.write(post_Data);
//                    bufferedWriter.flush();
//                    bufferedWriter.close();
//                    outputStream.close();
//                    InputStream inputStream = httpURLConnection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                    String result = "", line = "";
//                    while ((line = bufferedReader.readLine()) != null) {
//                        result += line;
//                    }
//                    return result;
//                } catch (Exception e) {
//                    return e.toString();
//                }
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                AlertDialog.Builder builder = new AlertDialog.Builder(MyCart.this);
//                builder.setTitle("Received Message");
//
//                try {
//
//                    JSONArray productArray = new JSONArray(s);
//                    if (productArray.length() != 0) {
//
//
//                        String[] product_ids = new String[productArray.length()];
//                        String[] product_names = new String[productArray.length()];
//                        String[] product_descs = new String[productArray.length()];
//                        String[] product_imgs = new String[productArray.length()];
//                        String[] product_prices = new String[productArray.length()];
//                        String[] product_brands = new String[productArray.length()];
//                        String[] product_sps = new String[productArray.length()];
//                        String[] product_dps = new String[productArray.length()];
//                        String[] product_qtys = new String[productArray.length()];
//
//
//                        JSONObject json_data = new JSONObject();
//                        for (int i = 0; i < productArray.length(); i++) {
//                            json_data = productArray.getJSONObject(i);
//
//                            product_ids[i] = json_data.getString("id");
//                            product_names[i] = json_data.getString("name");
//                            product_descs[i] = json_data.getString("description");
//                            product_imgs[i] = json_data.getString("image");
//                            product_prices[i] = " \u20B9 " + json_data.getString("mrp") + " ";
//                            product_brands[i] = json_data.getString("brand");
//                            product_sps[i] = " \u20B9 " + json_data.getString("selling_price") + " ";
//                            double p_mrp = Double.parseDouble(json_data.getString("mrp"));
//                            double p_sp = Double.parseDouble(json_data.getString("selling_price"));
//                            double p_dp = (p_mrp - p_sp) / (p_mrp / 100);
//                            int p_dp_i = (int) p_dp;
//                            product_dps[i] = String.valueOf(p_dp_i);
//                            product_qtys[i] = json_data.getString("qty");
//                            int p_qty = Integer.parseInt(json_data.getString("qty"));
//                            savings = savings + ((p_mrp - p_sp) * p_qty);
//                            payable_amt = payable_amt + (p_sp * p_qty);
//
//
//                        }
//                        tvSavings.setText("\u20B9" + Double.toString(savings));
//                        tvPayableAmt.setText("\u20B9" + Double.toString(payable_amt));
//
//                        l1.setVisibility(View.VISIBLE);
//                        l2.setVisibility(View.VISIBLE);
//                        mProgressBar.setVisibility(View.GONE);
//
//                        RecyclerView cart_item_recyclerview = findViewById(R.id.recyclerview_item_products);
//                        cart_item_recyclerview.setLayoutManager(new LinearLayoutManager(MyCart.this));
////                        cart_item_recyclerview.setAdapter(new Cart_Item_Adapter(product_ids, product_names, product_descs, product_imgs, product_prices, product_brands, product_sps, product_dps, product_qtys, tvSavings,tvPayableAmt, MyCart.this));
//                    } else {
//                        mProgressBar.setVisibility(View.GONE);
//                        empty.setVisibility(View.VISIBLE);
//                    }
//                } catch (JSONException e) {
//                    builder.setCancelable(true);
//                    builder.setTitle("No Internet Connection");
////                    builder.setMessage(s);
//                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                        }
//                    });
//                    builder.show();
//                }
//
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//
//        }
//        CartItems items = new CartItems();
//        items.execute(login_id);


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                class PlaceOrder extends AsyncTask<String, Void, String> {
//
//                    @Override
//                    protected String doInBackground(String... params) {
//                        String cartProductsURL = getResources().getString(R.string.base_url) + "placeOrder/";
//
//                        try {
//                            URL url = new URL(cartProductsURL);
//                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                            httpURLConnection.setRequestMethod("POST");
//                            httpURLConnection.setDoInput(true);
//                            httpURLConnection.setDoOutput(true);
//                            OutputStream outputStream = httpURLConnection.getOutputStream();
//                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                            String post_Data = URLEncoder.encode("login_id", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8")+"&"+
//                                    URLEncoder.encode("savings", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8")+"&"+
//                                    URLEncoder.encode("payableamt", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
//
//                            bufferedWriter.write(post_Data);
//                            bufferedWriter.flush();
//                            bufferedWriter.close();
//                            outputStream.close();
//                            InputStream inputStream = httpURLConnection.getInputStream();
//                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                            String result = "", line = "";
//                            while ((line = bufferedReader.readLine()) != null) {
//                                result += line;
//                            }
//                            return result;
//                        } catch (Exception e) {
//                            return e.toString();
//                        }
//                    }
//
//                    @Override
//                    protected void onPostExecute(String s) {
//                        super.onPostExecute(s);
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MyCart.this);
//                        builder.setTitle("Successful")
//                                .setMessage("Order Placed Successfully")
//                                .setIcon(R.drawable.ic_check_black)
//                                .setCancelable(false)
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                        Intent ii = new Intent(MyCart.this, OrderActivity.class);
//                                        startActivity(ii);
//                                        finish();
//                                    }
//                                });
//                        builder.show();
//                    }
//
//                    @Override
//                    protected void onPreExecute() {
//                        super.onPreExecute();
//                    }
//
//
//                }
//                PlaceOrder placeOrderOBJ = new PlaceOrder();
//                placeOrderOBJ.execute(sp.getString("loginid", null),tvSavings.getText().toString(),tvPayableAmt.getText().toString());
            }
        });



    }

    private void getCartData(String login_id) {

        Call<MasterCartProductRequestModel> call = apiInterface.getproductCart(login_id);

        call.enqueue(new Callback<MasterCartProductRequestModel>() {
            @Override
            public void onResponse(Call<MasterCartProductRequestModel> call, Response<MasterCartProductRequestModel> response) {
                if(response.isSuccessful()){
                    Log.d(TAG,"Onresponce"+response.body().getCartProductResponceModelList());
                    MasterCartProductRequestModel masterCartProductRequestModel = response.body();
                    lists = masterCartProductRequestModel.getCartProductResponceModelList();
                    if (lists!=null) {

                       try {
                           for (int l = 0; l <= lists.size(); l++) {
                               double p_mrp = Double.parseDouble(String.valueOf(lists.get(l).getProductPrice()));
                               double p_sp = Double.parseDouble(String.valueOf(lists.get(l).getProductSavePrice()));
                               double p_dp = (p_mrp - p_sp) / (p_mrp / 100);
                               int p_dp_i = (int) p_dp;
//                            product_dps[i] = String.valueOf(p_dp_i);
//                            product_qtys[i] = String.valueOf(lists.get(l).getQty());
                               int p_qty = Integer.parseInt(String.valueOf(lists.get(l).getQty()));
                               savings = savings + ((p_mrp - p_sp) * p_qty);
                               payable_amt = payable_amt + (p_sp * p_qty);
                               tvSavings.setText("\u20B9" + Double.toString(savings));
                               tvPayableAmt.setText("\u20B9" + Double.toString(payable_amt));
                           }
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                            l1.setVisibility(View.VISIBLE);
                            l2.setVisibility(View.VISIBLE);
                            mAdapter = new Cart_Item_Adapter(getApplicationContext(), lists, tvSavings,tvPayableAmt);
                            cart_item_recyclerview.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();

//                    mAdapter.setProductList(lists);
                        }else{
                            empty.setVisibility(View.VISIBLE);
                            l1.setVisibility(View.GONE);
                            l2.setVisibility(View.GONE);
                        }

                }
                else {
                    Toast.makeText(MyCart.this, "Error! Please try again!"+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MasterCartProductRequestModel> call, Throwable t) {
                Toast.makeText(MyCart.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }
}
