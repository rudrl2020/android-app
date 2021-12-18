package com.example.project1;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class NoticeDetailActivity extends AppCompatActivity {

    ImageView noticeImg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticedetail);


        TextView idx = (TextView) findViewById(R.id.idx);
        TextView noticeTitle = (TextView) findViewById(R.id.noticeTitle);
        TextView noticeKinds = (TextView) findViewById(R.id.noticeKinds);
        TextView writeDate = (TextView) findViewById(R.id.writeDate);
        noticeImg = findViewById(R.id.noticeImg);


        Intent intent = getIntent(); //보내온 Intent를 얻는다
        idx.setText((intent.getStringExtra("idx")));
        noticeTitle.setText((intent.getStringExtra("noticeTitle")));
        noticeKinds.setText((intent.getStringExtra("noticeKinds")));
        writeDate.setText((intent.getStringExtra("writeDate")));

        String img = idx.getText().toString();


        new DownloadFilesTask().execute("http://175.121.166.150:8000/capston/noticeBoard/noticeImage/"+img+".jpg");
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
            noticeImg.setImageBitmap(result);
        }
    }
}


