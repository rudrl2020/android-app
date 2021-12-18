package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateActivity extends AppCompatActivity {

    private String userPhone, post_Password, post_userName;
    private EditText et_userName, et_Password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        Intent intent = getIntent();
        userPhone = intent.getStringExtra("userPhone");



        Button btn_Update = findViewById(R.id.btn_Update);
        et_userName = findViewById(R.id.et_Name);
        et_Password = findViewById(R.id.et_Password);



        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                post_Password = et_Password.getText().toString();
                post_userName = et_userName.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.v("json", "성공");
                            boolean success = jsonObject.getBoolean("success");
                            if(success) { // 로그인에 성공한 경우

                                Toast.makeText(getApplicationContext(), "변경을 완료했습니다!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                                startActivity(intent);
//                            return;
                            } else { // 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(), "변경을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                UpdateRequest updateRequest = new UpdateRequest(userPhone, post_Password, post_userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(updateRequest);
            }
        });
        new GettingPHP().execute();
    }

    class GettingPHP extends AsyncTask<Void, Void, String> {

        String target; // 우리가 접속할 홈페이지 주소
        Context context;

        @Override
        protected void onPreExecute() {
            target = "http://175.121.166.150:8000/capston/application/SelectName.php"; //해당 웹서버에 접속할 수 있게
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


                String name = jsonObject.getString("name");
                Log.v("json", name);
                et_userName.setText(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
