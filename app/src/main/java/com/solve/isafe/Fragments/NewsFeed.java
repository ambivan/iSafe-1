package com.solve.isafe.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solve.isafe.Activities.CreateEvent;
import com.solve.isafe.Adapters.MyListAdapter;
import com.solve.isafe.Classes.MyListData;
import com.solve.isafe.Classes.UserPost;
import com.solve.isafe.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsFeed extends Fragment {

    EditText city1;

    CardView create;

    LatLng latLng;
    GoogleMap mGoogleMap;

    String c;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user;
    String userid;
    UserPost userPost;

    Button createbutton;

    MyListAdapter recyclerAdapter;

    List<MyListData> list;
    RecyclerView recycle;
    Button view;
    RecyclerView recyclerView;

    View vieww;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        vieww = inflater.inflate(com.solve.isafe.R.layout.tab1, container, false);

        FirebaseApp.initializeApp(getContext());

        list = new ArrayList<MyListData>();

        auth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) vieww.findViewById(com.solve.isafe.R.id.recyclerView);

        create = (CardView) vieww.findViewById(com.solve.isafe.R.id.create);
        createbutton = (Button) vieww.findViewById(com.solve.isafe.R.id.createbutton);
        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateEvent.class));
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();

                userid = user.getUid();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        userPost = dataSnapshot.getValue(UserPost.class);

                        if (userPost.getPost().equals("Team Leader")) {


                        } else {

                            create.setVisibility(View.GONE);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        };
        auth.addAuthStateListener(authStateListener);


        city1 = (EditText) vieww.findViewById(R.id.city);

        checkLocationPermission();

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getActivity());

        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {


                String addr1 = "";

                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());


                List<Address> addressList1 = null;
                try {

                    addressList1 = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


                    if (addressList1 != null && addressList1.size() > 0) {
                        Log.i("PlaceInfo", addressList1.get(0).toString());

                        city1.setText(addressList1.get(0).getLocality());

                        city1.setEnabled(false);

                        c = addressList1.get(0).getLocality();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("nuh", "uh");
                }
            }
        });


        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Events");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                list.clear();

                for (final DataSnapshot child : dataSnapshot.getChildren()) {

                    MyListData events = child.getValue(MyListData.class);

                    final MyListData eventlist = new MyListData();

                    String title = events.getTitle();
                    String event = events.getEvent();
                    final String city = events.getCity();
                    String date = events.getDate();
                    String time = events.getTime();
                    String topic = events.getTopic();
                    String isliked = events.getIs_liked();
                    String eventid = events.getEventid();

                    eventlist.setTitle(title + ",");
                    eventlist.setCity(city);
                    eventlist.setEvent("Organizing an " + event);
                    eventlist.setDate("on " + date);
                    eventlist.setTime("Starting at " + time);
                    eventlist.setTopic("Topic: " + topic);
                    eventlist.setIs_liked(isliked);
                    eventlist.setEventid(eventid);


                    list.add(eventlist);


                    recyclerAdapter = new MyListAdapter(list, getActivity());
                    RecyclerView.LayoutManager recyce = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(recyce);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(recyclerAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return vieww;
    }


    public final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        //
//
        @Override
        protected void onPostExecute(List<Address> addresses) {

            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(getActivity(), "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map
            mGoogleMap.clear();

            // Adding Markers on Google Map for each matching address
            if (addresses != null) {
                for (int i = 0; i < addresses.size(); i++) {

                    Address address = (Address) addresses.get(i);

                    // Creating an instance of GeoPoint, to display in Google Map
                    latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    // Locate the first location
                    if (i == 0)
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                }
            }
        }
    }
}