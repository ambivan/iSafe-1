package com.example.isafe;

import android.Manifest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ru.nikartm.support.util.DensityUtils.dpToPx;

public class NewsFeed extends Fragment {

  private FusedLocationProviderClient client;

  EditText city;

  CardView create;

   FirebaseAuth auth;
  FirebaseAuth.AuthStateListener authStateListener;
  FirebaseUser user;
    String userid;
    UserPost userPost;

    Button createbutton ;

    MyListAdapter recyclerAdapter;

    int i ;

    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<MyListData> list;
    RecyclerView recycle;
    Button view;
    RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;


    View vieww;
  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

      vieww =  inflater.inflate(R.layout.tab1, container, false);


      FirebaseApp.initializeApp(getContext());

      auth = FirebaseAuth.getInstance();
       recyclerView = (RecyclerView) vieww.findViewById(R.id.recyclerView);


      create = (CardView) vieww.findViewById(R.id.create);
      createbutton = (Button) vieww.findViewById(R.id.createbutton);

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

          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(userid);

          databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               userPost = dataSnapshot.getValue(UserPost.class);

              if (userPost.getPost().equals("Team Leader")){


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


      city = (EditText) vieww.findViewById(R.id.city);

      city.setText("Delhi");


      final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Events");

      dbref.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              list = new ArrayList<MyListData>();

              for (DataSnapshot child: dataSnapshot.getChildren()) {

                  System.out.println(child.getKey());

                  System.out.println("list cbdsj" + child.getValue());

                  MyListData events = child.getValue(MyListData.class);

                  MyListData eventlist = new MyListData();


                  String title = events.getTitle();
                  String event = events.getEvent();
                  String city = events.getCity();
                  String date = events.getDate();
                  String time = events.getTime();
                  String topic = events.getTopic();

                  eventlist.setTitle(title + ",");
                  eventlist.setCity(city);
                  eventlist.setEvent("Organizing an " + event);
                  eventlist.setDate("on " + date);
                  eventlist.setTime("Starting at " + time);
                  eventlist.setTopic( "Topic: " + topic);

                  list.add(eventlist);

                  System.out.println(list);

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

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView uni;
//        public TextView title1, event1, date1, time1, topic1 ;
//
//        Button register;
//        ImageView like, send, msg;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            this.uni = (ImageView) itemView.findViewById(R.id.uni);
//
//            this.title1 = (TextView) itemView.findViewById(R.id.title2);
//            this.event1 = (TextView) itemView.findViewById(R.id.event);
//            this.date1 = (TextView) itemView.findViewById(R.id.date);
//            this.time1 = (TextView) itemView.findViewById(R.id.time);
//            this.topic1 = (TextView) itemView.findViewById(R.id.topic);
//
//            this.register = (Button) itemView.findViewById(R.id.register);
//
//            this.like = (ImageView) itemView.findViewById(R.id.heart);
//            this.send = (ImageView) itemView.findViewById(R.id.send);
//            this.msg = (ImageView) itemView.findViewById(R.id.chatt);
//
//        }
//
//        public void setTitle(String title) {
//            title1.setText(title);
//        }
//
//        public void setEvent(String title) {
//            this.title = title;
//        }
//
//        public void setDate(String title) {
//            this.title = title;
//        }
//
//        public void setTime(String title) {
//            this.title = title;
//        }
//
//        public void setTopic(String title) {
//            this.title = title;
//        }
//
//
//        public String getTitle(){
//            return title;
//        }
//
//        public String getEvent(){
//            return event;
//        }
//
//        public String getDate(){
//            return date;
//        }
//
//        public String getTime(){
//            return time;
//        }
//
//        public String getTopic(){
//            return topic;
//        }
//    }
//
//    private void fetch() {
//
//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("Events");
//
//        FirebaseRecyclerOptions<MyListData> options =
//                new FirebaseRecyclerOptions.Builder<MyListData>()
//                        .setQuery(query, new SnapshotParser<MyListData>() {
//                            @NonNull
//                            @Override
//                            public MyListData parseSnapshot(@NonNull DataSnapshot snapshot) {
//                                return new MyListData(snapshot.child("title").getValue().toString(),
//                                        snapshot.child("event").getValue().toString(),
//                                        snapshot.child("date").getValue().toString(),
//                                        snapshot.child("time").getValue().toString(),
//                                        snapshot.child("topic").getValue().toString());
//                            }
//                        })
//                        .build();
//
//        adapter = new FirebaseRecyclerAdapter<MyListData, ViewHolder>(options) {
//            @Override
//            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.list_item, parent, false);
//
//                return new ViewHolder(view);
//            }
//
//
//            @Override
//            protected void onBindViewHolder(ViewHolder holder, final int position, MyListData model) {
//                holder.se(model.getTitle());
//                holder.setTxtDesc(model.getEvent());
//                holder.setTxtDesc(model.getEvent());
//
//            }
//
//        };
//        recyclerView.setAdapter(adapter);
//    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;



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
                            MY_PERMISSIONS_REQUEST_LOCATION );
                  }
                })
                .create()
                .show();
      } else {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION );
      }
    }
  }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}