package com.example.isafe.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isafe.Classes.Meeting;
import com.example.isafe.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class MeetingsAdapter extends RecyclerView.Adapter<MeetingsAdapter.ViewHolder> {


    List<Meeting> list;
    Context context;

    public MeetingsAdapter(List<Meeting> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public MeetingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.meetings_list, parent, false);
        ViewHolder viewHolder2 = new ViewHolder(listItem);
        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(MeetingsAdapter.ViewHolder holder, int position) {
        final Meeting myListData = list.get(position);

        holder.agenda.setText(myListData.getAgenda());
        holder.minutes.setText(myListData.getMinutes());
        holder.tasks.setText(myListData.getTasks());

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String dateString = formatter.format(myListData.getDate());
        holder.date.setText(dateString);


    }


    @Override
    public int getItemCount() {
        int arr = 0;

        try{
            if(list.size()==0){
                arr = 0;
            }
            else{
                arr=list.size();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arr;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView date, tasks,agenda,minutes ;


        public ViewHolder(View itemView) {
            super(itemView);

            this.minutes = (TextView) itemView.findViewById(R.id.minutesofmeet);
            this.agenda = (TextView) itemView.findViewById(R.id.agenda2);
            this.date = (TextView) itemView.findViewById(R.id.date2);
            this.tasks = (TextView) itemView.findViewById(R.id.tasksgiven);

        }
    }
}