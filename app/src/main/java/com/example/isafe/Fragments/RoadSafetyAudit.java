package com.example.isafe.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isafe.Activities.road2;
import com.example.isafe.Classes.Road1;
import com.example.isafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RoadSafetyAudit extends Fragment {


    View vrr;

    EditText e1, e2, e3, e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14;
    String et1, et2, et3, et4, et5, et6, et7, et8, et9, et10, et11, et12, et13, et14;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vrr = inflater.inflate(R.layout.road_safety_audit, container, false);


        Button next1 = vrr.findViewById(R.id.next1);

        e1 = vrr.findViewById(R.id.e1);
        e2 = vrr.findViewById(R.id.e2);
        e3 = vrr.findViewById(R.id.e3);
        e4= vrr.findViewById(R.id.e4);
        e5 = vrr.findViewById(R.id.e5);
        e6 = vrr.findViewById(R.id.e6);
        e7 = vrr.findViewById(R.id.e7);
        e8 = vrr.findViewById(R.id.e8);
        e9 = vrr.findViewById(R.id.e9);
        e10 = vrr.findViewById(R.id.e10);
        e11 = vrr.findViewById(R.id.e11);
        e12 = vrr.findViewById(R.id.e12);
        e13 = vrr.findViewById(R.id.e13);
        e14 = vrr.findViewById(R.id.e14);




        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et1 = e1.getText().toString();
                et2 = e2.getText().toString();
                et3 = e3.getText().toString();
                et4 = e4.getText().toString();
                et5 = e5.getText().toString();
                et6 = e6.getText().toString();
                et7 = e7.getText().toString();
                et8 = e8.getText().toString();
                et9 = e9.getText().toString();
                et10 = e10.getText().toString();
                et11 = e11.getText().toString();
                et12 = e12.getText().toString();
                et13 = e13.getText().toString();
                et14 = e14.getText().toString();

                if (TextUtils.isEmpty(et1) || TextUtils.isEmpty(et2) || TextUtils.isEmpty(et3) || TextUtils.isEmpty(et4) || TextUtils.isEmpty(et5) || TextUtils.isEmpty(et6) || TextUtils.isEmpty(et7) || TextUtils.isEmpty(et8) || TextUtils.isEmpty(et9) || TextUtils.isEmpty(et10)||TextUtils.isEmpty(et11)||TextUtils.isEmpty(et12)||TextUtils.isEmpty(et13)||TextUtils.isEmpty(et14)) {
                    Toast.makeText(getActivity(), "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("RoadSafetyAudit")
                            .child("Checklist1")
                            .push()
                            .setValue(new Road1(et1, et2, et3, et4, et5, et6, et7, et8, et9, et10, et11, et12, et13, et14));

                    startActivity(new Intent(getActivity(), road2.class));
                }
            }
        });


        return vrr;
    }
}
