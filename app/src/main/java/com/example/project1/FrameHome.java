package com.example.project1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FrameHome extends Fragment {
    private View view;
    private ImageView homenoticeimg;
    private TextView test;
    private String TAG = "프래그먼트";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.framehome, container, false);

        Button logbtn=(Button)view.findViewById(R.id.logbtn);
        Button btn_cart=(Button)view.findViewById(R.id.btn_cart);
        Button introbtn=(Button)view.findViewById(R.id.introbtn);
        Button menubtn=(Button)view.findViewById(R.id.menubtn);
        Button noticebtn = (Button)view.findViewById(R.id.noticebtn);
        homenoticeimg =  view.findViewById(R.id.homenoticeimg);
        test = view.findViewById(R.id.test);

        Intent intent = getActivity().getIntent();
        String userPhone = intent.getStringExtra("userPhone");
        String userName = intent.getStringExtra("userName");

        // 로그인이 된 상태면 로그인 / 회원가입이 보이지 않도록
        if(userPhone != null) {
            logbtn.setVisibility(View.GONE);
        }else{
            btn_cart.setVisibility(View.GONE);
        }





        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(),CartActivity.class);
                intent.putExtra("userPhone", userPhone);
                startActivity(intent);
            }
        });
        introbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(),IntroActivity.class);
                intent.putExtra("userPhone", userPhone);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(),MenuActivity.class);
                intent.putExtra("userPhone", userPhone);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
        noticebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NoticeList.class);
                startActivity(intent);
            }
        });
        new BackgroundTask().execute();


        return view;
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        //<doInBackground의 매개변수자료형, onProgressUpdate 매개변수자료형, onPostExecute의 매개변수 자료형>

        String target; // 우리가 접속할 홈페이지 주소

        @Override
        protected void onPreExecute() {
            target = "http://175.121.166.150:8000/capston/application/Select_notice.php";
        }
        //실질적 데이터를 얻어 올 수 있는 코드
        @Override
        protected String doInBackground(Void... voids) { //백그라운드 스레드에서 호출된다. 핵심적인작업
            try {
                URL url = new URL(target); //해당 서버에 접속 할 수 있도록 커넥팅
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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

                String idx = jsonObject.getString("noticephp");


                new DownloadFilesTask().execute("http://175.121.166.150:8000/capston/noticeBoard/noticeImage/"+idx+".jpg");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private class DownloadFilesTask extends AsyncTask<String,Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bmp = null;
            try {
                String img_url = strings[0]; //url of the image
                URL url = new URL(img_url);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            // doInBackground 에서 받아온 total 값 사용 장소
            homenoticeimg.setImageBitmap(result);
        }
    }
}