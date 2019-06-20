package com.example.isafe.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isafe.R;

public class Feedback extends Fragment {

    View vf;


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vf = inflater.inflate(R.layout.feedback, container, false);


        return vf;
    }

}
