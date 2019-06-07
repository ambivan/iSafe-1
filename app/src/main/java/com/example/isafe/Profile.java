package com.example.isafe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends Fragment {

    View v1;

    static TextView desig;

    private FirebaseAuth auth;


    FirebaseAuth.AuthStateListener authStateListener;

    Button signout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        v1 = inflater.inflate(R.layout.tab5, container, false);


        auth = FirebaseAuth.getInstance();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = auth.getCurrentUser();

            }
        };

        auth.addAuthStateListener(authStateListener);


//        signout = (Button) v1.findViewById(R.id.signout);
//
//        signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                auth.signOut();
//
//            }
//        });

        desig = (TextView) v1.findViewById(R.id.designation);

        if (SignupActivity.i == 1 || LoginActivity.i == 1) {

            desig.setText("Volunteer");

        } else if (SignupActivity.i == 2 || LoginActivity.i == 2) {

            desig.setText("Team Lead");

        } else if (SignupActivity.i == 3 || LoginActivity.i == 3) {

            desig.setText("Team Member");

        }


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
