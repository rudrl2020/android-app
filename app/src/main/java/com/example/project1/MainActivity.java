package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;

    private String TAG="메인";

    Fragment fragment_home;
    Fragment fragment_order;
    Fragment fragment_intro;
    Fragment fragment_myinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_home=new FrameHome();
        fragment_order=new FragOrder();
        fragment_intro=new FrameIntro();
        fragment_myinfo=new MyinfoActivity();

        bottomNavigationView=findViewById(R.id.bottomNavigationView);


        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment_home).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG,"bottom click");
                switch (item.getItemId()){
                    case R.id.home:
                        Log.i(TAG,"home");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment_home).commitAllowingStateLoss();
                        return true;
                    case R.id.order:
                        Log.i(TAG,"menu");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment_order).commitAllowingStateLoss();
                        return true;
                    case R.id.my:
                        Log.i(TAG,"my");
                        // 로그인이 안된 상태면 마이페이지가 아닌 로그인페이지로 이동
                        Intent intent = getIntent();
                        String userPhone = intent.getStringExtra("userPhone");
                        if(userPhone == null) {
                            Intent notLogin =new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(notLogin);
                        }else { // 로그인이 된 상태면 마이페이지로 이동
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment_myinfo).commitAllowingStateLoss();
                        }
                }
                return true;
            }
        });


    }

}