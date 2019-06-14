package com.example.isafe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class profileAdapter extends RecyclerView.Adapter<profileAdapter.ViewHolder> {

    profileM[] list;
    Context context;
    private LayoutInflater inflater;


    // RecyclerView recyclerView
    public profileAdapter(Context ctx, profileM[] list)  {
        inflater = LayoutInflater.from(ctx);
        this.list = list;

    }


    @Override
    public profileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItem= layoutInflater.inflate(R.layout.horizontal_rv, parent, false);

        profileAdapter.ViewHolder viewHolder = new profileAdapter.ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(profileAdapter.ViewHolder holder, int position) {

        profileM m = list[position];

        holder.profile_pic.setImageResource(m.getImage());
        holder.name.setText(m.getName());

    }
    @Override
    public int getItemCount() {


        return list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView profile_pic;
        public TextView name ;


        public ViewHolder(View itemView) {
            super(itemView);

            this.name  = (TextView) itemView.findViewById(R.id.membername);
            this.profile_pic = (ImageView) itemView.findViewById(R.id.recyclerprofile);

        }
    }
}
