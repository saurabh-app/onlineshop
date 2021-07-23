package com.onlineshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlineshop.R;
import com.onlineshop.activity.Product;
import com.onlineshop.model.CartProductResponceModel;
import com.onlineshop.model.ProductResponcemodel;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Cart_Item_Adapter extends RecyclerView.Adapter<Cart_Item_Adapter.ProductsViewHolder> {

    public static final String PREFS = "PREFS";
    Context context;
    SharedPreferences sp;
    private List<CartProductResponceModel> lists;
    private TextView total_saving;
    private TextView total_pamt;
    public Cart_Item_Adapter(Context context, List<CartProductResponceModel> lists, TextView tvSavings, TextView tvPayableAmt) {
        this.context = context;
        this.lists = lists;
        this.total_saving=total_saving;
        this.total_pamt=total_pamt;
    }

    @NonNull
    @Override
    public Cart_Item_Adapter.ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_cart, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_Item_Adapter.ProductsViewHolder holder, int position) {
        for (int l = 0; l <= lists.size(); l++) {
            CartProductResponceModel cartProductResponceModel = lists.get(position);
            String id = cartProductResponceModel.getProductId();
            String name = cartProductResponceModel.getProductName();
            String desc = cartProductResponceModel.getProductDescription();
            String img = cartProductResponceModel.getProductImage();
            String price = String.valueOf("Rs."+cartProductResponceModel.getProductPrice());
            String selling_price = String.valueOf("Rs."+cartProductResponceModel.getProductSavePrice());
//            String brand = product_brand[position];
            String discount = String.valueOf(cartProductResponceModel.getProductOffPrice());
            String qty = String.valueOf(cartProductResponceModel.getQty());


            holder.pro_id.setText(id);
            holder.pro_name.setText(name);
            holder.pro_desc.setText(desc);
            holder.pro_price.setText(price);
            holder.pro_sp.setText(selling_price);
//            holder.pro_brand.setText(brand);
            holder.pro_discount.setText(discount + " %   OFF");
            holder.pro_qty.setText(qty);

            if (Integer.parseInt(discount) <= 0) {
                holder.pro_discount.setVisibility(View.GONE);
            }
            if (selling_price.trim().equals(price.trim())) {
                holder.pro_price.setVisibility(View.GONE);
            }


            Picasso.get().load("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260").placeholder(R.drawable.watermark_icon).into(holder.pro_img);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView pro_id;
        TextView pro_name;
        TextView pro_desc;
        TextView pro_price;
        TextView pro_sp;
        TextView pro_brand;
        TextView pro_discount;
        TextView pro_qty;
        ImageView pro_del;
        ImageView pro_img;
        ImageView add, remove;

        public ProductsViewHolder(final View itemView) {
            super(itemView);
            sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

            pro_id = itemView.findViewById(R.id.product_id);
            pro_name = itemView.findViewById(R.id.product_name);
            pro_desc = itemView.findViewById(R.id.product_short_desc);
            pro_img = itemView.findViewById(R.id.product_img);
            pro_price = itemView.findViewById(R.id.product_price);
            pro_sp = itemView.findViewById(R.id.selling_price);
            pro_brand = itemView.findViewById(R.id.brand_name);
            pro_discount = itemView.findViewById(R.id.discount);
            pro_del = itemView.findViewById(R.id.product_del);
            pro_qty = itemView.findViewById(R.id.product_qty);
            add = itemView.findViewById(R.id.add);
            remove = itemView.findViewById(R.id.remove);

            strikeThroughText(pro_price);

            pro_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product detail = new Product();
                    detail.startProductDetailActivity(pro_id.getText().toString(), context);
                }
            });
            pro_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Product detail = new Product();
