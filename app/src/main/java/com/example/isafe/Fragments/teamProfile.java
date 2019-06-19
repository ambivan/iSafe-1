package com.example.isafe.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isafe.Adapters.profileAdapter;
import com.example.isafe.Classes.UserPost;
import com.example.isafe.Classes.profileM;
import com.example.isafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class teamProfile extends Fragment {


    View vt;

    TextView team_name;
    RecyclerView recyclerView;
    private ArrayList<profileM> profileMArrayList;
    TextView prof_name;
    private profileAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vt = inflater.inflate(com.example.isafe.R.layout.tab5team, container, false);

        team_name = (TextView) vt.findViewById(R.id.team_name);
        recyclerView= (RecyclerView) vt.findViewById(R.id.recyclerViewh);
        prof_name = (TextView) vt.findViewById(R.id.pro_name);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                             @Override
                                                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                 UserPost userPost = dataSnapshot.getValue(UserPost.class);

                                                                 if (userPost != null) {
                                                                     prof_name.setText(userPost.getName());
                                                                 }


                                                             }

                                                             @Override
                                                             public void onCancelled(@NonNull DatabaseError databaseError) {

                                                             }

                                                         });


        profileM[] profileMS = new profileM[]{
                new profileM("Shambhavi", R.drawable.profile),
                new profileM("Varun", R.drawable.profile),
                new profileM("Mansi", R.drawable.profile),
                new profileM("Manasvi", R.drawable.profile)
        };

        adapter = new profileAdapter(getContext(), profileMS);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        team_name.setText("Roadies");

        return vt;

    }


}
