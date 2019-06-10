package com.example.isafe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.isafe.Signup2.post;

public class Profile extends Fragment {

    private String loggedInUserName = "";


    View v1;

    static TextView desig;

    private FirebaseAuth auth;

    DatabaseReference databaseReference;

    String designation, userid;

    FirebaseAuth.AuthStateListener authStateListener;

    Button signout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        v1 = inflater.inflate(R.layout.tab5, container, false);

        desig = (TextView) v1.findViewById(R.id.designation);

        auth = FirebaseAuth.getInstance();

        Log.i("bleh", post);


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                FirebaseUser user = auth.getCurrentUser();

                userid = user.getUid();

                databaseReference = FirebaseDatabase.getInstance().getReference().child(userid);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        UserPost userPost = dataSnapshot.getValue(UserPost.class);
                        designation = userPost.getPost();

                        desig.setText(designation);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        };

        auth.addAuthStateListener(authStateListener);

        return v1;
    }


    @Override
    public void onStart() {
        super.onStart();

        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        auth.removeAuthStateListener(authStateListener);
    }


}