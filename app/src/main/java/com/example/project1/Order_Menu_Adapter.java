package com.example.project1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Order_Menu_Adapter extends BaseAdapter {

    private Context context;
    private List<Order_Menu> order_menuList;
    private String userPhone;

    public Order_Menu_Adapter(Context context, List<Order_Menu> order_menuList, String userPhone) {
        this.context = context;
        this.order_menuList = order_menuList;
        this.userPhone = userPhone;

    }

    @Override
    public int getCount() {
        return order_menuList.size();
    }

    @Override
    public Object getItem(int i) {return order_menuList.get(i);}

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.activity_order_menu_detail, null);

        TextView menuName = (TextView) v.findViewById(R.id.tv_menuName);
        TextView menuPrice = (TextView) v.findViewById(R.id.tv_price);
        ImageView menuImg = (ImageView) v.findViewById(R.id.iv_menu);
        Button  btn_addCart = (Button) v.findViewById(R.id.btn_addCart);

        btn_addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userPhone==null){
                    Toast.makeText(context.getApplicationContext(),"로그인을 해주세요",Toast.LENGTH_SHORT).show();
                }

                String menu = menuName.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context.getApplicationContext(), "장바구니에 담았습니다!", Toast.LENGTH_SHORT).show();
                    }
                };
                OrderMenuRequest orderMenuRequest = new OrderMenuRequest(userPhone, menu, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                queue.add(orderMenuRequest);
            }
        });




        menuName.setText(order_menuList.get(i).getMenuName());
        menuPrice.setText(order_menuList.get(i).getPrice());
        Log.e("orderMenu", order_menuList.get(i).getImgName());
        String url = "http://175.121.166.150:8000/capston/menu/menuImage/"+order_menuList.get(i).getImgName()+".jpg";
        Glide.with(v).load(url).apply(new RequestOptions()
                .signature(new ObjectKey("signature string"))
                .skipMemoryCache(true)
                .override(100,100)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
        )
                .into(menuImg);


        return v;
    }

}