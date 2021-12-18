package com.example.project1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragMenu extends Fragment {
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
        View view = inflater.inflate(R.layout.menu, container, false);

        myRecyclerViewAdapter adapter;
        RecyclerView recyclerView;

        ArrayList<DataModel> dataModels = new ArrayList();


        dataModels.add(new DataModel("Coffee",R.drawable.coffee));
        dataModels.add(new DataModel("Latte",R.drawable.latte));
        dataModels.add(new DataModel("Frappe",R.drawable.frappe));
        dataModels.add(new DataModel("Yogurt",R.drawable.yogurt));
        dataModels.add(new DataModel("Ade",R.drawable.ade));
        dataModels.add(new DataModel("Tea",R.drawable.tea));
        dataModels.add(new DataModel("Desert",R.drawable.desert));





        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new myRecyclerViewAdapter(getActivity(),dataModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));

        return view;
    }
}
