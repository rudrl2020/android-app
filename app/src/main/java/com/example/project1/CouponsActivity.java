package com.example.project1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class CouponsActivity  extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    CouponsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpager2);


        FragmentManager fm = getSupportFragmentManager();
        adapter = new CouponsAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);


        tabLayout.addTab(tabLayout.newTab().setText("스탬프"));
        tabLayout.addTab(tabLayout.newTab().setText("쿠폰"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}