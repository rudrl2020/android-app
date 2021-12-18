package com.example.project1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SixthMenuContents extends AppCompatActivity {

    //private로 해당 클래스에 멤버 변수
    private ListView menuListView;
    private MenuListAdapter adapter;
    private List<Menu> menuList;
    TextView textView;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sixthmenucontents);


        textView = (TextView) findViewById(R.id.textView);
        menuListView = (ListView) findViewById(R.id.menuListView);
        menuList = new ArrayList<Menu>(); //new ArrayList로 초기화
        adapter = new MenuListAdapter(getApplicationContext(), menuList);
        //adapter에  noticeList 내역들이 차례대로 들어감
        new BackgroundTask().execute();

        menuListView.setAdapter(adapter);
        //리스트뷰에 해당 adapter이 매칭이 됨으로써
        // adapter에 들어가있는 모든 내용이 뷰 형태로 리스트뷰에 들어가서 보여짐



    }


    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target; // 우리가 접속할 홈페이지 주소

        @Override
        protected void onPreExecute() {
            target = "http://175.121.166.150:8000/capston/application/teaList.php"; //해당 웹서버에 접속할 수 있게
        }

        //실질적 데이터를 얻어 올 수 있는 코드
        @Override
        protected String doInBackground(Void... voids) {
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
                while ((temp = bufferedReader.readLine()) != null) {
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
                JSONObject jsonObject = new JSONObject(result);//응답부분처리하기
                //response에 각각의 공지사항 리스트가 담김

                JSONArray jsonArray = jsonObject.getJSONArray("menutea");
                int count = 0;
                String menuName, menuKinds, price, imageName;
                while (count < jsonArray.length())//전체 크기보다 작을 때 까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    menuName = object.getString("menuName");
                    menuKinds = object.getString("menuKinds");
                    price = object.getString("price");
                    imageName = object.getString("imageName");
                    //하나의 객체 만들어주기, 하나의 공지사항에 대한 객체 생성
                    //하나의 생성자를 이용해서 객체 할당
                    Menu menu = new Menu(menuName, menuKinds, price, imageName);//객체생성
                    menuList.add(menu);
                    adapter.notifyDataSetChanged();
                    count++;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


