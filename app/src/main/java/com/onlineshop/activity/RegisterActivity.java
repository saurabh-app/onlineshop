package com.onlineshop.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineshop.R;
import com.onlineshop.home.HomeActivity;
import com.onlineshop.model.MasterRegisteredRequestModel;
import com.onlineshop.model.MasterRequestModel;
import com.onlineshop.network.ApiClient;
import com.onlineshop.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    String name, email, mobile, city_id, locality_id, address, password,cpassword;
    Spinner city, locality;
    EditText etName, etMobile, etEmail, etAddress, etPassword , etcPassword;
    Button register;
    List<String> city_ids = new ArrayList<String>();
    List<String> locality_ids = new ArrayList<String>();
    TextView loginnext;

    int otp;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        city = findViewById(R.id.city);
        locality = findViewById(R.id.locality);
        loginnext = findViewById(R.id.loginnext);
        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etMobile = findViewById(R.id.mobile);
        etAddress = findViewById(R.id.address);
        etPassword = findViewById(R.id.password);
        etcPassword = findViewById(R.id.cpassword);
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                mobile = etMobile.getText().toString();
                address = etAddress.getText().toString();
                password = etPassword.getText().toString();
                cpassword = etcPassword.getText().toString();

//                if ((!name.equals("")) && (!email.equals("")) && (!mobile.equals("")) && (!address.equals("")) && (!password.equals("")) && (!cpassword.equals(""))) {
//                    TelephonyManager telemanager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//                    if (ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                        builder.setTitle("Permission Required")
//                                .setMessage("SMS And Phone Permission Required to get registered. Do You want to allow");
//                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent();
//                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                                intent.setData(uri);
//                                startActivity(intent);
//                            }
//                        })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        finish();
//                                    }
//                                });
//                        builder.show();
//                    } else {
//                        String getSerialNumber = telemanager.getSimSerialNumber();
//                        String getSimNumber = telemanager.getLine1Number();
                        if(!cpassword.equals(password)) {
                            Toast.makeText(RegisterActivity.this, "Password Didn't Matched", Toast.LENGTH_SHORT).show();

                        }else if(!mobile.matches("[0-9]{10}"))
                        {
                            Toast.makeText(RegisterActivity.this, "Enter Valid Number", Toast.LENGTH_SHORT).show();
                        }else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
                        {
                            Toast.makeText(RegisterActivity.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                        }
                        else  Register(name, password,email,mobile,address);
                    }
//                }else{
//                    Toast.makeText(RegisterActivity.this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
//                }




//            }
        });

//        city.setOnItemSelectedListener(this);
//        locality.setOnItemSelectedListener(this);
        loginnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();


            }
        });
    }

    private void Register(String username, String password, String email, String mobile, String address) {

        Call<MasterRegisteredRequestModel > call = apiInterface.OnsaveRegister(username,password,email,mobile,address);

        call.enqueue(new Callback<MasterRegisteredRequestModel>() {
            @Override
            public void onResponse(Call<MasterRegisteredRequestModel > call, Response<MasterRegisteredRequestModel > response) {
                if(response.isSuccessful()){
                   MasterRegisteredRequestModel masterRequestModel = response.body();
                    if(masterRequestModel.getData()!=null){
                       int result= masterRequestModel.getData().get(0).getResult();

                       if (result==1){

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                           Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                        finish();
                       }else {
                           Toast.makeText(RegisterActivity.this, " User All Reday Exit !!", Toast.LENGTH_SHORT).show();
                       }


                    } else {
                        Toast.makeText(RegisterActivity.this, "Please contact technical team!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Error! Please try again!"+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MasterRegisteredRequestModel > call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validateRegister(String username, String password, String email, String mobile, String address, String cpassword) {
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        } if(email == null || email.trim().length() == 0){
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        } if(mobile == null || mobile.trim().length() == 0){
            Toast.makeText(this, "Mobile No is required", Toast.LENGTH_SHORT).show();
            return false;
        } if(address == null || address.trim().length() == 0){
            Toast.makeText(this, "Address is required", Toast.LENGTH_SHORT).show();
            return false;
        } if(cpassword == null || cpassword.trim().length() == 0){
            Toast.makeText(this, "Confirm Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
