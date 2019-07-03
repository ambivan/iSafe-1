package com.solve.isafe.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.solve.isafe.Classes.MyListData;
import com.solve.isafe.MarkAttendanceMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyListAdapter2 extends RecyclerView.Adapter<MyListAdapter2.ViewHolder> {
    List<MyListData> list;
    static Context context;
    public static int i;

    public static String university;
    public static String city1;

    ArrayList<MyListData> list1;




    // RecyclerView recyclerView;
    public MyListAdapter2(List<MyListData> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public MyListAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(com.solve.isafe.R.layout.attendance_item, parent, false);
        MyListAdapter2.ViewHolder viewHolder2 = new MyListAdapter2.ViewHolder(listItem);
        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(final MyListAdapter2.ViewHolder holder, int position) {
        final MyListData myListData = list.get(position);
        holder.title.setText(myListData.getTitle());
        holder.event.setText(myListData.getEvent());
        holder.date.setText(myListData.getDate());
        holder.time.setText(myListData.getTime());
        holder.topic.setText(myListData.getTopic());
        holder.city.setText(myListData.getCity());

        holder.markatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int b = holder.getPosition();

                MyListData mll = list.get(b);

                university = mll.getTitle();
                city1 = mll.getCity();

                //if you need position, just use recycleViewHolder.getAdapterPosition();
                Intent intent = new Intent(v.getContext(), MarkAttendanceMap.class);
                context.startActivity(intent);

            }
        });



    }


    @Override
    public int getItemCount() {
        int arr = 0;

        try {
            if (list.size() == 0) {
                arr = 0;
            } else {
                arr = list.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arr;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView uni;
        public TextView title, event, date, time, topic, city;

        Button markatt;
        ImageView like, send, msg;


        public ViewHolder(View itemView) {
            super(itemView);

            this.uni = (ImageView) itemView.findViewById(com.solve.isafe.R.id.uni);

            this.title = (TextView) itemView.findViewById(com.solve.isafe.R.id.title2);
            this.event = (TextView) itemView.findViewById(com.solve.isafe.R.id.event);
            this.date = (TextView) itemView.findViewById(com.solve.isafe.R.id.date);
            this.time = (TextView) itemView.findViewById(com.solve.isafe.R.id.time);
            this.topic = (TextView) itemView.findViewById(com.solve.isafe.R.id.topic);
            this.city = (TextView) itemView.findViewById(com.solve.isafe.R.id.cityname);

            this.markatt = (Button) itemView.findViewById(com.solve.isafe.R.id.markatt);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = new Date();

        }
    }
}