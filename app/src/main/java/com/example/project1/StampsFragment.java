package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class StampsFragment extends Fragment {

    private String userPhone;
    private TextView memberStamps;
    private Button btn_CouponAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_stamps,container,false);

        TextView memberName = (TextView) rootview.findViewById(R.id.memberName);
        memberStamps = (TextView) rootview.findViewById(R.id.memberStamps);
        btn_CouponAdd = (Button)rootview.findViewById(R.id.btnCoupons);


        Intent intent = getActivity().getIntent();
        String userName = intent.getStringExtra("userName");
        userPhone = intent.getStringExtra("userPhone");

        memberName.setText(userName+" 님");



        new GettingPHP().execute();
        return rootview;
    }

    class GettingPHP extends AsyncTask<Void, Void, String> {

        String target; // 우리가 접속할 홈페이지 주소
        Context context;

        @Override
        protected void onPreExecute() {
            target = "http://175.121.166.150:8000/capston/application/Stamps.php"; //해당 웹서버에 접속할 수 있게
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
        public void onPostExecute(String result) {
            try {
                //결과값 result로 받아오기
                JSONObject jsonObject = new JSONObject(result);
                //응답부분처리하기
                //PHP에서 받아온 JSON데이터를 result를 인자로 넣어 JSON오브젝트로 변환
                //response에 각각의 공지사항 리스트가 담김
//                JSONArray jsonArray = jsonObject.getJSONArray("stampsphp"); //response의 jsonObject들을 배열로 저장

                String stamps = jsonObject.getString("stamps");

                memberStamps.setText(stamps+" 개");

                btn_CouponAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success"); // CouponAdd.php의 success가 참인지 거짓인지
                                    if(success) { // 로그인에 성공한 경우
                                        String userStamps = jsonObject.getString("stamps");
                                        Toast.makeText(getActivity().getApplicationContext(), "쿠폰이 지급되었습니다!", Toast.LENGTH_SHORT).show();
                                        memberStamps.setText(userStamps+" 개");
                                    } else { // 로그인에 실패한 경우
                                        Toast.makeText(getActivity().getApplicationContext(), "스탬프가 부족해요..", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getActivity().getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        Log.e("스탬프 수 : ", stamps );
                        Log.e("핸드폰번호 : ", userPhone);

                        CouponAddRequest couponAddRequest = new CouponAddRequest(userPhone, stamps, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                        queue.add(couponAddRequest);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}