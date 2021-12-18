package com.example.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class MenuListAdapter extends BaseAdapter { //baseAdapter 상속

    private Context context;
    private List<Menu> menuList; //notice가 들어가는 리스트


    //생성자
    public MenuListAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    //메소드 생성
    @Override
    public int getCount() {

        return menuList.size(); //noticeList에 size로 반환
    }

    @Override
    public Object getItem(int i) {
        return menuList.get(i); //noticeList에 해당위치(get(i))에 있는 노티스를 반환
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.menudetail, null); //해당하는 디자인을 사용할 수 있게
        TextView menuName = (TextView) v.findViewById(R.id.menuName); //(TextView)를 객체로 만들어 줄 수 있게
        TextView menuKinds = (TextView) v.findViewById(R.id.menuKinds);
        TextView price = (TextView) v.findViewById(R.id.price);
        TextView imageName = (TextView) v.findViewById(R.id.imageName);
        ImageView menuimg = (ImageView) v.findViewById(R.id.menuimg);


        //noticeText를 현재 리스트에 있는 값으로 넣어 줄 수 있도록
        menuName.setText(menuList.get(i).getMenuName());
        menuKinds.setText(menuList.get(i).getMenuKinds());
        price.setText(menuList.get(i).getPrice());
        imageName.setText(menuList.get(i).getImageName());
        String menu = price.getText().toString();
        price.setText(menu+"원");

        String url = "http://175.121.166.150:8000/capston/menu/menuImage/"+menuList.get(i).getImageName()+".jpg";
        Glide.with(v).load(url).apply(new RequestOptions()
                .signature(new ObjectKey("signature string"))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
        )
                .into(menuimg);




        //tag를 붙여줌 noticeList.get(i)에 있는 getNotice()
        //최근 V값을 반환
        v.setTag(menuList.get(i).getMenuName());
        return v;




    }




}

