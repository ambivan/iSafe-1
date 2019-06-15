package com.example.isafe.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isafe.Adapters.profileAdapter;
import com.example.isafe.Classes.profileM;

import java.util.ArrayList;

public class teamProfile extends Fragment {


    View vt;

    TextView team_name;
    RecyclerView recyclerView;
    private ArrayList<profileM> profileMArrayList;
    private profileAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vt = inflater.inflate(com.example.isafe.R.layout.tab5team, container, false);

        team_name = (TextView) vt.findViewById(com.example.isafe.R.id.team_name);
        recyclerView= (RecyclerView) vt.findViewById(com.example.isafe.R.id.recyclerViewh);

        profileM[] profileMS = new profileM[]{
                new profileM("Shambhavi", com.example.isafe.R.drawable.profile),
                new profileM("Varun", com.example.isafe.R.drawable.profile),
                new profileM("Mansi", com.example.isafe.R.drawable.profile),
                new profileM("Manasvi", com.example.isafe.R.drawable.profile)
        };

        adapter = new profileAdapter(getContext(), profileMS);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        team_name.setText("Roadies");

        return vt;

    }


}
