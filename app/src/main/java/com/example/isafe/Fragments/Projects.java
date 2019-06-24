package com.example.isafe.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.isafe.R;

public class Projects extends Fragment {


    View vp;

    Button add;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vp = inflater.inflate(com.example.isafe.R.layout.projects, container, false);

        add = (Button) vp.findViewById(R.id.addp);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return vp;

    }
}
