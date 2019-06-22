package com.example.isafe.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isafe.Activities.HomePageActivity;
import com.example.isafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends Fragment {

    View vf;

    EditText feedback;
    String feed;
    Button submit;


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vf = inflater.inflate(R.layout.feedback, container, false);

        submit = (Button) vf.findViewById(R.id.submit);
        feedback = (EditText) vf.findViewById(R.id.feedback);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                feed = feedback.getText().toString();


                if (!TextUtils.isEmpty(feed)) {
                    Toast.makeText(getActivity(), "Your Feedback has been submitted!", Toast.LENGTH_SHORT).show();


                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Feedbacks")
                            .push()
                            .setValue(feed);

                    HomePageActivity.frag = 1;
                    startActivity(new Intent(getActivity(), HomePageActivity.class));

                }else {
                    Toast.makeText(getActivity(), "Please enter some feedback!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return vf;
    }

}
