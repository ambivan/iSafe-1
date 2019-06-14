package com.example.isafe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AgendaMeeting extends AppCompatActivity {

    ImageView chat;

    Button invite;
    Button startmeet;

    ListView listView2;
    ArrayList<String> arrayList;
    ArrayList<Integer> pic;
    ArrayList<Integer> tick;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_meeting);

        arrayList = new ArrayList<>();


        chat = (ImageView) findViewById(R.id.chatnotif);
        invite = (Button) findViewById(R.id.inviteall);
        startmeet = (Button) findViewById(R.id.startmeet);
        listView2 = (ListView) findViewById(R.id.list2);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invite.setVisibility(View.INVISIBLE);

                startmeet.setVisibility(View.VISIBLE);
                startmeet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(AgendaMeeting.this, ChatMeeting.class));

                    }
                });

            }
        });


        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child(userid);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue());

                List<HashMap<String, ArrayList<String>>> aList = new ArrayList<HashMap<String, ArrayList<String>>>();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String key = ds.getKey();
                    System.out.println("hi" + key);

                    if (!key.equals("name")&&!key.equals("post")){

                        if (key != null) {
                            DatabaseReference a = dbref.child(key);

                            a.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    UserPost userPost = dataSnapshot.getValue(UserPost.class);

                                    System.out.println(userPost.getName());

                                    if (userPost.getName()== null){

                                        arrayList.add("You have no team members yet.");

                                    }else {

                                        arrayList.add(userPost.getName());

                                        if (arrayList != null) {

                                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AgendaMeeting.this, android.R.layout.simple_list_item_1, arrayList);

                                            listView2.setAdapter(arrayAdapter);

                                        }

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
