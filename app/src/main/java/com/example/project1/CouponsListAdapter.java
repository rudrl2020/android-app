package com.example.project1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.util.List;

public class CouponsListAdapter extends BaseAdapter { //baseAdapter 상속

    private Context context;
    private List<DataCoupons> couponsList;

    //생성자
    public CouponsListAdapter(Context context, List<DataCoupons> couponsList) {
        this.context = context;
        this.couponsList = couponsList;
    }

    //메소드 생성
    @Override
    public int getCount() {

        return couponsList.size(); //noticeList에 size로 반환
    }

    @Override
    public Object getItem(int i) {
        return couponsList.get(i); //noticeList에 해당위치(get(i))에 있는 노티스를 반환
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.couponscontents, null); //해당하는 디자인을 사용할 수 있게
        TextView couponId = (TextView) v.findViewById(R.id.couponId); //(TextView)를 객체로 만들어 줄 수 있게
        ImageView couponsImg = (ImageView) v.findViewById(R.id.couponsImg);


        //noticeText를 현재 리스트에 있는 값으로 넣어 줄 수 있도록
        couponId.setText(couponsList.get(i).getId());

        String url = "http://175.121.166.150:8000/capston/application/CouponImg/"+couponsList.get(i).getId()+".jpg";
        Glide.with(v).load(url).apply(new RequestOptions()
                .signature(new ObjectKey("signature string"))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(couponsImg);




        v.setTag(couponsList.get(i).getId());
        return v;

    }
}
