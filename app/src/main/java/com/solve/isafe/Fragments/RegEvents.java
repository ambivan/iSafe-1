package com.solve.isafe.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solve.isafe.Adapters.MyListAdapter2;
import com.solve.isafe.Classes.MyListData;
import com.solve.isafe.R;

import java.util.ArrayList;
import java.util.List;

public class RegEvents extends Fragment {


    View vre;

    RecyclerView recyclerView;
    List<MyListData> list;
    MyListAdapter2 recyclerAdapter;

    TextView regfirst;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vre = inflater.inflate(R.layout.regevents, container, false);

        recyclerView = (RecyclerView) vre.findViewById(com.solve.isafe.R.id.recyclerView2);
        list = new ArrayList<MyListData>();

        regfirst = vre.findViewById(R.id.not);


        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Registered Events");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    MyListData events = child.getValue(MyListData.class);

                    MyListData eventlist = new MyListData();

                    String title = events.getTitle();
                    String event = events.getEvent();
                    String city = events.getCity();
                    String date = events.getDate();
                    String time = events.getTime();
                    String topic = events.getTopic();
                    String eventid = events.getEventid();

                    eventlist.setTitle(title);
                    eventlist.setCity(city);
                    eventlist.setEvent(event);
                    eventlist.setDate(date);
                    eventlist.setTime(time);
                    eventlist.setTopic(topic);
                    eventlist.setEventid(eventid);

                    list.add(eventlist);

                    if (list != null) {
                        regfirst.setVisibility(View.GONE);
                    }

                    recyclerAdapter = new MyListAdapter2(list, getActivity());
                    RecyclerView.LayoutManager recyce = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(recyce);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(recyclerAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return vre;
    }


}
