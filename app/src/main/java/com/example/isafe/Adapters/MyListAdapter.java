package com.example.isafe.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    List<MyListData> list;
    Context context;

    ArrayList<MyListData> list1;

    static int mla;

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

//        if (myListData.getIs_liked().equals("1")) {
//            holder.like.setImageResource(R.drawable.redheart);
//        }

        holder.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyListData mylist = list.get(mla);

                FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Registered Events")
                        .push()
                        .setValue(new MyListData(mylist.getTitle(), mylist.getCity(),
                                mylist.getEvent(), mylist.getDate(), mylist.getTime(), mylist.getTopic(), "0"));

                holder.register.setEnabled(false);
                holder.register.setBackgroundResource(com.example.isafe.R.drawable.reportbuttonbg);
                holder.register.setText("Registered");
            }
        });


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myListData.setIs_liked("1");
                if (myListData.getIs_liked().equals("1")) {
                    holder.like.setImageResource(R.drawable.redheart);
                }

                FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Liked Events").push().setValue(new MyListData(myListData.getTitle(), myListData.getCity(),
                        myListData.getEvent(), myListData.getDate(), myListData.getTime(), myListData.getTopic(), myListData.getIs_liked()));

            }
        });


        DatabaseReference d = FirebaseDatabase.getInstance().getReference()
                .child("Events");

        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                list1 = new ArrayList<MyListData>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    System.out.println(child.getKey());

                    System.out.println("list cbdsj" + child.getValue());

                    MyListData events = child.getValue(MyListData.class);

                    MyListData eventlist = new MyListData();

                    String title = events.getTitle();
                    String event = events.getEvent();
                    String city = events.getCity();
                    String date = events.getDate();
                    String time = events.getTime();
                    String topic = events.getTopic();
                    String is_liked = events.getIs_liked();

                    if (is_liked.equals("0")) {
                        holder.like.setImageResource(R.drawable.heart);
                    } else {
                        holder.like.setImageResource(R.drawable.redheart);
                    }

                    eventlist.setTitle(title);
                    eventlist.setCity(city);
                    eventlist.setEvent(event);
                    eventlist.setDate(date);
                    eventlist.setTime(time);
                    eventlist.setTopic(topic);

                    list.add(eventlist);

                    System.out.println(list);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

                    System.out.println(getPosition());
                    mla = getPosition();




                }
            });

//      like.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              int m = getPosition();
//
//              MyListData ma = list.get(m);
//
//              FirebaseDatabase.getInstance().getReference()
//                      .child("Users")
//                      .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                      .child("Liked Events")
//                      .push()
//                      .setValue(new MyListData(ma.getTitle(), ma.getCity(), ma.getEvent(), ma.getDate(), ma.getTime(), ma.getTopic(), ma.getIs_liked()));
//          }
//      });
        }
    }
}