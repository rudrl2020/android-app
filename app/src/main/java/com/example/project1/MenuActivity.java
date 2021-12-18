package com.example.project1;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private String TAG="메인";

    Fragment fragment_home;
    Fragment fragment_order;
    Fragment fragment_intro;
    Fragment fragment_menu;
    Fragment fragment_myinfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        fragment_home=new FrameHome();
        fragment_order=new FragOrder();
        fragment_intro=new FrameIntro();
        fragment_menu=new FragMenu();
        fragment_myinfo=new MyinfoActivity();

        bottomNavigationView=findViewById(R.id.bottomNavigationView);


        getSupportFragmentManager().beginTransaction().replace(R.id.main_menulayout,fragment_menu).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG,"bottom click");
                switch (item.getItemId()){
                    case R.id.home:
                        Log.i(TAG,"home");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_menulayout,fragment_home).commitAllowingStateLoss();
                        return true;
                    case R.id.order:
                        Log.i(TAG,"menu");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_menulayout,fragment_order).commitAllowingStateLoss();
                        return true;
                    case R.id.my:
                        Log.i(TAG,"my");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_menulayout,fragment_myinfo).commitAllowingStateLoss();
                }
                return true;
            }
        });

    }
}
