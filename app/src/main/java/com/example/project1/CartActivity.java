package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private String userPhone, couponIdx;
    private ListView lv_cart;
    private Cart_Adapter adapter;
    private List<Cart> cartList;
    private int salePrice;
    private Button btn_submit, btn_clear;
    private TextView tv_sum, tv_Sale, tv_totalPrice, cart_tv_idx, cart_tv_couponId;
    public static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        // Cart_Adapter에서 CartActivity로 접근하기 위해 context 사용
        context = this;

        tv_sum = (TextView)findViewById(R.id.tv_sum);
        btn_clear = (Button) findViewById(R.id.btn_CartClear);
        tv_Sale = (TextView) findViewById(R.id.tv_Sale);
        cart_tv_idx = (TextView) findViewById(R.id.cart_tv_idx);
        cart_tv_couponId = (TextView) findViewById(R.id.cart_tv_couponId);
        tv_totalPrice = (TextView) findViewById(R.id.tv_totalPrice);
        lv_cart = (ListView)findViewById(R.id.lv_cart);

        btn_submit = (Button)findViewById(R.id.btn_submit);
        Button btn_coupon = (Button)findViewById(R.id.btn_coupon);

        if(tv_Sale.getText().toString().equals("") ||tv_Sale.getText().toString() == null) {
            tv_Sale.setText("0");
        }


        cartList = new ArrayList<Cart>();
        adapter = new Cart_Adapter(getApplicationContext(), cartList);
        lv_cart.setAdapter(adapter);




        Intent intent = getIntent();
        userPhone = intent.getExtras().getString("userPhone");






        btn_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),CouponFindActivity.class);
                intent.putExtra("userPhone", userPhone);
                startActivity(intent);
            }
        });

        new GettingPHP().execute();
    }




    class GettingPHP extends AsyncTask<Void, Void, String> {

        String target; // 우리가 접속할 홈페이지 주소
        Context context;

        @Override
        protected void onPreExecute() {
            target = "http://175.121.166.150:8000/capston/application/Select_Cart.php"; //해당 웹서버에 접속할 수 있게
        }
        //실질적 데이터를 얻어 올 수 있는 코드
        @Override
        protected String doInBackground(Void... voids) {
            try {

                Log.e("캐치", "성공");
                String postData = "userPhone=" + userPhone;

                Log.e("userPhone", userPhone);
                URL url = new URL(target); //해당 서버에 접속 할 수 있도록 커넥팅
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                //해당 InPutStream에 있는 내용들을 버퍼에 담아서 읽을 수 있도록 하기
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                //이 템프에 하나씩 읽어와서 문자열 형태로 저장할 수 있게
                StringBuilder stringBuilder = new StringBuilder();
                //템프에다가 하나씩 넣는다. 버퍼에서 가져온 값을 한 줄씩 읽으면서 템프에다가 하나씩 넣는다.
                while ((temp = bufferedReader.readLine()) != null)
                {
                    //stringBuilder에 템프에 한줄씩 추가하면서 넣기
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        //해당 결과를 처리할 수 있음
        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }
        //해당결과를 처리할 수 있음
        @Override
        public void onPostExecute(String result) {
            try {
                //결과값 result로 받아오기
                JSONObject jsonObject = new JSONObject(result);
                //응답부분처리하기
                //PHP에서 받아온 JSON데이터를 result를 인자로 넣어 JSON오브젝트로 변환
                JSONArray jsonArray = jsonObject.getJSONArray("response"); //response의 jsonObject들을 배열로 저장

                int cnt = 0;
                String menuName, price, menuNum;
                while (cnt <jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(cnt);
                    menuName = object.getString("menuName");
                    price = object.getString("price");
                    menuNum = object.getString("num");

                    Log.e("menuName", menuName);
                    Log.e("price", price);
                    Log.e("menuNum", menuNum);

                    Cart cart = new Cart(menuName, price, menuNum);
                    cartList.add(cart);
                    adapter.notifyDataSetChanged();
                    cnt++;

                }

                btn_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        };
                        CartDeleteRequest cartDeleteRequest = new CartDeleteRequest(userPhone, cart_tv_idx.getText().toString(),responseListener);
                        RequestQueue queue_del = Volley.newRequestQueue(CartActivity.this);
                        queue_del.add(cartDeleteRequest);

                        cartList.clear();
                        adapter.notifyDataSetChanged();
                        tv_Sale.setText("0");
                        tv_sum.setText("0");
                        tv_totalPrice.setText("0");
                        Toast.makeText(getApplicationContext(), "장바구니를 비웠습니다.", Toast.LENGTH_SHORT).show();

                    }
                });



                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        };

                        if(cart_tv_couponId.getText().toString().equals("") || cart_tv_couponId.getText().toString() == null) {
                            cart_tv_couponId.setText("null");
                        }
                        if(adapter.getCount() == 0) {
                            Toast.makeText(getApplicationContext(), "장바구니에 담긴 메뉴가 없습니다..", Toast.LENGTH_SHORT).show();
                        } else {
                            int cnt = 0;
                            for (int i = 0; i < adapter.getCount(); i++) {
                                // 서버로 Volley를 이용해서 요청을 함
                                if (adapter.getMenuNum_Adapter(i) != "0" && !adapter.getMenuNum_Adapter(i).equals("0")) {
                                    PayRequest payRequest = new PayRequest(userPhone, adapter.getMenuName_Adapter(i), adapter.getMenuNum_Adapter(i), cart_tv_couponId.getText().toString(), responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(CartActivity.this);
                                    queue.add(payRequest);
                                    cnt++;
                                    if (i == adapter.getCount() - 1) {
                                        CartDeleteRequest cartDeleteRequest = new CartDeleteRequest(userPhone, cart_tv_idx.getText().toString(), responseListener);
                                        RequestQueue queue_del = Volley.newRequestQueue(CartActivity.this);
                                        queue_del.add(cartDeleteRequest);
                                    }
                                }

                                cartList.clear();
                                adapter.notifyDataSetChanged();
                                tv_Sale.setText("0");
                                tv_sum.setText("0");
                                tv_totalPrice.setText("0");
                                if(cnt > 0) {
                                    Toast.makeText(getApplicationContext(), "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "주문할 메뉴가 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });



                tv_sum.setText(String.valueOf(adapter.getSum()));
                if(tv_Sale.getText().toString() == "") {
                    salePrice = 0;
                } else {
                    salePrice = Integer.parseInt(tv_Sale.getText().toString());
                }

                int tv_sum_int = Integer.parseInt(tv_sum.getText().toString());
                int tv_Sale_int =Integer.parseInt(tv_Sale.getText().toString());
                int total = tv_sum_int - tv_Sale_int;

                if(total <= 0) {
                    total = 0;
                }

                tv_totalPrice.setText(String.valueOf(total));


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("onPost", "실패");
            }
        }
    }
}