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

import com.example.isafe.Agenda_Meeting;
import com.example.isafe.ChatMeeting;
import com.example.isafe.Classes.UserPost;
import com.example.isafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Meetings extends Fragment {


  View vm;
  Button start;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    vm = inflater.inflate(R.layout.meetings, container, false);

    start = (Button) vm.findViewById(com.example.isafe.R.id.start);

    start.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

          d.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                  UserPost userPost = dataSnapshot.getValue(UserPost.class);

                  if (userPost.getPost().equals("Team Member")){

                      startActivity(new Intent(getActivity(), ChatMeeting.class));

                  }else {
                      startActivity(new Intent(getActivity(), Agenda_Meeting.class));
                  }

              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });

      }
    });

    return vm;

  }

}