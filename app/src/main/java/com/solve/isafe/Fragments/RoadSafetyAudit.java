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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.solve.isafe.Activities.road1;
import com.solve.isafe.Classes.RSA;
import com.solve.isafe.R;

import java.io.IOException;
import java.util.List;

public class RoadSafetyAudit extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener  {

    public final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    Marker mCurrLocationMarker;

    View vrr;

    LatLng latLng;
    GoogleMap mGoogleMap;

    String len;

    EditText rname, startlat, startlong, endlat, endlong, rlength;
    String snamer, startlatloc, startlongloc,endlatloc,endlongloc, slength;

    public static String timestamp;

    Button proceed, startpoint, endpoint;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vrr = inflater.inflate(R.layout.road_safety_audit, container, false);

        checkLocationPermission();

        startlat = vrr.findViewById(R.id.startlat);
        startlong = vrr.findViewById(R.id.startlong);
        endlat = vrr.findViewById(R.id.endlat);
        endlong = vrr.findViewById(R.id.endlong);

        startpoint = vrr.findViewById(R.id.startpoint);
        endpoint = vrr.findViewById(R.id.endpoint);

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getActivity());

        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {


                final String u = String.valueOf(location.getLatitude());
                final String vv = String.valueOf(location.getLongitude());

                startlat.setText(u);
                startlong.setText(vv);

                startpoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startlatloc = startlat.getText().toString();
                        startlongloc = startlong.getText().toString();

                        endlat.setText(u);
                        endlong.setText(vv);

                    }
                });


                endpoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        endlatloc = endlat.getText().toString();
                        endlongloc = endlong.getText().toString();
                    }
                });

                            }
        });

        final Spinner spinner = (Spinner) vrr.findViewById(R.id.spinnertime);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.length));

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position == 0) {

                    len = "m";

                } else if (position == 1) {
                    len = "km";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rname = vrr.findViewById(R.id.rname);
        rlength = vrr.findViewById(R.id.rlength);
        proceed = vrr.findViewById(R.id.proceddd);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timestamp = String.valueOf(System.currentTimeMillis());

                snamer = rname.getText().toString();
                slength = rlength.getText().toString() + " " + len;


                if (!TextUtils.isEmpty(snamer) && !TextUtils.isEmpty(slength)) {

                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("RoadSafetyAudit")
                            .child(timestamp)
                            .setValue(new RSA(snamer, timestamp, slength, startlatloc, startlongloc, endlatloc, endlongloc));

                    startActivity(new Intent(getActivity(), road1.class));


                } else {
                    Toast.makeText(getActivity(), "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return vrr;
    }

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Location mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker


        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        startlat.setText(String.valueOf(location.getLatitude()));
        startlong.setText(String.valueOf(location.getLongitude()));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

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
