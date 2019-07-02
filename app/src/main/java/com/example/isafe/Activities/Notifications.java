package com.example.isafe.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.example.isafe.Adapters.NotificationsAdapter;
import com.example.isafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {

    ArrayList<Long> strings;
    NotificationsAdapter recyclerAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar123);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        Window window = Notifications.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(Notifications.this, R.color.mystatus));

        recyclerView = findViewById(R.id.recyclerViewnotif);

        DatabaseReference n = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Notifications");

        strings = new ArrayList<>();


        n.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()){

                    System.out.println(child.getValue());

                    Long date6 = child.getValue(Long.class);

                    strings.add(date6);

                    recyclerAdapter = new NotificationsAdapter(strings, Notifications.this);
                    RecyclerView.LayoutManager recyce = new LinearLayoutManager(Notifications.this);
                    recyclerView.setLayoutManager(recyce);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(recyclerAdapter);

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
