package com.example.isafe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.isafe.Adapters.Pager;
import com.example.isafe.Classes.UserPost;
import com.example.isafe.Fragments.EventChecklist;
import com.example.isafe.Fragments.Feedback;
import com.example.isafe.Fragments.GoodSamaritan;
import com.example.isafe.Fragments.Projects;
import com.example.isafe.Fragments.Reimbursement;
import com.example.isafe.Fragments.RoadSafetyAudit;
import com.example.isafe.Fragments.comp;
import com.example.isafe.Fragments.opp;
import com.example.isafe.R;
import com.example.isafe.Services.NotificationService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePageActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, OnNavigationItemSelectedListener{

    private ViewPager viewPager;
    public TabLayout tabLayout;

    ImageView notif;

    NotificationService notificationService;


    FirebaseAuth auth;
    FirebaseUser user;
    String userid;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference;
    UserPost userPost;

    TextView profile_name;
    TextView badge;
    View header;
    ImageView profile;

    NavigationView navigationView;
    public static int h;

    int p ;

    public static int frag = 0;

    private int[] imageResId = {
            R.drawable.profile,
            R.drawable.chat,
            R.drawable.acc,
            R.drawable.attendance,
            R.drawable.newsfeed};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        FirebaseApp.initializeApp(this);

        notif = (ImageView) findViewById(R.id.icon1);
        badge = findViewById(R.id.badge);

        Window window = HomePageActivity.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(HomePageActivity.this,R.color.mystatus));

        DatabaseReference e = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Status");

        e.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                badge.setText(String.valueOf(dataSnapshot.getValue()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Intent intent = new Intent(HomePageActivity.this, NotificationService.class);
        startService(intent);

        badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Status")
                        .setValue("0");



            }
        });


        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();

                if (user != null){

                    userid = user.getUid();

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            userPost = dataSnapshot.getValue(UserPost.class);

                            if (userPost != null) {
                                if (userPost.getPost().equals("Team Leader")){

                                    navigationView.getMenu().clear();
                                    navigationView.inflateMenu(R.menu.navbar_teamlead);
                                    h = 1;

                                } else if (userPost.getPost().equals("Team Member")) {
                                    navigationView.getMenu().clear();
                                    navigationView.inflateMenu(R.menu.navbar_teammember);
                                    h = 1;
                                }else{
                                    navigationView.getMenu().clear();
                                    navigationView.inflateMenu(R.menu.activity_home_page2_drawer);
                                    h = 0;
                                }

                                header = navigationView.getHeaderView(0);
                                profile_name = (TextView) header.findViewById(R.id.profile_name);
                                profile = (ImageView) header.findViewById(R.id.profile_pic);

                                profile_name.setText(userPost.getName());

                                DatabaseReference d = FirebaseDatabase.getInstance()
                                        .getReference()
                                        .child("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("Profile URL");

                                d.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        System.out.println(dataSnapshot.getValue());

                                        if (dataSnapshot.getValue() != null) {

                                            Glide.with(HomePageActivity.this).load(dataSnapshot.getValue()).into(profile);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else {
                    finish();
                    startActivity(new Intent(HomePageActivity.this, MainActivity.class));
                }

            }
        };
        auth.addAuthStateListener(authStateListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((OnNavigationItemSelectedListener) this);

        TabLayout.Tab[] tabs = new TabLayout.Tab[5];

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        for (int i = 0; i<5; i++){
            tabLayout.addTab(tabLayout.newTab());
             tabs[i] = tabLayout.getTabAt(i);
        }


        Pager viewPagerAdapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i<5; i++){
            tabs[i].setIcon(imageResId[i]);
            tabs[i].setCustomView(R.layout.tabl);
        }
      setFragment();
    }

    private void setFragment() {

        if (frag == 0 || frag == 1){
            viewPager.setCurrentItem(0);
        }else if (frag == 2){
            viewPager.setCurrentItem(1);
        }else if (frag == 3){
            viewPager.setCurrentItem(2);
        }else if (frag == 4){
            viewPager.setCurrentItem(3);
        }else if (frag == 5){
            viewPager.setCurrentItem(4);
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        frag = tab.getPosition();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        else{
            super.onBackPressed();
        }

        }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment frag1 = null;

        if (id == R.id.Home){
            finish();
            frag = 0;
            startActivity(new Intent(HomePageActivity.this, HomePageActivity.class));
        }
        if (id == R.id.competitions) {
            frag1 = new comp();

        } else if (id == R.id.opportunities) {
            frag1 = new opp();

        } else if (id == R.id.RSA) {

            frag1 = new RoadSafetyAudit();

        } else if (id == R.id.goodSamaritan) {

            frag1 = new GoodSamaritan();

        } else if (id == R.id.signout) {

            auth.signOut();

        } else if (id == R.id.events) {

            frag1 = new EventChecklist();

        }else if (id == R.id.Reimbursement) {

            frag1 = new Reimbursement();

        } else if (id == R.id.feedback){
            frag1 = new Feedback();
        } else if (id == R.id.projects)
            frag1 = new Projects();


        if (frag1 != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, frag1).addToBackStack("My fragments");
            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authStateListener);
    }

}
