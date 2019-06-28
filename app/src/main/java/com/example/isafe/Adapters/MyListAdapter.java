package com.example.isafe.Adapters;

import android.content.Context;
import android.content.Intent;
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

    private static int mla;
    private static int ml;

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

        System.out.println(myListData.getEventid()+"bsdak");

        DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Registered Events");

        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (myListData.getEventid().equals(ds.getKey())) {
                        System.out.println("yes");

                        holder.register.setEnabled(false);
                        holder.register.setBackgroundResource(R.drawable.report);
                        holder.register.setText("Registered");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference l = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Liked Events");

        l.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    System.out.println(ds.getKey());

                    if (myListData.getEventid().equals(ds.getKey())) {
                        System.out.println("yess");

//                        holder.like.setBackgroundResource(R.drawable.redheart);


                    } else {

                        holder.like.setBackgroundResource(R.drawable.heart);


                    }
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

        } catch (Exception ignored) {

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

                    final MyListData mylist = list.get(mla);


                    DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("Events");

                    d.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                if (mylist.getEventid().equals(ds.getKey())) {
                                    System.out.println("yes");
                                } else {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Registered Events")
                                            .child(mylist.getEventid())
                                            .setValue(new MyListData(mylist.getEventid(), mylist.getTitle(), mylist.getCity(),
                                                    mylist.getEvent(), mylist.getDate(), mylist.getTime(), mylist.getTopic(), "0"));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

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

                    final String event = mll.getTitle() + mll.getCity() + " is " + mll.getEvent() + " " + mll.getDate() + ".\n" + mll.getTime() + "\n" + mll.getTopic();

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
                    final MyListData m = list.get(ml);

                    DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("Events");

                    d.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                if (m.getEventid().equals(ds.getKey())) {

                                    System.out.println("yes");


                                } else {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Liked Events")
                                            .child(m.getEventid())
                                            .setValue(new MyListData(m.getEventid(), m.getTitle(), m.getCity(),
                                                    m.getEvent(), m.getDate(), m.getTime(), m.getTopic(), "1"));

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });
        }
    }
}