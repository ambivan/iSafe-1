package com.example.isafe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatMeeting extends AppCompatActivity {

    private DatabaseReference mdatabase;
    private static final int SIGN_IN_REQUEST_CODE = 111;
    private FirebaseListAdapter<Message> adapter;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    static String userid;
    DatabaseReference databaseReference;

    private ListView listView;
    private String loggedInUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_meeting);

        FirebaseApp.initializeApp(this);

        final EditText input = (EditText) findViewById(R.id.input);


        listView = (ListView)findViewById(R.id.list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                FirebaseUser user = auth.getCurrentUser();

                userid = user.getUid();

                Log.i("n=bleh", "user issss" + userid);

            }
        };

        auth.addAuthStateListener(authStateListener);



        showAllOldMessages();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatMeeting.this, "Please enter some texts!", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .push()
                            .setValue(new Message(input.getText().toString(),
                                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                    FirebaseAuth.getInstance().getCurrentUser().getUid())
                            );
                    input.setText("");
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
        loggedInUserName = userid;
        Log.d("Main", "user id: " + loggedInUserName);

        adapter = new FbaseListAdapter(this, Message.class, R.layout.incomingmessage,
                FirebaseDatabase.getInstance().getReference());
        listView.setAdapter(adapter);

        Log.i("bleh", "mesaagsedd" + userid);
    }

    public String getLoggedInUserName() {
        return loggedInUserName;
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
}
