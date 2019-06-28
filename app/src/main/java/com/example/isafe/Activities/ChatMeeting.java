package com.example.isafe.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.isafe.Adapters.MyListAdapter3;
import com.example.isafe.CamActivity;
import com.example.isafe.Classes.Message;
import com.example.isafe.Classes.UserPost;
import com.example.isafe.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatMeeting extends AppCompatActivity {

    private static final int SIGN_IN_REQUEST_CODE = 111;
    private FirebaseListAdapter<Message> adapter;
    private ListView listView;
    private String loggedInUserName = "";

    Button stop;

    ArrayList<String> arrayList;
    int c = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_meeting);

        FirebaseApp.initializeApp(this);

        Window window = ChatMeeting.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(ChatMeeting.this,R.color.mystatus));

        arrayList = new ArrayList<>();

        final EditText input = (EditText) findViewById(R.id.input);

        stop = (Button) findViewById(R.id.stop);

        listView = (ListView)findViewById(R.id.list1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        showAllOldMessages();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatMeeting.this, "Please enter some texts!", Toast.LENGTH_SHORT).show();
                } else {

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            UserPost userPost = dataSnapshot.getValue(UserPost.class);

                            if (userPost.getPost().equals("Team Member")) {

                                stop.setVisibility(View.GONE);

                            }

                            FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child("Messages")
                                    .child(userPost.getTeamname())
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


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ChatMeeting.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are You Sure?")
                        .setMessage("Do you want to end this meeting?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                finish();
                                startActivity(new Intent(ChatMeeting.this, MeetingReport.class));
                                c = 1;

                            }
                        })

                        .setNegativeButton("No", null)
                        .show();
            }
        });

        if(c == 1){

            finish();

        }

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

                    adapter = new MyListAdapter3(ChatMeeting.this, Message.class, R.layout.outgoingmessage,
                            FirebaseDatabase.getInstance().getReference().child("Messages").child(userPost.getTeamname()));
                }
                listView.setAdapter(adapter);

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

