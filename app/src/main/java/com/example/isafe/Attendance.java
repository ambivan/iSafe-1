package com.example.isafe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.isafe.MapActivity.MY_PERMISSIONS_REQUEST_LOCATION;

public class Attendance extends Fragment {


    private FusedLocationProviderClient client;

    View vv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        vv = inflater.inflate(R.layout.tab2, container, false);

        MyListData[] myListData = new MyListData[]{

                new MyListData(R.drawable.institute, "IIT", "writing compettiton", "on this and this day", "at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.institute, "IIT", "writing compettiton", "on this and this day", "at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.institute, "IIT", "writing compettiton", "on this and this day", "at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.institute, "IIT", "writing compettiton", "on this and this day", "at this time", "topic" + "gettopic"),
                new MyListData(R.drawable.institute, "IIT", "writing compettiton", "on this and this day", "at this time", "topic" + "gettopic"),

        };


        RecyclerView recyclerView = (RecyclerView) vv.findViewById(R.id.recyclerView2);
        MyListAdapter2 adapter = new MyListAdapter2(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return vv;
    }


}




