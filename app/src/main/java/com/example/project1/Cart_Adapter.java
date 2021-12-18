package com.example.project1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Cart_Adapter extends BaseAdapter {

    private Context context;
    private List<Cart> cart;
    private int sum = 0,tvsum_int;
    private String menuName_return = "";
    private String menuNum_return = "";


    public Cart_Adapter(Context context, List<Cart> cart) {
        this.context = context;
        this.cart = cart;
    }

    @Override
    public int getCount() { return cart.size();}

    public String getMenuName_Adapter(int i) {
        menuName_return = cart.get(i).getMenuName();
        return menuName_return;
    }

    public String getMenuNum_Adapter(int i) {
        menuNum_return = cart.get(i).getMenuNum();
        return menuNum_return;
    }

    public int getSum() {
        for(int i =0; i < getCount(); i++) {
            int price_int = Integer.parseInt(cart.get(i).getPrice());
            int num_int = Integer.parseInt(cart.get(i).getMenuNum());
            int cal_int = price_int * num_int;
            sum = sum + cal_int;
        }
        return sum;
    }

    public String refresh(int a, int b) {
        int result;
        if (a > b) {
            result = a-b;
        } else {
            result = 0;
        }

        return String.valueOf(result);
    }

    @Override
    public Object getItem(int i) {return cart.get(i);}

    @Override
    public long getItemId(int i) {return i;}



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.activity_cart_detail, null);
        TextView tv_sum= (TextView) ((CartActivity)CartActivity.context).findViewById(R.id.tv_sum);
        TextView cart_tv_Sale = (TextView) ((CartActivity)CartActivity.context).findViewById(R.id.tv_Sale);
        TextView cart_tv_total = (TextView) ((CartActivity)CartActivity.context).findViewById(R.id.tv_totalPrice);


        TextView menuName = (TextView) v.findViewById(R.id.cart_tv_menuName);
        TextView price = (TextView) v.findViewById(R.id.cart_tv_price);
        TextView num = (TextView) v.findViewById(R.id.cart_tv_num);
        Button btn_plus = (Button)v.findViewById(R.id.btn_plus);
        Button btn_minus = (Button)v.findViewById(R.id.btn_minus);






        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tvSale_int = Integer.parseInt(cart_tv_Sale.getText().toString());
                String num_cnt = num.getText().toString();
                int menuNum_int = Integer.parseInt(num_cnt) + 1;
                int total = 0;

                num.setText(String.valueOf(menuNum_int));
                cart.get(i).setMenuNum(String.valueOf(menuNum_int));
                sum = sum + Integer.parseInt(cart.get(i).getPrice());
                tv_sum.setText(String.valueOf(sum));

                int tvsum_int = Integer.parseInt(tv_sum.getText().toString());

                total = tvsum_int - tvSale_int;

                if(total <= 0) {
                    total = 0;
                }
                cart_tv_total.setText(String.valueOf(total));
            }
        });
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num_cnt = num.getText().toString(); // 개수tv
                int menuNum_int = Integer.parseInt(num_cnt);
                int total=0;

                if(num_cnt == "0") {
                    cart.get(i).setMenuNum("0");
                }

                if(menuNum_int <= 0) {
                    num.setText("0");
                } else {
                    num.setText(String.valueOf(menuNum_int-1));
                    cart.get(i).setMenuNum(String.valueOf(menuNum_int-1));

                    sum = sum - Integer.parseInt(cart.get(i).getPrice());
                    tv_sum.setText(String.valueOf(sum));
                    tvsum_int = Integer.parseInt(tv_sum.getText().toString());
                    int tvSale_int = Integer.parseInt(cart_tv_Sale.getText().toString());

                    total = sum - tvSale_int;
                    if(total <= 0) {
                        total = 0;
                    }
                }
                cart_tv_total.setText(String.valueOf(total));
            }
        });
        menuName.setText(cart.get(i).getMenuName());
        price.setText(cart.get(i).getPrice());
        num.setText(cart.get(i).getMenuNum());

        return v;
    }
}
