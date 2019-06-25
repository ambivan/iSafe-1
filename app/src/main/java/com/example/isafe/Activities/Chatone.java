package com.example.isafe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isafe.Adapters.AdapterOne;
import com.example.isafe.Classes.Message;
import com.example.isafe.Classes.UserPost;
import com.example.isafe.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Chatone extends AppCompatActivity {

    TextView date, membername;

    EditText input;

    private FirebaseListAdapter<Message> adapter;


    ListView listView4;

    private static final int SIGN_IN_REQUEST_CODE = 111;
    private String loggedInUserName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatone);

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        listView4 = (ListView) findViewById(R.id.list14);

        date = (TextView) findViewById(R.id.datemem);
        membername = (TextView) findViewById(R.id.membername);

        input = (EditText) findViewById(R.id.input2);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String dateString = formatter.format(new Date().getTime());

        date.setText(dateString);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        showAllOldMessages();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().trim().equals("")) {
                    Toast.makeText(Chatone.this, "Please enter some texts!", Toast.LENGTH_SHORT).show();
                } else {

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            UserPost userPost = dataSnapshot.getValue(UserPost.class);


                            if (userPost.getPost().equals("Team Leader")) {


                                FirebaseDatabase.getInstance()
                                        .getReference()
                                        .child("Messages")
                                        .child(IndividualChat.unique + userPost.getTeamname())
                                        .push()
                                        .setValue(new Message(input.getText().toString().trim(),
                                                userPost.getName(),
                                                FirebaseAuth.getInstance().getCurrentUser().getUid()));

                            } else if (userPost.getPost().equals("Team Member")){
                                FirebaseDatabase.getInstance()
                                        .getReference()
                                        .child("Messages")
                                        .child(userPost.getName() + userPost.getTeamname())
                                         .push()
                                .setValue(new Message(input.getText().toString().trim(), userPost.getName(),
                                        FirebaseAuth.getInstance().getCurrentUser().getUid()));
                            }

                            input.setText("");



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });

                }

            }

        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in successful!", Toast.LENGTH_LONG).show();
                showAllOldMessages();
            } else {
                Toast.makeText(this, "Sign in failed, please try again later", Toast.LENGTH_LONG).show();
                // Close the app
                finish();
            }
        }
    }


    private void showAllOldMessages() {
        loggedInUserName = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("Main", "user id: " + loggedInUserName);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserPost userPost = dataSnapshot.getValue(UserPost.class);

                if (userPost != null) {
                    if (userPost.getPost().equals("Team Member")) {
                        System.out.println("yes" + userPost.getName());
                        membername.setText(userPost.getName());

                        adapter = new AdapterOne(Chatone.this, Message.class, R.layout.outgoingmessage,
                                FirebaseDatabase.getInstance().getReference().child("Messages").child(userPost.getName() + userPost.getTeamname()));
                    } else if (userPost.getPost().equals("Team Leader")){
                        System.out.println("yes" + userPost.getName());
                        adapter = new AdapterOne(Chatone.this, Message.class, R.layout.outgoingmessage,
                                FirebaseDatabase.getInstance().getReference().child("Messages").child(IndividualChat.unique + userPost.getTeamname()));
                    }

                }

              listView4.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    public String getLoggedInUserName() {
        return loggedInUserName;
    }
}