//                    detail.startProductDetailActivity(pro_id.getText().toString(), context);

                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    class IncreaseProductQuantity extends AsyncTask<String, Void, String> {
//
//                        @Override
//                        protected void onPreExecute() {
//                            super.onPreExecute();
//                        }
//
//                        @Override
//                        protected void onPostExecute(String s) {
//                            super.onPostExecute(s);
//
////                            Intent intent = ((Activity) context).getIntent();
////                            ((Activity) context).finish();
////                            context.startActivity(intent);
//
//                            int qtyi = Integer.parseInt(pro_qty.getText().toString());
//                            qtyi++;
//                            pro_qty.setText(Integer.toString(qtyi));
//
//                            double gsp=Double.parseDouble(pro_sp.getText().toString().substring(2).trim());
//                            double gmrp=Double.parseDouble(pro_price.getText().toString().substring(2).trim());
//
//                            double profit=gmrp-gsp;
//
//                            double old_samt=Double.parseDouble(total_saving.getText().toString().substring(1).trim());
//                            double new_samt=old_samt+profit;
//                            total_saving.setText("\u20B9"+new_samt);
//
//                            double old_pamt=Double.parseDouble(total_pamt.getText().toString().substring(1).trim());
//                            double new_pamt=old_pamt+gsp;
//                            total_pamt.setText("\u20B9"+new_pamt);
//                        }
//
//                        @Override
//                        protected String doInBackground(String... params) {
//
//                            String urls = context.getResources().getString(R.string.base_url).concat("increaseProductQuantity/");
//                            try {
//                                URL url = new URL(urls);
//                                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                                httpURLConnection.setRequestMethod("POST");
//                                httpURLConnection.setDoInput(true);
//                                httpURLConnection.setDoOutput(true);
//                                OutputStream outputStream = httpURLConnection.getOutputStream();
//                                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                                String post_Data = URLEncoder.encode("login_id", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
//                                        URLEncoder.encode("product_id", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
//
//                                bufferedWriter.write(post_Data);
//                                bufferedWriter.flush();
//                                bufferedWriter.close();
//                                outputStream.close();
//                                InputStream inputStream = httpURLConnection.getInputStream();
//                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                                String result = "", line = "";
//                                while ((line = bufferedReader.readLine()) != null) {
//                                    result += line;
//                                }
//                                return result;
//                            } catch (Exception e) {
//                                return e.toString();
//                            }
//                        }
//                    }

                    //creating asynctask object and executing it
//                    IncreaseProductQuantity ipqOBJ = new IncreaseProductQuantity();
//                    ipqOBJ.execute(sp.getString("loginid", null), pro_id.getText().toString());


                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int qtyi = Integer.parseInt(pro_qty.getText().toString());

                    if (qtyi != 1) {
                        decreaseProductQuantity(sp.getString("loginid", null), pro_id.getText().toString());
//                        class DecreaseProductQuantity extends AsyncTask<String, Void, String> {
//
//                            @Override
//                            protected void onPreExecute() {
//                                super.onPreExecute();
//                            }
//
//                            @Override
//                            protected void onPostExecute(String s) {
//                                int qtyi = Integer.parseInt(pro_qty.getText().toString());
//                                if (qtyi != 1) {
//                                    qtyi--;
//                                    pro_qty.setText(Integer.toString(qtyi));
//                                }
//
//                                double gsp=Double.parseDouble(pro_sp.getText().toString().substring(2).trim());
//                                double gmrp=Double.parseDouble(pro_price.getText().toString().substring(2).trim());
//
//                                double profit=gmrp-gsp;
//
//                                double old_samt=Double.parseDouble(total_saving.getText().toString().substring(1).trim());
//                                double new_samt=old_samt-profit;
//                                total_saving.setText("\u20B9"+new_samt);
//
//                                double old_pamt=Double.parseDouble(total_pamt.getText().toString().substring(1).trim());
//                                double new_pamt=old_pamt-gsp;
//                                total_pamt.setText("\u20B9"+new_pamt);
//
//                            }
//
//                            @Override
//                            protected String doInBackground(String... params) {
//
//                                String urls = context.getResources().getString(R.string.base_url).concat("decreaseProductQuantity/");
//                                try {
//                                    URL url = new URL(urls);
//                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                                    httpURLConnection.setRequestMethod("POST");
//                                    httpURLConnection.setDoInput(true);
//                                    httpURLConnection.setDoOutput(true);
//                                    OutputStream outputStream = httpURLConnection.getOutputStream();
//                                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                                    String post_Data = URLEncoder.encode("login_id", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
//                                            URLEncoder.encode("product_id", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
//
//                                    bufferedWriter.write(post_Data);
//                                    bufferedWriter.flush();
//                                    bufferedWriter.close();
//                                    outputStream.close();
//                                    InputStream inputStream = httpURLConnection.getInputStream();
//                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                                    String result = "", line = "";
//                                    while ((line = bufferedReader.readLine()) != null) {
//                                        result += line;
//                                    }
//                                    return result;
//                                } catch (Exception e) {
//                                    return e.toString();
//                                }
//                            }
//                        }

                        //creating asynctask object and executing it
//                        DecreaseProductQuantity dpqOBJ = new DecreaseProductQuantity();
//                        dpqOBJ.execute(sp.getString("loginid", null), pro_id.getText().toString());
                    }

                }
            });

            pro_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();


//                    class DeleteProduct extends AsyncTask<String, Void, String> {
//
//                        @Override
//                        protected void onPreExecute() {
//                            super.onPreExecute();
//                        }
//
//                        @Override
//                        protected void onPostExecute(String s) {
//                            super.onPostExecute(s);
//
//                            Intent intent = ((Activity) context).getIntent();
//                            ((Activity) context).finish();
//                            context.startActivity(intent);
//
//                        }
//
//                        @Override
//                        protected String doInBackground(String... params) {
//
//                            String urls = context.getResources().getString(R.string.base_url).concat("deleteCartItem/");
//                            try {
//                                URL url = new URL(urls);
//                                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                                httpURLConnection.setRequestMethod("POST");
//                                httpURLConnection.setDoInput(true);
//                                httpURLConnection.setDoOutput(true);
//                                OutputStream outputStream = httpURLConnection.getOutputStream();
//                                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                                String post_Data = URLEncoder.encode("login_id", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
//                                        URLEncoder.encode("product_id", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
//
//                                bufferedWriter.write(post_Data);
//                                bufferedWriter.flush();
//                                bufferedWriter.close();
//                                outputStream.close();
//                                InputStream inputStream = httpURLConnection.getInputStream();
//                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                                String result = "", line = "";
//                                while ((line = bufferedReader.readLine()) != null) {
//                                    result += line;
//                                }
//                                return result;
//                            } catch (Exception e) {
//                                return e.toString();
//                            }
//                        }
//                    }

//                    DeleteProduct dpOBJ = new DeleteProduct();
//                    dpOBJ.execute(sp.getString("loginid", null), pro_id.getText().toString());


                }
            });


        }
    }

    private void decreaseProductQuantity(String loginid, String pro_id) {

    }

    private void strikeThroughText(TextView price) {
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }
}