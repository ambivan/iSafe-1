package com.example.isafe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.isafe.Classes.UserPost;
import com.example.isafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IndividualChat extends AppCompatActivity {

    ListView listView3;
    ArrayList<String> arrayList2;

    static String name, unique ;

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_chat);

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        arrayList2 = new ArrayList<>();
        listView3 = (ListView) findViewById(R.id.list2);

        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue());

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String key = ds.getKey();
                    System.out.println("hi" + key);

                    if (!key.equals("name")
                            &&!key.equals("post")
                            && !key.equals("Meeting Reports")
                            &&!key.equals("teamname")
                            && !key.equals("Registered Events")
                            && !key.equals("Domain")
                            && !key.equals("Events")
                            &&!key.equals("Liked Events")
                            &&!key.equals("Reimbursements")
                            && !key.equals("Profile URL")){

                        if (key != null) {
                            DatabaseReference a = dbref.child(key);
                            System.out.println("k" + key);

                            a.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    final UserPost userPost = dataSnapshot.getValue(UserPost.class);

                                    System.out.println(userPost.getName());

                                    if (userPost.getName()== null){

                                        arrayList2.add("You have no team members yet.");
                                        System.out.println("hey " + arrayList2);

                                    }else {

                                        arrayList2.add(userPost.getName());

                                    }

                                    ArrayAdapter<String > arrayAdapter = new ArrayAdapter<>(IndividualChat.this, android.R.layout.simple_list_item_1, arrayList2);
                                    System.out.println(arrayList2);
                                    listView3.setAdapter(arrayAdapter);

                                    listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            System.out.println("hell" + arrayList2.get(position));

                                            System.out.println(userPost.getName());

                                            name = arrayList2.get(position);

                                            DatabaseReference u =FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                                            System.out.println(u.getKey());

                                            unique = arrayList2.get(position);

                                            startActivity(new Intent(IndividualChat.this, Chatone.class));

                                        }
                                    });

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
