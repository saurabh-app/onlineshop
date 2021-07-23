package com.onlineshop.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineshop.R;
import com.onlineshop.home.HomeActivity;
import com.onlineshop.model.MasterRequestModel;
import com.onlineshop.model.MasterResponceModel;
import com.onlineshop.network.ApiClient;
import com.onlineshop.network.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static final String PREFS = "PREFS";
    private static final String TAG = "LoginActivity";
    EditText etLogin_id, etPassword;
    Button btnLogin;
    String login_id, password;
    TextView signup, forget;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    private ApiInterface apiInterface;
    List<MasterResponceModel> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        sp = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        edit = sp.edit();

        signup = findViewById(R.id.signup);
        etLogin_id = findViewById(R.id.loginid);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        forget = findViewById(R.id.forget);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etLogin_id.getText().toString();
                String password = etPassword.getText().toString();
                //validate form
                if(validateLogin(username, password)) {
                    //do login

                    login(username, password);
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ForgetActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void login(final String username,final String password) {
        Call<MasterRequestModel> call = apiInterface.getLogins(username,password);

        call.enqueue(new Callback<MasterRequestModel>() {
            @Override
            public void onResponse(Call<MasterRequestModel> call, Response<MasterRequestModel> response) {
                if(response.isSuccessful()){
                    MasterRequestModel masterRequestModel = response.body();
                    if(masterRequestModel.getMasterResponceModels()!=null){
                        list=masterRequestModel.getMasterResponceModels();
                        for (int j = 0; j < list.size(); j++) {
                          String Name=list.get(j).getNAME();
                          String Email=list.get(j).getEmail();
                          String mobile=list.get(j).getMobileno();
                          String Address= String.valueOf(list.get(j).getAddress());
                          System.out.println(Name+" "+Email+" "+mobile+" "+Address);

                            edit.putString("loginid", mobile);
                            edit.putString("name",Name );
                            edit.putString("address", Address);
                            edit.putString("email",Email);
                            edit.apply();
                            Intent i = new Intent(LoginActivity.this, StartActivity.class);
                            startActivity(i);
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Error! Please try again!"+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MasterRequestModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
