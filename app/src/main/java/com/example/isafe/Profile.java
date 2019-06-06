package com.example.isafe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Profile extends Fragment {

    View v1;

    TextView desig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        v1 = inflater.inflate(R.layout.tab5, container, false);

        desig = (TextView) v1.findViewById(R.id.designation);

        if (SignupActivity.i == 1){
            desig.setText("Volunteer");
        }else if (SignupActivity.i ==2){
            desig.setText("Team Lead");
        }else if (SignupActivity.i == 3){
            desig.setText("Team Member");
        }


        return v1;
    }
}
