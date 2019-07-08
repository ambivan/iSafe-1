package com.solve.isafe.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solve.isafe.Classes.MyListData;
import com.solve.isafe.R;

import java.util.List;

public class Reg_Events extends RecyclerView.Adapter<Reg_Events.ViewHolder> {
    List<MyListData> list;
    Context context;

    // RecyclerView recyclerView;
    public Reg_Events(List<MyListData> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public Reg_Events.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.regg, parent, false);
        Reg_Events.ViewHolder viewHolder2 = new Reg_Events.ViewHolder(listItem);
        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(final Reg_Events.ViewHolder holder, int position) {
        final MyListData myListData = list.get(position);
        holder.title.setText(myListData.getTitle());
        holder.event.setText(myListData.getEvent());
        holder.date.setText(myListData.getDate());
        holder.time.setText(myListData.getTime());
        holder.topic.setText(myListData.getTopic());
        holder.city.setText(myListData.getCity());

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

        public ViewHolder(View itemView) {
            super(itemView);

            this.uni = (ImageView) itemView.findViewById(com.solve.isafe.R.id.uni);
            this.title = (TextView) itemView.findViewById(com.solve.isafe.R.id.title2);
            this.event = (TextView) itemView.findViewById(com.solve.isafe.R.id.event);
            this.date = (TextView) itemView.findViewById(com.solve.isafe.R.id.date);
            this.time = (TextView) itemView.findViewById(com.solve.isafe.R.id.time);
            this.topic = (TextView) itemView.findViewById(com.solve.isafe.R.id.topic);
            this.city = (TextView) itemView.findViewById(com.solve.isafe.R.id.cityname);

        }
    }
}
