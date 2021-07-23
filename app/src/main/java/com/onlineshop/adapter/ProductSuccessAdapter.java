package com.onlineshop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlineshop.R;
import com.onlineshop.activity.AddToCart;
import com.onlineshop.activity.LoginActivity;
import com.onlineshop.activity.Product;
import com.onlineshop.interFace.AddorRemoveCallbacks;
import com.onlineshop.model.ProductResponcemodel;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ProductSuccessAdapter extends RecyclerView.Adapter<ProductSuccessAdapter.ProductsViewHolder> {
    public static final String PREFS = "PREFS";
    Context context;
    SharedPreferences sp;
   private List<ProductResponcemodel> lists;
    String name,desc,price,selling_price,discounts,img;
    String id;

    public ProductSuccessAdapter(Context context, List<ProductResponcemodel> lists) {
        this.context=context;
        this.lists=lists;

    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_recent_products, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        for(int l=0; l<=lists.size(); l++){
        ProductResponcemodel productResponcemodel=lists.get(position);

            id = String.valueOf(productResponcemodel.getId());
             name = productResponcemodel.getProductName();
             desc = productResponcemodel.getProductDescription();
             price = String.valueOf(Float.valueOf(productResponcemodel.getProductPrice()));
             selling_price = String.valueOf(productResponcemodel.getProductOffPrice());
            discounts = String.valueOf(Float.valueOf(productResponcemodel.getProductSavePrice()));
             img = productResponcemodel.getProductImage();
//           try {
               holder.product_id.setText(id);
//
//        }catch (Exception e){
//               e.printStackTrace();
//           }
               holder.product_name.setText(name);
               holder.product_short_desc.setText(desc);
               holder.product_price.setText(price);
               holder.selling_price.setText(discounts);
//        holder.pro_brand.setText(brand);
               holder.discount.setText(selling_price + " %   OFF");

//               if (Integer.parseInt((discounts)) <= 0) {
//                   holder.discount.setVisibility(View.GONE);
//               }
//               if (selling_price.trim().equals("\u20B9" + price.trim())) {
//                   holder.product_price.setVisibility(View.GONE);
//               }


               Picasso.get().load("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                       .placeholder(R.drawable.watermark_icon)
                       .into(holder.product_img);

//

//                with(context).load(img).placeholder(R.drawable.watermark_icon).into(holder.pro_img);



        }
    }

    @Override
    public int getItemCount() {
        if(lists != null){
            return lists.size();
        }
        return 0;
    }

    public void setProductList(List<ProductResponcemodel> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        TextView product_id,product_name,product_short_desc,product_price,selling_price,brand_name,discount,product_add;

        ImageView product_img;
        LinearLayout product_card;

        public ProductsViewHolder(View itemView) {
            super(itemView);

            product_id = itemView.findViewById(R.id.product_id);
            product_name = itemView.findViewById(R.id.product_name);
            product_short_desc = itemView.findViewById(R.id.product_short_desc);
            product_img = itemView.findViewById(R.id.product_img);
            product_price = itemView.findViewById(R.id.product_price);
            selling_price = itemView.findViewById(R.id.selling_price);
            brand_name = itemView.findViewById(R.id.brand_name);
            discount = itemView.findViewById(R.id.discount);
            product_add = itemView.findViewById(R.id.product_add);
            strikeThroughText(product_price);
            product_card = itemView.findViewById(R.id.product_card);
            sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

            product_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product detail = new Product();
                    detail.startProductDetailActivity(product_id.getText().toString(), context);
                }
            });
            product_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product detail = new Product();
                    detail.startProductDetailActivity(product_id.getText().toString(), context);
                }
            });
            product_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product detail = new Product();
                    detail.startProductDetailActivity(product_id.getText().toString(), context);

                }
            });
            product_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sp.getString("loginid",null)!=null) {
                        AddToCart addToCart = new AddToCart(context);
                        addToCart.addToCart(product_id.getText().toString(), "1");
                        ((AddorRemoveCallbacks)context).onAddProduct();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Heyy..")
                                .setMessage("To add this item in your cart you have to login first. Do you want to login ")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(context, LoginActivity.class);
                                        context.startActivity(intent);
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

        }
    }

    private void strikeThroughText(TextView price) {
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
