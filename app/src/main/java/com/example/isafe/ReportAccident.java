package com.example.isafe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportAccident extends Fragment implements View.OnClickListener{


    LinearLayout click, loc, contact;

    ImageView second, third, what;

    Button report;

    static TextView location;

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        v = inflater.inflate(R.layout.tab3, container, false);

        DatabaseReference rootloc, dataloc;

        report = (Button) v.findViewById(R.id.report);

        click = (LinearLayout) v.findViewById(R.id.clicklin);
        loc = (LinearLayout) v.findViewById(R.id.loclin);
        contact = (LinearLayout) v.findViewById(R.id.contactlin);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalact = new Intent(getActivity(), CamActivity.class);
                finalact.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(finalact);
            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent finalact = new Intent(getActivity(), MapActivity.class);
                finalact.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(finalact);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalact = new Intent(getActivity(), ContactActivity.class);
                finalact.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(finalact);
            }
        });

        return v;
    }


    @Override
    public void onClick(View v) {

    }
}
