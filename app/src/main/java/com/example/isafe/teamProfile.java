package com.example.isafe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class teamProfile extends Fragment {


    View vt;

    TextView team_name;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vt = inflater.inflate(R.layout.tab5team, container, false);

        team_name = (TextView) vt.findViewById(R.id.team_name);
        recyclerView= (RecyclerView) vt.findViewById(R.id.recyclerViewh);

        team_name.setText("Roadies");



        return vt;

    }

}
