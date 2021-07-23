package com.onlineshop.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.onlineshop.R;
import com.onlineshop.activity.LoginActivity;
import com.onlineshop.activity.StartActivity;
import com.onlineshop.adapter.Bsp_Grid;
import com.onlineshop.adapter.ProductSuccessAdapter;
import com.onlineshop.adapter.SliderAdapterImage;
import com.onlineshop.model.GridItem;
import com.onlineshop.model.MasterProductRequestModel;
import com.onlineshop.model.MasterRequestModel;
import com.onlineshop.model.ProductResponcemodel;
import com.onlineshop.model.SliderItem;
import com.onlineshop.network.ApiClient;
import com.onlineshop.network.ApiInterface;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private ApiInterface apiInterface;
    List<ProductResponcemodel> lists ;
    private ProductSuccessAdapter mAdapter;
    private ProgressBar mProgressBar;
    private GridView mGridView;
    private Bsp_Grid mGridAdapter;
    private ArrayList<GridItem> mGridData;

    List<String> bsp_id_list = new ArrayList<String>();


    public HomeFragment() {
        // Required empty public constructor
    }
    SliderView sliderView;
    private SliderAdapterImage adapter;
    RecyclerView recyclerview_best_deals,recyclerview_recent_products;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        lists = new ArrayList<>();
        mGridView = view.findViewById(R.id.gridView);

        mProgressBar = view.findViewById(R.id.progressBar);
        sliderView = view.findViewById(R.id.imageSliders);
        recyclerview_best_deals = view.findViewById(R.id.recyclerview_best_deals);
        recyclerview_recent_products = view.findViewById(R.id.recyclerview_recent_products);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerview_best_deals.setLayoutManager(layoutManager);



        recyclerview_best_deals.setNestedScrollingEnabled(true);
        recyclerview_best_deals.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_best_deals.scheduleLayoutAnimation();



        adapter = new SliderAdapterImage(getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        /*  Grid View Best Selling Product  */



        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new Bsp_Grid(getContext(), R.layout.bsp_grid_single, mGridData);
        mGridView.setAdapter(mGridAdapter);


        getProductlist();

getRecentProduct();
        renewItems(view);


        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
        return view;
    }

    private void getRecentProduct() {

        Call<MasterProductRequestModel> call = apiInterface.getproductlist();

        call.enqueue(new Callback<MasterProductRequestModel>() {
            @Override
            public void onResponse(Call<MasterProductRequestModel> call, Response<MasterProductRequestModel> response) {
                if(response.isSuccessful()){
                    Log.d(TAG,"Onresponce"+response.body().getProductResponcemodel());
                    MasterProductRequestModel masterProductRequestModel = response.body();
                    lists = masterProductRequestModel.getProductResponcemodel();
                    mAdapter = new ProductSuccessAdapter(getContext(), lists);
                    recyclerview_recent_products.setAdapter(mAdapter);


                }
                else {
                    Toast.makeText(getContext(), "Error! Please try again!"+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MasterProductRequestModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getProductlist() {

        Call<MasterProductRequestModel> call = apiInterface.getproductlist();

        call.enqueue(new Callback<MasterProductRequestModel>() {
            @Override
            public void onResponse(Call<MasterProductRequestModel> call, Response<MasterProductRequestModel> response) {
                if(response.isSuccessful()){
                    Log.d(TAG,"Onresponce"+response.body().getProductResponcemodel());
                    MasterProductRequestModel masterProductRequestModel = response.body();
                    lists = masterProductRequestModel.getProductResponcemodel();
//                    mAdapter = new ProductSuccessAdapter(getContext(), lists);
//                    product_recyclerview.setAdapter(mAdapter);
                    mAdapter = new ProductSuccessAdapter(getContext(), lists);
                    recyclerview_best_deals.setAdapter(mAdapter);
//                    mAdapter.setProductList(lists);


                }
                else {
                    Toast.makeText(getContext(), "Error! Please try again!"+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MasterProductRequestModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
//            sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
            } else {
                sliderItem.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("Slider Item Added Manually");
        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        adapter.addItem(sliderItem);
    }
}
