package com.solve.isafe.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Assist extends Fragment {

    Button call;
    View view;

    TextView know;



    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(com.solve.isafe.R.layout.tab4, container, false);

        call = (Button) view.findViewById(com.solve.isafe.R.id.buttonassist);
        know = (TextView) view.findViewById(com.solve.isafe.R.id.knowmore);


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);

                callIntent.setData(Uri.parse("tel:18004197779"));

                startActivity(callIntent);
            }
        });

        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.isafeassist.org/"));
                startActivity(browserIntent);
            }
        });

        return view;


    }
}