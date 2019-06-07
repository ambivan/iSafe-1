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
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NewsFeed extends Fragment {

    private FusedLocationProviderClient client;

    EditText city;


    View vieww;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        vieww =  inflater.inflate(R.layout.tab1, container, false);




        city = (EditText) vieww.findViewById(R.id.city);

        city.setText("Delhi");

        MyListData[] myListData = new MyListData[] {

                new MyListData(R.drawable.institute, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),

                new MyListData(R.drawable.institute, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),

                new MyListData(R.drawable.institute, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),

                new MyListData(R.drawable.institute, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),

                new MyListData(R.drawable.institute, "IIT", "writing compettiton","on this and this day","at this time", "topic" + "gettopic"),

        };


        RecyclerView recyclerView = (RecyclerView) vieww.findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        checkLocationPermission();

        client = LocationServices.getFusedLocationProviderClient(getActivity());

//        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//
//                String addr1 = "";
//
//                Geocoder geocoder = new Geocoder(getContext(),Locale.getDefault());
//
//
//                List<Address> addressList1 = null;
//                try {
//                    addressList1 = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//
//                    if (addressList1 != null && addressList1.size()>0){
//                        Log.i("PlaceInfo", addressList1.get(0).toString());
//
//                        for (int i=0; i<7; i++) {
//
//                            if (addressList1.get(0).getAddressLine(i) != null) {
//                                addr1 += addressList1.get(0).getAddressLine(i) + " ";
//
//                                city.setText(addr1);
//
//                            }
//
//                        }
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.i("nuh", "uh");
//                }
//
//            }
//        });


        return vieww;
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;



    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }
}
