package com.onlineshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.onlineshop.R;

public class ForgetActivity extends AppCompatActivity {
    EditText email;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        email = findViewById(R.id.email);
        submit = findViewById(R.id.submit_otp);
    }
}
