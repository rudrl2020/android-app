package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.security.Key;

public class MyinfoActivity extends Fragment {
    private View view;

    private String TAG = "프래그먼트";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.myinfo, container, false);

        TextView tv_name =view.findViewById(R.id.tv_Name);
        Button logout=(Button)view.findViewById(R.id.logout);
        Button userchange=(Button)view.findViewById(R.id.userchange);
        Button couponBox = (Button)view.findViewById(R.id.couponbox);
        Button btn_orderList= (Button)view.findViewById(R.id.btn_orderList);

        Intent intent = getActivity().getIntent();
        String userName = intent.getStringExtra("userName");
        String userStamps = intent.getStringExtra("userStamps");
        String userPhone = intent.getStringExtra("userPhone");

        if(userName != null) {
            tv_name.setText(userName);
        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        couponBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity().getApplicationContext(),CouponsActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("userStamps", userStamps);
                intent.putExtra("userPhone", userPhone);
                startActivity(intent);
            }
        });

        userchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(),UpdateActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("userPhone", userPhone);
                startActivity(intent);
            }
        });
        btn_orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(),SelectOrderList.class);
                intent.putExtra("userName", userName);
                intent.putExtra("userPhone", userPhone);
                startActivity(intent);
            }
        });



        return view;
    }
}