package com.example.project1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragOrder extends Fragment {
    private View view;

    private ListView lv_orderMenu;
    private List<Order_Menu> order_menuList;
    private Order_Menu_Adapter adapter;
    private String userPhone;

    private String TAG = "프래그먼트";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.activity_order_menu, container, false);


        Intent intent = getActivity().getIntent();
        String userPhone = intent.getStringExtra("userPhone");


        TextView tv = (TextView) view.findViewById(R.id.tv_title);
        Button btn_add = (Button) view.findViewById(R.id.btn_addCart);
        lv_orderMenu = (ListView) view.findViewById(R.id.lv_orderMenu);
        order_menuList = new ArrayList<Order_Menu>();
        adapter = new Order_Menu_Adapter((getActivity().getApplicationContext()), order_menuList, userPhone);

        lv_orderMenu.setAdapter(adapter);


        new BackgroundTask().execute();

        return view;
    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target; // 우리가 접속할 홈페이지 주소

        @Override
        protected void onPreExecute() {
            target = "http://175.121.166.150:8000/capston/application/Select_Menu.php"; //해당 웹서버에 접속할 수 있게
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

                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String menuName, price, imageName;
                while (count < jsonArray.length())//전체 크기보다 작을 때 까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    menuName = object.getString("menuName");
                    price = object.getString("price");
                    imageName = object.getString("imageName");
                    //하나의 객체 만들어주기, 하나의 공지사항에 대한 객체 생성
                    //하나의 생성자를 이용해서 객체 할당
                    Order_Menu order_menu = new Order_Menu(menuName, price, imageName);//객체생성
                    order_menuList.add(order_menu);
                    adapter.notifyDataSetChanged();
                    count++;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}