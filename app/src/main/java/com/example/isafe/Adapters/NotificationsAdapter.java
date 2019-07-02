package com.example.isafe.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isafe.Activities.HomePageActivity;
import com.example.isafe.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder> {

    private List<Long> list;

    private Context context;

    public NotificationsAdapter(List<Long> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.notificationslist, viewGroup, false);
        NotificationsAdapter.MyViewHolder viewHolder2 = new NotificationsAdapter.MyViewHolder(listItem);
        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Long mylist = list.get(i);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        String dateString = formatter.format(mylist);
        myViewHolder.date.setText(dateString);

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date;

        CardView click;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.date = itemView.findViewById(R.id.times);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HomePageActivity.frag = 1;
                    context.startActivity(new Intent(context.getApplicationContext(), HomePageActivity.class));

                }
            });
        }
    }
}
