package com.example.isafe.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Projects extends Fragment {


    View vp;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vp = inflater.inflate(com.example.isafe.R.layout.projects, container, false);

        return vp;

    }
}
