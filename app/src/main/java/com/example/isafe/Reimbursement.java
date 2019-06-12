package com.example.isafe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Reimbursement extends Fragment {

    View vr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vr = inflater.inflate(R.layout.reimbursement, container, false);



        return  vr;

    }

}
