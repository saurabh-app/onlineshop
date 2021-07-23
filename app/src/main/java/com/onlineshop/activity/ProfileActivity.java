package com.onlineshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.onlineshop.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
