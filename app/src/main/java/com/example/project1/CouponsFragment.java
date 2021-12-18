package com.example.project1;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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


public class CouponsFragment extends Fragment {


    private TextView memberCoupons;
    private String userPhone;
    private CouponsListAdapter adapter;
    private List<DataCoupons> couponsList;

    public CouponsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupons,container,false);

        memberCoupons = (TextView) view.findViewById(R.id.memberCoupons);




        ListView listView= view.findViewById(R.id.couponsListView);

        Intent intent = getActivity().getIntent();
        userPhone = intent.getStringExtra("userPhone");




        couponsList = new ArrayList<DataCoupons>();
        adapter = new CouponsListAdapter(getActivity().getApplicationContext(), couponsList);
        listView.setAdapter(adapter);

        new  GettingPHP().execute();


        return view;
    }


    class GettingPHP extends AsyncTask<Void, Void, String> {

        String target; // 우리가 접속할 홈페이지 주소
        Context context;

        @Override
        protected void onPreExecute() {
            target = "http://175.121.166.150:8000/capston/application/CouponsSelect.php"; //해당 웹서버에 접속할 수 있게
        }
        //실질적 데이터를 얻어 올 수 있는 코드
        @Override
        protected String doInBackground(Void... voids) {
            try {

                String postData = "userPhone=" + userPhone;
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
        public void onPostExecute(String result) {//결과값 result로 받아오기
            try {
                JSONObject jsonObject = new JSONObject(result);
                //응답부분처리하기
                //result를 인자로 넣어 jsonObject를 생성
                //response에 각각의 공지사항 리스트가 담김

                JSONArray jsonArray = jsonObject.getJSONArray("couponId"); //response의 jsonObject들을 배열로 저장
                int count = 0;
                String id ;
                while (count < jsonArray.length())//전체 크기보다 작을 때 까지 jsonObject에 담긴 두개의 jsonObject를 jsonArray를 통해 하나씩 호출
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    id = object.getString("couponId");
                    DataCoupons couponsData = new DataCoupons(id);
                    couponsList.add(couponsData);
                    adapter.notifyDataSetChanged();
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}