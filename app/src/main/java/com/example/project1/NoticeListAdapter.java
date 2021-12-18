package com.example.project1;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter { //baseAdapter 상속

    private Context context;
    private List<Notice> noticeList; //notice가 들어가는 리스트

    //생성자
    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    //메소드 생성
    @Override
    public int getCount() {

        return noticeList.size(); //noticeList에 size로 반환
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i); //noticeList에 해당위치(get(i))에 있는 노티스를 반환
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.noticecontents, null); //해당하는 디자인을 사용할 수 있게
        TextView idx = (TextView) v.findViewById(R.id.idx); //(TextView)를 객체로 만들어 줄 수 있게
        TextView noticeTitle = (TextView) v.findViewById(R.id.noticeTitle);
        TextView noticeKinds = (TextView) v.findViewById(R.id.noticeKinds);
        TextView writeDate = (TextView) v.findViewById(R.id.writeDate);


        //noticeText를 현재 리스트에 있는 값으로 넣어 줄 수 있도록
        idx.setText(noticeList.get(i).getIndex());
        noticeTitle.setText(noticeList.get(i).getTitle());
        noticeKinds.setText(noticeList.get(i).getKinds());
        writeDate.setText(noticeList.get(i).getDate());
        String notice = noticeKinds.getText().toString();
        noticeKinds.setText("["+notice+"]");


        //tag를 붙여줌 noticeList.get(i)에 있는 getNotice()
        //최근 V값을 반환
        v.setTag(noticeList.get(i).getIndex());
        return v;

    }
}
