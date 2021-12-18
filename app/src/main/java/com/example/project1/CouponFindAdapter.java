package com.example.project1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.util.List;

public class CouponFindAdapter extends BaseAdapter {

    private Context context;
    private List<CouponFind> couponFinds;

    public CouponFindAdapter(Context context, List<CouponFind> couponFinds) {
        this.context = context;
        this.couponFinds = couponFinds;
    }

    public int getCount() {return couponFinds.size();}

    @Override
    public Object getItem(int i) {return couponFinds.get(i);}

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.activity_coupon_find_detail, null);
        ImageView iv_coupon = (ImageView)v.findViewById(R.id.iv_couponImg);



        String url = "http://175.121.166.150:8000/capston/application/CouponImg/"+couponFinds.get(i).getCoupondId()+".jpg";
        Glide.with(v).load(url).apply(new RequestOptions()
                .signature(new ObjectKey("signature string"))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
        )
                .into(iv_coupon);


        return v;
    }

}