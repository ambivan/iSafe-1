package com.example.isafe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewsFeed extends Fragment {


    View vieww;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        vieww =  inflater.inflate(R.layout.tab1, container, false);



        MyListData[] myListData = new MyListData[] {

                new MyListData(R.drawable.institute, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.institute, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.institute, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.institute, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.institute, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),

        };


        RecyclerView recyclerView = (RecyclerView) vieww.findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return vieww;
    }
}
