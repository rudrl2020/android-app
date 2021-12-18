package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


public class CouponFindActivity extends AppCompatActivity {

    private String userPhone;
    private ListView lv_couponfind;
    private CouponFindAdapter adapter;
    private List<CouponFind> couponList;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_find);
        context = this;

        lv_couponfind = (ListView) findViewById(R.id.lv_couponfind);
        couponList = new ArrayList<CouponFind>();
        adapter = new CouponFindAdapter(getApplicationContext(), couponList);
        lv_couponfind.setAdapter(adapter);

        lv_couponfind.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_Sum = (TextView) ((CartActivity)CartActivity.context).findViewById(R.id.tv_sum);
                TextView tv_Sale = (TextView) ((CartActivity)CartActivity.context).findViewById(R.id.tv_Sale);
                TextView tv_totalPrice = (TextView) ((CartActivity)CartActivity.context).findViewById(R.id.tv_totalPrice);
                TextView tv_idx = (TextView) ((CartActivity)CartActivity.context).findViewById(R.id.cart_tv_idx);
                TextView tv_couponId = (TextView) ((CartActivity)CartActivity.context).findViewById(R.id.cart_tv_couponId);
                tv_Sale.setText(couponList.get(position).getSalePrice());
                tv_idx.setText(couponList.get(position).getIdx());
                tv_couponId.setText(couponList.get(position).getCoupondId());

                int sum_int = Integer.parseInt(tv_Sum.getText().toString());
                int sale_int = Integer.parseInt(tv_Sale.getText().toString());
                int total_int = sum_int - sale_int;

                if(total_int <= 0) {
                    total_int = 0;
                }

                tv_totalPrice.setText(String.valueOf(total_int));
                onBackPressed();
            }
        });



        Intent intent = getIntent();
        userPhone = intent.getExtras().getString("userPhone");

        new GettingPHP().execute();
    }

    class GettingPHP extends AsyncTask<Void, Void, String> {

        String target; // 우리가 접속할 홈페이지 주소
        Context context;

        @Override
        protected void onPreExecute() {
            target = "http://175.121.166.150:8000/capston/application/CouponFind.php"; //해당 웹서버에 접속할 수 있게
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
                String idx, couponId, salePrice;
                while (cnt <jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(cnt);
                    idx = object.getString("idx");
                    couponId = object.getString("couponId");
                    salePrice = object.getString("salePrice");

                    CouponFind couponFind = new CouponFind(idx, couponId, salePrice);
                    couponList.add(couponFind);
                    adapter.notifyDataSetChanged();
                    cnt++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("onPost", "실패");
            }
        }
    }
}