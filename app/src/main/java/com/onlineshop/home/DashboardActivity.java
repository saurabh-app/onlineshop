package com.onlineshop.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.onlineshop.R;
import com.onlineshop.activity.LoginActivity;

import com.onlineshop.activity.MyCart;
import com.onlineshop.fragment.HomeFragment;
import com.onlineshop.interFace.AddorRemoveCallbacks;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AddorRemoveCallbacks {
    private String TAG;

    private DrawerLayout drawer;
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TextView Sign, txtusername;
    private static int cart_count=0;
    LinearLayout llHome, llAbout, llService, llRepair, llInsurance, llcontact, llcart, llbook, llvouch, llSignout;

    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        drawer.setDrawerShadow(R.drawable.navigation_drawer_shadow, GravityCompat.START);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);

//        mDrawerToggle = new ActionBarDrawerToggle(
//                this,
//                drawer,
//                R.drawable.ic_drawer,
//                R.string.navigation_drawer_open,
//                R.string.navigation_drawer_close
//        )
//
//        {
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                /* empty */
//            }
//
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                /* empty */
//            }
//
//
//        };
//
//        drawer.post(new Runnable() {
//            @Override
//            public void run() {
//                mDrawerToggle.syncState();
//            }
//        });
//
//        drawer.setDrawerListener(mDrawerToggle);


        final Fragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment,"OnlineShop");
        ft.commit();
        toolbar.setTitle("OnlineShop");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        sp = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = sp.edit();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        llHome = (LinearLayout) navigationView.findViewById(R.id.llHome);
        llRepair = (LinearLayout) navigationView.findViewById(R.id.llRepair);
        llAbout = (LinearLayout) navigationView.findViewById(R.id.llAbout);
        llService = (LinearLayout) navigationView.findViewById(R.id.llService);
        llInsurance = (LinearLayout) navigationView.findViewById(R.id.llInsurance);
        llcart = (LinearLayout) navigationView.findViewById(R.id.llcart);
        llbook = (LinearLayout) navigationView.findViewById(R.id.llbook);
        llvouch = (LinearLayout) navigationView.findViewById(R.id.llvouch);
        llcontact = (LinearLayout) navigationView.findViewById(R.id.llcontact);
        llSignout = (LinearLayout) navigationView.findViewById(R.id.llSignot);
        Sign = (TextView) navigationView.findViewById(R.id.tv_email);
//        txtusername = (TextView) navigationView.findViewById(R.id.txtusername);
//        Sign.setText("Welcome " + sharedPreferences.getString("Name", "") + " " + sharedPreferences.getString("email", ""));

        if (sp.getString("loginid", null) == null) {
            Sign.setText("Sign Out");
        }
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.commit();
                // AppState.getSingleInstance().setLoggingOut(true);
                Intent intent = new Intent(DashboardActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, "Dashboard");
                ft.commit();
                toolbar.setTitle("OnlineShop");
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        llSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                // AppState.getSingleInstance().setLoggingOut(true);
                Log.d(TAG, "Now log out and start the activity login");
                Intent intent = new Intent(DashboardActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }



//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_apriori, menu);
//
//        final MenuItem menuItem = menu.findItem(R.id.action_cart);
////        menuItem.setIcon(Converter.convertLayoutToImage(DashboardActivity.this,cart_count,R.drawable.ic_shopping_cart_white));
//
//
//
//
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        return true;
//    }



    @Override
    protected void onPostResume() {
        super.onPostResume();
        invalidateOptionsMenu();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apriori, menu);
        return true;
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the HomeActivity/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Are you sure you want to exit?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            DashboardActivity.this.finish();
////                            Application.finish();
//                        }
//                    })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_cart) {



            if(sp.getString("loginid",null)!=null) {
                Intent i = new Intent(this, MyCart.class);
                startActivity(i);
                return true;
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle("Heyy..")
                        .setMessage("To see your cart you have to login first. Do you want to login ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(DashboardActivity.this,LoginActivity.class);
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


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.llHome) {
            Fragment fragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
            ft.commit();
               toolbar.setTitle("OnlineShop");

        }
//        else if (id == R.id.llAbout) {
//            Fragment fragment = new AboutsFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
//            ft.commit();
//        } else if (id == R.id.nav_Service) {
//
//        } else if (id == R.id.nav_repair) {
//
//        } else if (id == R.id.nav_insurance) {
//
//        } else if (id == R.id.nav_contact) {
//            Fragment fragment = new ContactFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
//            ft.commit();
//
//        } else if (id == R.id.nav_profile) {
//
//        } else if (id == R.id.nav_booking) {
//
//        } else if (id == R.id.nav_vouchersicon) {
//
//        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onAddProduct() {
        cart_count++;
        invalidateOptionsMenu();
    }

    @Override
    public void onRemoveProduct() {
        cart_count--;
        invalidateOptionsMenu();
    }



}
