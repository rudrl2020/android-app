package com.example.project1;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SelectOrderListAdapter extends BaseAdapter { //baseAdapter 상속

    private Context context;
    private List<OrderList> orderList;

    //생성자
    public SelectOrderListAdapter(Context context, List<OrderList> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    //메소드 생성
    @Override
    public int getCount() {

        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.activity_orderlist_content, null); //해당하는 디자인을 사용할 수 있게

        TextView idx = (TextView) v.findViewById(R.id.tv_Idx); //(TextView)를 객체로 만들어 줄 수 있게
        TextView menuName = (TextView) v.findViewById(R.id.tv_Menu);
        TextView date = (TextView) v.findViewById(R.id.tv_Date);

        if(orderList.get(i).getCounts().equals("0")) {
            idx.setText(orderList.get(i).getIndex());
            menuName.setText(orderList.get(i).getMenuName());
            date.setText(orderList.get(i).getDate());
        } else {
            idx.setText(orderList.get(i).getIndex());
            menuName.setText(orderList.get(i).getMenuName()+" 외 "+ orderList.get(i).getCounts()+"개");
            date.setText(orderList.get(i).getDate());
        }






        //최근 V값을 반환
        v.setTag(orderList.get(i).getIndex());
        return v;

    }
}