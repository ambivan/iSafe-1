package com.example.isafe.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.isafe.Activities.road2;
import com.example.isafe.R;

public class RoadSafetyAudit extends Fragment {


    View vrr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vrr = inflater.inflate(R.layout.road_safety_audit, container, false);


        Button next1 = vrr.findViewById(R.id.next1);

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), road2.class));
            }
        });


        return vrr;
    }
}
