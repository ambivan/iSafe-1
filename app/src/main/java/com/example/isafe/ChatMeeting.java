package com.example.isafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.isafe.Classes.Message;
import com.example.isafe.Classes.UserPost;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatMeeting extends AppCompatActivity {

    private DatabaseReference mdatabase;
    private static final int SIGN_IN_REQUEST_CODE = 111;
    private FirebaseListAdapter<Message> adapter;
    private ListView listView;
    private String loggedInUserName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_meeting);

        FirebaseApp.initializeApp(this);

        final EditText input = (EditText) findViewById(R.id.input);


        listView = (ListView)findViewById(R.id.list1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .build(), SIGN_IN_REQUEST_CODE);
        } else {
            // User is already signed in, show list of messages
            showAllOldMessages();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatMeeting.this, "Please enter some texts!", Toast.LENGTH_SHORT).show();
                } else {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            UserPost userPost = dataSnapshot.getValue(UserPost.class);

                            FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child("Messages")
                                    .push()
                                    .setValue(new Message(input.getText().toString().trim(),
                                            userPost.getName(),
                                            FirebaseAuth.getInstance().getCurrentUser().getUid())) ;

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

        adapter = new MyListAdapter3(ChatMeeting.this, Message.class, R.layout.outgoingmessage,
                FirebaseDatabase.getInstance().getReference().child("Messages"));
        listView.setAdapter(adapter);
    }

    public String getLoggedInUserName() {
        return loggedInUserName;
    }
}

