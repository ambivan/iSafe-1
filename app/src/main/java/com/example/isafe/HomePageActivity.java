package com.example.isafe;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.isafe.Profile.desig;

public class HomePageActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener{


    private ViewPager viewPager;

     FirebaseAuth auth;

     FirebaseAuth.AuthStateListener authStateListener;

    static int frag = 0;

    private TabLayout tabLayout;

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

        FirebaseApp.initializeApp(this);


        desig = (TextView) findViewById(R.id.designation);

//        if (SignupActivity.i == 1 || LoginActivity.i == 1) {
//
//            desig.setText("Volunteer");
//
//        } else if (SignupActivity.i == 2 || LoginActivity.i == 2) {
//
//            desig.setText("Team Lead");
//
//        } else if (SignupActivity.i == 3 || LoginActivity.i == 3) {
//
//            desig.setText("Team Member");
//
//        }

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);


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
        } else {
            super.onBackPressed();
        }

        moveTaskToBack(true);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment frag = null;

        if (id == R.id.competitions) {

            frag = new comp();


        } else if (id == R.id.opportunities) {

//            frag = new opp();

        } else if (id == R.id.RSA) {

        } else if (id == R.id.goodSamaritan) {

        } else if (id == R.id.signout) {

            auth.signOut();

        }

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, frag);
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
