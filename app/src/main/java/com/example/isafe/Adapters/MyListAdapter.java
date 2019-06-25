package com.example.isafe.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isafe.Classes.MyListData;
import com.example.isafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    List<MyListData> list;
    Context context;

    ArrayList<MyListData> list1;

    static int mla;
    int ml;

    int position;

    // RecyclerView recyclerView;
    public MyListAdapter(List<MyListData> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItem = layoutInflater.inflate(com.example.isafe.R.layout.list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(listItem);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final MyListData myListData = list.get(position);
        holder.title.setText(myListData.getTitle());
        holder.event.setText(myListData.getEvent());
        holder.date.setText(myListData.getDate());
        holder.time.setText(myListData.getTime());
        holder.topic.setText(myListData.getTopic());
        holder.city.setText(myListData.getCity());

        if (myListData.getIs_liked().equals("0")){
            holder.like.setImageResource(R.drawable.heart);
        }else{
            holder.like.setImageResource(R.drawable.redheart);
        }

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyListData m = list.get(ml);
                System.out.println(m);
                if (m.getIs_liked().equals("0")){
                    m.setIs_liked("1");
                    holder.like.setImageResource(R.drawable.redheart);
                }else{
                    m.setIs_liked("0");
                    holder.like.setImageResource(R.drawable.heart);
                }
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

        }

        System.out.println("size" + arr);

        return arr;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView uni;
        public TextView title, event, date, time, topic, city;

        Button register;
        ImageView like, send, msg;


        public ViewHolder(View itemView) {
            super(itemView);

            this.uni = (ImageView) itemView.findViewById(com.example.isafe.R.id.uni);

            this.title = (TextView) itemView.findViewById(com.example.isafe.R.id.title2);
            this.event = (TextView) itemView.findViewById(com.example.isafe.R.id.event);
            this.date = (TextView) itemView.findViewById(com.example.isafe.R.id.date);
            this.time = (TextView) itemView.findViewById(com.example.isafe.R.id.time);
            this.topic = (TextView) itemView.findViewById(com.example.isafe.R.id.topic);
            this.city = (TextView) itemView.findViewById(com.example.isafe.R.id.cityname);

            this.register = (Button) itemView.findViewById(com.example.isafe.R.id.register);

            this.like = (ImageView) itemView.findViewById(com.example.isafe.R.id.heart);
            this.send = (ImageView) itemView.findViewById(com.example.isafe.R.id.send);
            this.msg = (ImageView) itemView.findViewById(com.example.isafe.R.id.chatt);

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mla = getPosition();

                    MyListData mylist = list.get(mla);

                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Registered Events")
                            .push()
                            .setValue(new MyListData(mylist.getTitle(), mylist.getCity(),
                                    mylist.getEvent(), mylist.getDate(), mylist.getTime(), mylist.getTopic(), "0"));

                    register.setEnabled(false);
                    register.setBackgroundResource(R.drawable.report);
                    register.setText("Registered");
                }
            });

            send.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    int m = getPosition();
                    Intent a = new Intent(Intent.ACTION_SEND);

                    MyListData mll = list.get(m);

                    final String event = mll.getTitle() + mll.getCity() + " is" + mll.getEvent() + " " + mll.getDate() + ".\n" + mll.getTime() + "\n" + mll.getTopic();

                    a.setType("text/plain");
                    String shareBody = "Hey!!" + "\n" + event + "\n" + "Would You like to be a part of this?";
                    String shareSub = "iSAFE";
                    a.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                    a.putExtra(Intent.EXTRA_TEXT, shareBody);
                    context.startActivity(Intent.createChooser(a, "Share Using"));

                }
            });


            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ml = getPosition();
                    System.out.println(ml);
                    MyListData m = list.get(ml);
                    System.out.println(m);
                    if (m.getIs_liked().equals("0")){
                        m.setIs_liked("1");
                        like.setImageResource(R.drawable.redheart);
                    }else{
                        m.setIs_liked("0");
                        like.setImageResource(R.drawable.heart);
                    }
                }
            });
        }
    }
}