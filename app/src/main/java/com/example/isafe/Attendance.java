package com.example.isafe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Attendance extends Fragment {

    View vv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        vv = inflater.inflate(R.layout.tab2, container, false);

        MyListData[] myListData = new MyListData[] {

                new MyListData(R.drawable.profile, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.newsfeed, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.call, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.camera, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.car, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),

        };


        RecyclerView recyclerView = (RecyclerView) vv.findViewById(R.id.recyclerView2);
        MyListAdapter2 adapter = new MyListAdapter2(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return vv;
    }
}
