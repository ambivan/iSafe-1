package com.example.isafe.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isafe.R;

public class RoadSafetyAudit extends Fragment {


    View vrr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vrr = inflater.inflate(R.layout.road_safety_audit, container, false);




        return vrr;
    }
}
