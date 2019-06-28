package com.example.isafe.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.isafe.Activities.ChatMeeting;
import com.example.isafe.R;
import com.example.isafe.Services.NotificationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MeetingRequest extends Fragment {

    Button accept;

    View vr;

    LinearLayout linearLayout;

    NotificationService notificationService;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vr = inflater.inflate(R.layout.activity_meeting_request, container, false);

        accept = (Button) vr.findViewById(R.id.accept);
        linearLayout = (LinearLayout) vr.findViewById(R.id.acceptlin);


        DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Agendas");

        System.out.println("oh"+FirebaseAuth.getInstance().getCurrentUser().getUid());

        d.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                linearLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Accept");
        databaseReference.setValue(0);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.setValue(1);

                startActivity(new Intent(getActivity(), ChatMeeting.class));

            }
        });

        return vr;
    }
}
