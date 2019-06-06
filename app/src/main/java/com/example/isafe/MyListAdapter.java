package com.example.isafe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private MyListData[] listdata;

    // RecyclerView recyclerView;
    public MyListAdapter(MyListData[] listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyListData myListData = listdata[position];
        holder.title.setText(listdata[position].getTitle());
        holder.uni.setImageResource(listdata[position].getImage());

    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView uni;
        public TextView title, event, date, time, topic ;

        Button register;
        ImageView like, send, msg;


        public ViewHolder(View itemView) {
            super(itemView);

            this.uni = (ImageView) itemView.findViewById(R.id.uni);

            this.title = (TextView) itemView.findViewById(R.id.title2);
            this.event = (TextView) itemView.findViewById(R.id.event);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.time = (TextView) itemView.findViewById(R.id.time);
            this.topic = (TextView) itemView.findViewById(R.id.topic);

            this.register = (Button) itemView.findViewById(R.id.register);

            this.like = (ImageView) itemView.findViewById(R.id.heart);
            this.send = (ImageView) itemView.findViewById(R.id.send);
            this.msg = (ImageView) itemView.findViewById(R.id.chatt);



        }
    }
}
