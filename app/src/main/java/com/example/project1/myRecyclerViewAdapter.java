package com.example.project1;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class myRecyclerViewAdapter extends RecyclerView.Adapter {

    String TAG = "RecyclerViewAdapter";
    ArrayList<DataModel> dataModels;
    Context context;


    public myRecyclerViewAdapter(Context context, ArrayList<DataModel> dataModels) {
        this.dataModels = dataModels;
        this.context = context;

    }

    @Override
    public int getItemCount() {

        return dataModels.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //adapter에서 아이템의 레이아웃을 넘겨줌
        Log.d(TAG, "onCreateViewHolder");


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menuitemview, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { //실제 화면에 데이터와 레이아웃을 연결
        Log.d(TAG, "onBindViewHolder");

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.textView.setText(dataModels.get(position).getTitle());
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(context, MenuContents.class);
                        context.startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(context, SubMenuContents.class);
                        context.startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(context, ThirdMenuContents.class);
                        context.startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(context, ForthMenuContents.class);
                        context.startActivity(intent4);
                        break;
                    case 4:
                        Intent intent5 = new Intent(context, FifthMenuContents.class);
                        context.startActivity(intent5);
                        break;
                    case 5:
                        Intent intent6 = new Intent(context, SixthMenuContents.class);
                        context.startActivity(intent6);
                        break;
                    case 6:
                        Intent intent7 = new Intent(context, SeventhMenuContents.class);
                        context.startActivity(intent7);
                        break;
                }
            }
        });
        myViewHolder.imageView.setImageResource(dataModels.get(position).image_path);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
            imageView = itemView.findViewById(R.id.imageview);



        }
    }
}




