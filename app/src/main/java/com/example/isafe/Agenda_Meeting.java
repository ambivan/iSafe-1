package com.example.isafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.isafe.Classes.UserPost;
import com.example.isafe.Fragments.Meetings;
import com.example.isafe.Fragments.comp;
import com.example.isafe.Fragments.opp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Agenda_Meeting extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView chat;

    Button invite;
    Button startmeet;

    ListView listView2;
    ArrayList<String> arrayList;
    ArrayList<Integer> pic;
    ArrayList<Integer> tick;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.isafe.R.layout.activity_agenda__meeting);

        Toolbar toolbar = (Toolbar) findViewById(com.example.isafe.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        arrayList = new ArrayList<>();

        chat = (ImageView) findViewById(com.example.isafe.R.id.chatnotif);
        invite = (Button) findViewById(com.example.isafe.R.id.inviteall);
        startmeet = (Button) findViewById(com.example.isafe.R.id.startmeet);
        listView2 = (ListView) findViewById(com.example.isafe.R.id.list2);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invite.setVisibility(View.INVISIBLE);

                startmeet.setVisibility(View.VISIBLE);
                startmeet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(Agenda_Meeting.this, ChatMeeting.class));

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

                UserPost userPost = dataSnapshot.getValue(UserPost.class);

                if (userPost.getPost().equals("Team Leader")){


                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.navbar_teamlead);

                } else if (userPost.getPost().equals("Team Member")) {

                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.navbar_teammember);

                }else{

                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_home_page2_drawer);

                }

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

                                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Agenda_Meeting.this, android.R.layout.simple_list_item_1, arrayList);

                                            System.out.println(arrayList);
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


        DrawerLayout drawer = (DrawerLayout) findViewById(com.example.isafe.R.id.drawer_layout3);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.example.isafe.R.string.navigation_drawer_open, com.example.isafe.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(com.example.isafe.R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.example.isafe.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment frag1 = null;

        if (id == com.example.isafe.R.id.Home){
            finish();
            HomePageActivity.frag = 0;
            startActivity(new Intent(Agenda_Meeting.this, HomePageActivity.class));
        }
        if (id == com.example.isafe.R.id.competitions) {
            frag1 = new comp();

        } else if (id == com.example.isafe.R.id.opportunities) {
            frag1 = new opp();

        } else if (id == com.example.isafe.R.id.RSA) {

        } else if (id == com.example.isafe.R.id.goodSamaritan) {

            frag1 = new GoodSamaritan();

        } else if (id == com.example.isafe.R.id.signout) {

            FirebaseAuth.getInstance().signOut();

        } else if (id == com.example.isafe.R.id.Meetings) {

            frag1 = new Meetings();

        }else if (id == com.example.isafe.R.id.events) {

            frag1 = new EventChecklist();

        }else if (id == com.example.isafe.R.id.Reimbursement) {

            frag1 = new Reimbursement();

        }

        if (frag1 != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(com.example.isafe.R.id.content_frame, frag1).addToBackStack("My fragments");
            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(com.example.isafe.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
