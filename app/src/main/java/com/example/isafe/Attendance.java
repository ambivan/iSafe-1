package com.example.isafe;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.isafe.MapActivity.MY_PERMISSIONS_REQUEST_LOCATION;

public class Attendance extends Fragment {

    RecyclerView recyclerView;
    List<MyListData> list;
    MyListAdapter2 recyclerAdapter;



    private FusedLocationProviderClient client;

    View vv;

    Button mark ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vv = inflater.inflate(R.layout.tab2, container, false);

        recyclerView = (RecyclerView) vv.findViewById(R.id.recyclerView2);

        mark = (Button) vv.findViewById(R.id.markatt);

        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Events");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list = new ArrayList<MyListData>();

                for (DataSnapshot child: dataSnapshot.getChildren()) {

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

                    eventlist.setTitle(title + ",");
                    eventlist.setCity(city);
                    eventlist.setEvent("Organizing an " + event);
                    eventlist.setDate("on " + date);
                    eventlist.setTime("Starting at " + time);
                    eventlist.setTopic( "Topic" + topic);

                    list.add(eventlist);

                    System.out.println(list);

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



        return vv;
    }


}