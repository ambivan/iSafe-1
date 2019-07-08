package com.solve.isafe.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.solve.isafe.Activities.ContactActivity;
import com.solve.isafe.Activities.FinalActivity;
import com.solve.isafe.Activities.CamActivity;
import com.solve.isafe.MapActivity;
import com.solve.isafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReportAccident extends Fragment implements TabLayout.OnTabSelectedListener {

    ArrayList<String> photolist;
    CardView click, loc, contact;

    ImageView second, third, what;

    Button report, viewonmap;

    EditText location;

    LinearLayout lin1, lin2, lin3;

    static int counter = 0;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        v = inflater.inflate(R.layout.tab3, container, false);

        what = (ImageView) v.findViewById(R.id.what);
        second = (ImageView) v.findViewById(R.id.second);
        third = (ImageView) v.findViewById(R.id.third);


        report = (Button) v.findViewById(R.id.report);
        viewonmap = (Button) v.findViewById(R.id.viewonmap);

        photolist = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        click = v.findViewById(R.id.clicklin);
        loc = v.findViewById(R.id.loclin);
        contact = v.findViewById(R.id.contactlin);

        location = (EditText) v.findViewById(R.id.loctag);

        lin1 = (LinearLayout) v.findViewById(R.id.lin1);
        lin2 = (LinearLayout) v.findViewById(R.id.lin2);
        lin3 = (LinearLayout) v.findViewById(R.id.lin3);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalact = new Intent(getActivity(), CamActivity.class);
                finalact.putExtra("frag", 3);
                finalact.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(finalact);
            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent finalact = new Intent(getActivity(), MapActivity.class);
                finalact.putExtra("frag", "3");


                finalact.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(finalact);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalact = new Intent(getActivity(), ContactActivity.class);
                finalact.putExtra("frag", "3");

                finalact.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(finalact);
            }
        });

        if (CamActivity.i == 1) {

            click.setVisibility(View.INVISIBLE);


            lin1.setVisibility(View.VISIBLE);

            CamActivity.i = 2;

        }

        what.setImageBitmap(CamActivity.photo);
        second.setImageBitmap(CamActivity.photo2);
        third.setImageBitmap(CamActivity.photo3);


        if (MapActivity.m == 1) {

            if (CamActivity.i == 2) {
                click.setVisibility(View.INVISIBLE);
                lin1.setVisibility(View.VISIBLE);
            }

            loc.setVisibility(View.INVISIBLE);
            lin2.setVisibility(View.VISIBLE);

            location.setText(MapActivity.loc);

            databaseReference
                    .child("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("Accident Report")
                    .child("Location")
                    .setValue(MapActivity.loc);

            viewonmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(getActivity(), MapActivity.class));

                }
            });

            if (CamActivity.i == 2) {
                Toast.makeText(getActivity(), "You can report the accident now. Contact Details are optional. ", Toast.LENGTH_LONG).show();
            }
            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CamActivity.i == 2) {
                        Intent finalact = new Intent(getActivity(), FinalActivity.class);
                        finalact.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        CamActivity.i = 0;
                        MapActivity.m = 0;

                        startActivity(finalact);
                    } else {
                        click.setVisibility(View.VISIBLE);


                        lin1.setVisibility(View.INVISIBLE);

                        Toast.makeText(getActivity(), "Please Upload Photos.", Toast.LENGTH_SHORT).show();

                        CamActivity.i = 0;
                        MapActivity.m = 0;
                    }
                }
            });


        }

        if (ContactActivity.c == 1) {

            click.setVisibility(View.INVISIBLE);


            lin1.setVisibility(View.VISIBLE);

            contact.setVisibility(View.INVISIBLE);

            lin3.setVisibility(View.VISIBLE);


            TextView contactname, phonenumber;

            contactname = (TextView) v.findViewById(R.id.namee);
            phonenumber = (TextView) v.findViewById(R.id.phonenoo);

            contactname.setText(ContactActivity.contactName);
            phonenumber.setText(ContactActivity.number);

            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent finalact = new Intent(getActivity(), FinalActivity.class);
                    finalact.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(finalact);

                    CamActivity.i = 0;
                    MapActivity.m = 0;
                    ContactActivity.c = 0;

                }
            });

        }


        if (ContactActivity.c == 0 && MapActivity.m == 0 && CamActivity.i == 0) {

            lin1.setVisibility(View.INVISIBLE);
            lin2.setVisibility(View.INVISIBLE);
            lin3.setVisibility(View.INVISIBLE);

            click.setVisibility(View.VISIBLE);
            loc.setVisibility(View.VISIBLE);
            contact.setVisibility(View.VISIBLE);


        }

        return v;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

        ContactActivity.c = 0;
        MapActivity.m = 0;
        CamActivity.i = 0;

        lin2.setVisibility(View.INVISIBLE);


        click.setVisibility(View.VISIBLE);
        loc.setVisibility(View.VISIBLE);
        contact.setVisibility(View.VISIBLE);

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

        lin1.setVisibility(View.INVISIBLE);
        lin2.setVisibility(View.INVISIBLE);
        lin3.setVisibility(View.INVISIBLE);

        click.setVisibility(View.VISIBLE);
        loc.setVisibility(View.VISIBLE);
        contact.setVisibility(View.VISIBLE);


    }


}