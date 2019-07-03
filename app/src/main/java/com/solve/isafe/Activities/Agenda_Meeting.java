package com.solve.isafe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.solve.isafe.Classes.AgendaGen;
import com.solve.isafe.Classes.UserPost;
import com.solve.isafe.Fragments.EventChecklist;
import com.solve.isafe.Fragments.GoodSamaritan;
import com.solve.isafe.Fragments.Reimbursement;
import com.solve.isafe.Fragments.comp;
import com.solve.isafe.Fragments.opp;
import com.solve.isafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Agenda_Meeting extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView chat;

    Button invite;
    Button startmeet;

    ListView listView2;
    ArrayList<String> arrayList;

    Button save;

    EditText agenda;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda__meeting);

        Toolbar toolbar = (Toolbar) findViewById(com.solve.isafe.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Window window = Agenda_Meeting.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(Agenda_Meeting.this, R.color.mystatus));

        arrayList = new ArrayList<>();

        chat = (ImageView) findViewById(R.id.chatnotif);
        invite = (Button) findViewById(R.id.inviteall);
        startmeet = (Button) findViewById(R.id.startmeet);
        listView2 = (ListView) findViewById(R.id.list2);

        invite.setEnabled(false);

        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserPost userPost = dataSnapshot.getValue(UserPost.class);

                if (userPost.getPost().equals("Team Leader")) {


                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.navbar_teamlead);

                } else if (userPost.getPost().equals("Team Member")) {

                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.navbar_teammember);

                } else {

                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_home_page2_drawer);

                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();

                    if (!key.equals("name")
                            && !key.equals("Status")
                            && !key.equals("Notifications")
                            && !key.equals("post")
                            && !key.equals("Feedbacks")
                            && !key.equals("MemberCount")
                            && !key.equals("Meeting Reports")
                            && !key.equals("teamname")
                            && !key.equals("RoadSafetyAudit")
                            && !key.equals("AttendedEvents")
                            && !key.equals("Registered Events")
                            && !key.equals("Domain")
                            && !key.equals("Projects")
                            && !key.equals("Accident Report")
                            && !key.equals("EventDay")
                            && !key.equals("Reimbursements")
                            && !key.equals("Events")
                            && !key.equals("Liked Events")
                            && !key.equals("Profile URL")) {

                        if (key != null) {
                            DatabaseReference a = dbref.child(key);

                            a.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    UserPost userPost = dataSnapshot.getValue(UserPost.class);

                                    if (userPost.getName() == null) {

                                        arrayList.add("You have no team members yet.");

                                    } else {

                                        arrayList.add(userPost.getName());

                                    }

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Agenda_Meeting.this, android.R.layout.simple_list_item_1, arrayList);
                                    listView2.setAdapter(arrayAdapter);
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

        agenda = (EditText) findViewById(R.id.enteragenda);

        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String agen = agenda.getText().toString();

                if (!TextUtils.isEmpty(agen)) {


                    final String userid1 = FirebaseAuth.getInstance().getCurrentUser().getUid();


                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Agendas")
                            .child(String.valueOf(new SimpleDateFormat("ddmyyyy").format(new Date().getTime())));
                    databaseReference.setValue(new AgendaGen(userid1, agen));

                    invite.setEnabled(true);


                    invite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            d.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    {

                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                            String key = ds.getKey();


                                            if (!key.equals("name")
                                                    && !key.equals("post")
                                                    && !key.equals("Notifications")
                                                    && !key.equals("MemberCount")
                                                    && !key.equals("Meeting Reports")
                                                    && !key.equals("teamname")
                                                    && !key.equals("Feedbacks")
                                                    && !key.equals("Status")
                                                    && !key.equals("Registered Events")
                                                    && !key.equals("Reimbursements")
                                                    && !key.equals("Domain")
                                                    && !key.equals("RoadSafetyAudit")
                                                    && !key.equals("AttendedEvents")
                                                    && !key.equals("Projects")
                                                    && !key.equals("Accident Report")
                                                    && !key.equals("EventDay")
                                                    && !key.equals("Liked Events")
                                                    && !key.equals("Profile URL")
                                                    && !key.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {


                                                if (key != null) {


                                                    final DatabaseReference a = FirebaseDatabase.getInstance().getReference().child("Users").child(key);

                                                    a.child("Agendas")
                                                            .setValue(new AgendaGen(userid1, agen));


                                                }

                                            }

                                        }

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            invite.setVisibility(View.GONE);

                            startmeet.setVisibility(View.VISIBLE);
                            startmeet.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    startActivity(new Intent(Agenda_Meeting.this, ChatMeeting.class));


                                }
                            });

                        }


                    });
//

                } else {
                    Toast.makeText(Agenda_Meeting.this, "Please enter Agenda of meeting.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(com.solve.isafe.R.id.drawer_layout3);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.solve.isafe.R.string.navigation_drawer_open, com.solve.isafe.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(com.solve.isafe.R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.solve.isafe.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment frag1 = null;

        if (id == com.solve.isafe.R.id.Home) {
            finish();
            HomePageActivity.frag = 0;
            startActivity(new Intent(Agenda_Meeting.this, HomePageActivity.class));
        }
        if (id == com.solve.isafe.R.id.competitions) {
            frag1 = new comp();

        } else if (id == com.solve.isafe.R.id.opportunities) {
            frag1 = new opp();

        } else if (id == com.solve.isafe.R.id.RSA) {

        } else if (id == com.solve.isafe.R.id.goodSamaritan) {

            frag1 = new GoodSamaritan();

        } else if (id == com.solve.isafe.R.id.signout) {

            FirebaseAuth.getInstance().signOut();

        } else if (id == com.solve.isafe.R.id.events) {

            frag1 = new EventChecklist();

        } else if (id == com.solve.isafe.R.id.Reimbursement) {

            frag1 = new Reimbursement();

        }

        if (frag1 != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(com.solve.isafe.R.id.content_frame, frag1).addToBackStack("My fragments");
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(com.solve.isafe.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
