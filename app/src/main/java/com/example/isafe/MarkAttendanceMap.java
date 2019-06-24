package com.example.isafe;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isafe.Activities.HomePageActivity;
import com.example.isafe.Adapters.MyListAdapter2;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.isafe.Adapters.MyListAdapter2.city1;
import static com.example.isafe.Adapters.MyListAdapter2.university;

public class MarkAttendanceMap extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    MarkerOptions markerOptions;
    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Double lat1, long1, lat2, long2, lat11, long11;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    PendingIntent mGeofencePendingIntent;
    ArrayList<Geofence> mGeofenceList;

    Button mark;


    EditText etLocation, velocation;
    static String loc;
    String addr = "";

    public FusedLocationProviderClient client;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_attendance_map);

        checkLocationPermission();

        etLocation = (EditText) findViewById(R.id.yourloc);
        velocation = (EditText) findViewById(R.id.venueloc);
        mark = (Button) findViewById(R.id.marker);

        velocation.setText("IIT Delhi");

        etLocation.setEnabled(false);

        mGeofenceList = new ArrayList<Geofence>();

        client = LocationServices.getFusedLocationProviderClient(MarkAttendanceMap.this);

        client.getLastLocation().addOnSuccessListener(MarkAttendanceMap.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {


                String addr1 = "";

                Geocoder geocoder = new Geocoder(MarkAttendanceMap.this, Locale.getDefault());


                List<Address> addressList1 = null;
                try {

                    addressList1 = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    System.out.println(location.getLatitude());
                    System.out.println(location.getLongitude());

                    lat1 = location.getLatitude();
                    long1 = location.getLongitude();


                    if (addressList1 != null && addressList1.size() > 0) {
                        Log.i("PlaceInfo", addressList1.get(0).toString());

                        for (int i = 0; i < 7; i++) {

                            if (addressList1.get(0).getAddressLine(i) != null) {
                                addr1 += addressList1.get(0).getAddressLine(i) + " ";

                                etLocation.setText(addr1);

                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("nuh", "uh");
                }
            }
        });


        checkLocationPermission();

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        // Getting a reference to the map
        if (mGoogleMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }


        mGoogleApiClient = new GoogleApiClient.Builder(MarkAttendanceMap.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        System.out.println(city1);
        System.out.println(MyListAdapter2.university);

        velocation.setText(university + city1);

        loc = velocation.getText().toString();

        new MarkAttendanceMap.GeocoderTask().execute(loc);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(MarkAttendanceMap.this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MarkAttendanceMap.this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MarkAttendanceMap.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {

            mGoogleMap.setMyLocationEnabled(true);
        }


        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                addr = "";

                Geocoder geocoder = new Geocoder(MarkAttendanceMap.this, Locale.getDefault());

                List<Address> addressList = null;

                try {
                    addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (addressList != null && addressList.size() > 0) {
                        Log.i("PlaceInfo", addressList.get(0).toString());

                        for (int i = 0; i < 7; i++) {

                            if (addressList.get(0).getAddressLine(i) != null) {
                                addr += addressList.get(0).getAddressLine(i) + " ";

                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("nuh", "uh");
                }


            }
        });

    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(MarkAttendanceMap.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

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

        loc = velocation.getText().toString();
        new MarkAttendanceMap.GeocoderTask().execute(loc);

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        lat11= location.getLatitude();
        long11 = location.getLongitude();

//        Double a = CalculationByDistance(lat11, long11, lat2, long2);
//        System.out.println(a);

        System.out.println(latLng);
//        MarkerOptions markerOptions1 = new MarkerOptions();
//        markerOptions1.position(latLng);
//        markerOptions1.title(addr);
//        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions1);

        //move map camera
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

    }

        private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

            @Override
            protected List<Address> doInBackground(String... locationName) {
                // Creating an instance of Geocoder class
                Geocoder geocoder = new Geocoder(getBaseContext());
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
                    Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
                }

                // Clears all the existing markers on the map
                mGoogleMap.clear();

                // Adding Markers on Google Map for each matching address
                if (addresses != null) {
                    for (int i = 0; i < addresses.size(); i++) {

                        Address address = (Address) addresses.get(i);

                        // Creating an instance of GeoPoint, to display in Google Map
                        latLng = new LatLng(address.getLatitude(), address.getLongitude());

                        System.out.println("hey" + latLng);
                        System.out.println(address.getLatitude());
                        System.out.println(address.getLongitude());

                        lat2 = address.getLatitude();
                        long2 = address.getLongitude();

                        final Double a = CalculationByDistance(lat1, long1, lat2, long2);
                        System.out.println(a);

                        mark.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (a>100.00){
                                    mark.setBackgroundResource(R.drawable.reportbuttonbg);
                                    Toast.makeText(MarkAttendanceMap.this, "You are still far from your venue location", Toast.LENGTH_SHORT).show();
                                } else if (a<100.00){
                                    mark.setBackgroundResource(R.drawable.reportbuttonbg);
                                    Toast.makeText(MarkAttendanceMap.this, "Attendance Marked!", Toast.LENGTH_SHORT).show();
                                    HomePageActivity.frag = 1;
                                    startActivity(new Intent(MarkAttendanceMap.this, HomePageActivity.class));
                                }
                            }
                        });


//                       

                        String addressText = String.format("%s, %s",
                                address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                                address.getCountryName());

                        markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(velocation.getText().toString());


                        mGoogleMap.addMarker(markerOptions);

                        // Locate the first location
                        if (i == 0)
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                    }
                }
            }
        }

    private void checkLocationPermission () {
        if (ContextCompat.checkSelfPermission(MarkAttendanceMap.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MarkAttendanceMap.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(MarkAttendanceMap.this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MarkAttendanceMap.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MarkAttendanceMap.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }

    }

    @Override
    public void onBackPressed () {
        moveTaskToBack(true);
    }

    public double CalculationByDistance(double initialLat, double initialLong,
                                        double finalLat, double finalLong){
        int R = 6371; // km (Earth radius)
        double dLat = toRadians(finalLat-initialLat);
        double dLon = toRadians(finalLong-initialLong);
        initialLat = toRadians(initialLat);
        finalLat = toRadians(finalLat);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(initialLat) * Math.cos(finalLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c * 1000;


    }

    public double toRadians(double deg) {
        return deg * (Math.PI/180);
    }
}


