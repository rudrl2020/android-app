package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    String TAG = "RecyclerViewAdapter";
    ArrayList<Order_Menu> orderList;
    Context context;

    public RecyclerViewAdapter(Context context, ArrayList<Order_Menu> orderList){
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_order_menu_detail, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");

        MyViewHolder myViewHolder = (MyViewHolder)holder;

        myViewHolder.tv_menuName.setText(orderList.get(position).getMenuName());
        myViewHolder.tv_price.setText(orderList.get(position).getPrice());

//        myViewHolder.iv_menu.setImageResource(orderList.get(position).);

//        String url = "http://175.121.166.150:8000/capston/menu/menuImage/"+orderList.get(position).getImgName()+".jpg";
//        Glide.with(myViewHolder).load(url).apply(new RequestOptions()
//                .signature(new ObjectKey("signature string"))
//                .skipMemoryCache(true)
//                .override(100,100)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//        )
//                .into(myViewHolder.iv_menu);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_menuName,tv_price;
        ImageView iv_menu;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tv_menuName = itemView.findViewById(R.id.tv_menuName);
            tv_price = itemView.findViewById(R.id.tv_price);
            iv_menu = itemView.findViewById(R.id.iv_menu);
        }
    }
}
