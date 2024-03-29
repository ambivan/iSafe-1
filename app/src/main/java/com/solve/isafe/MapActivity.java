package com.solve.isafe;

import android.Manifest;
import android.app.AlertDialog;
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
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.solve.isafe.Activities.HomePageActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
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
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    MarkerOptions markerOptions;
    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;

    EditText etLocation;
    public static String loc;
    Button btn_find;

    String addr = "";
    public static int m = 0;

    public FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        Window window = MapActivity.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(MapActivity.this, R.color.mystatus));

        checkLocationPermission();

        HomePageActivity.frag = 3;


        etLocation = (EditText) findViewById(R.id.et_location);
        btn_find = (Button) findViewById(R.id.btn_find);

        client = LocationServices.getFusedLocationProviderClient(MapActivity.this);

        client.getLastLocation().addOnSuccessListener(MapActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                checkLocationPermission();

                String addr1 = "";

                Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());

                List<Address> addressList1 = null;
                try {
                    addressList1 = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    if (addressList1 != null && addressList1.size() > 0) {
                        Log.i("PlaceInfo", addressList1.get(0).toString());

                        for (int i = 0; i < 7; i++) {

                            if (addressList1.get(0).getAddressLine(i) != null) {
                                addr1 += addressList1.get(0).getAddressLine(i) + " ";

                                etLocation.setText(addr1);
                                loc = etLocation.getText().toString();

                                etLocation.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        loc = etLocation.getText().toString();

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });

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


        mGoogleApiClient = new GoogleApiClient.Builder(MapActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        // Setting button click event listener for the find button
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting reference to EditText to get the user input location

                // Getting user input location
                loc = etLocation.getText().toString();

                if (loc != null && !loc.equals("")) {
                    new GeocoderTask().execute(loc);
                }
            }
        });


        Button locate = (Button) findViewById(R.id.locat);
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLocation != null) {

                    finish();
                    Intent home = new Intent(MapActivity.this, HomePageActivity.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(home);
                    m = 1;
                }
            }
        });

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
                    if (ContextCompat.checkSelfPermission(MapActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MapActivity.this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
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
            if (ContextCompat.checkSelfPermission(MapActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
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

                Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());

                List<Address> addressList = null;

                try {
                    addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (addressList != null && addressList.size() > 0) {
                        Log.i("PlaceInfo", addressList.get(0).toString());

                        for (int i = 0; i < 7; i++) {

                            if (addressList.get(0).getAddressLine(i) != null) {
                                addr += addressList.get(0).getAddressLine(i) + " ";

                                etLocation.setText(addr);
                                loc = etLocation.getText().toString();

                                etLocation.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        loc = etLocation.getText().toString();

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
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
        if (ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
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
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(latLng);
        markerOptions1.title(addr);

        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions1);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));


    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


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

                    String addressText = String.format("%s, %s",
                            address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                            address.getCountryName());

                    markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(etLocation.getText().toString());
                    mGoogleMap.addMarker(markerOptions);

                    // Locate the first location
                    if (i == 0)
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                }
            }
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(MapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(MapActivity.this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MapActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}