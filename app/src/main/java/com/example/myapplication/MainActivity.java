package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.myapplication.ui.current.CurrentFragment;
import com.example.myapplication.ui.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

//import com.example.myapplication.ui.device.DeviceFragment;
//import com.example.myapplication.ui.history.HistoryFragment;
//import com.example.myapplication.ui.map.MapFragment;

public class MainActivity extends AppCompatActivity  {

    public static Activity activity;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;

        //전역변수 로그
        Log.d("main",UserInfo.userName);
        Log.d("main",UserInfo.userID);
        Log.d("main",UserInfo.userCompany);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.ContentLayout, new CurrentFragment()).commit();

        this.InitalizeLayout();
    }

    //fragment to fragment
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ContentLayout, fragment).commit();
    }

    public void InitalizeLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.userName)).setText(UserInfo.userName);
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.userID)).setText(UserInfo.userID);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            //// 네비게이션 메뉴 클릭 시 이벤트 입력 /////////
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_current:
                        //토스트 메시지 띄우기
                        //Toast.makeText(getApplicationContext(), "HELLO", Toast.LENGTH_SHORT).show();
                        replaceFragment(new CurrentFragment());
                        break;

                    case R.id.nav_history:
                        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_map:
                        Intent intent1 = new Intent(MainActivity.this, MapActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_device:
                        Intent intent2 = new Intent(MainActivity.this, AQIHistoryAcitivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_profile:
                        replaceFragment(new ProfileFragment());
                        break;

                    case R.id.nav_sign_out:
                        Intent intent3 = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent3);
                        finish();
                        break;

                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

        });
    }

}






