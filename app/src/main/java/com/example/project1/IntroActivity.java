package com.example.project1;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class IntroActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private String TAG="메인";

    Fragment fragment_home;
    Fragment fragment_menu;
    Fragment fragment_order;
    Fragment fragment_intro;
    Fragment fragment_myinfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frameintro);

        fragment_home=new FrameHome();
        fragment_menu=new FragMenu();
        fragment_order=new FragOrder();
        fragment_intro=new FrameIntro();
        fragment_myinfo=new MyinfoActivity();

        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.introlayout,fragment_intro).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG,"bottom click");
                switch (item.getItemId()){
                    case R.id.home:
                        Log.i(TAG,"home");
                        getSupportFragmentManager().beginTransaction().replace(R.id.introlayout,fragment_home).commitAllowingStateLoss();
                        return true;
                    case R.id.order:
                        Log.i(TAG,"menu");
                        getSupportFragmentManager().beginTransaction().replace(R.id.introlayout,fragment_order).commitAllowingStateLoss();
                        return true;
                    case R.id.my:
                        Log.i(TAG,"my");
                        getSupportFragmentManager().beginTransaction().replace(R.id.introlayout,fragment_myinfo).commitAllowingStateLoss();
                }
                return true;
            }
        });

    }
}
