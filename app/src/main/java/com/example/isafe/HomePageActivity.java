package com.example.isafe;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.isafe.Profile.desig;

public class HomePageActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener{


    private ViewPager viewPager;


    TabLayout.Tab t;

    static int frag = 0;

    private TabLayout tabLayout;

    private int[] imageResId = {

            R.drawable.profile,

            R.drawable.chat,

            R.drawable.accident,

            R.drawable.attendance,

            R.drawable.newsfeed};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);


        TabLayout.Tab[] tabs = new TabLayout.Tab[5];

        FirebaseApp.initializeApp(this);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Signout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.competitions) {
            // Handle the camera action
        } else if (id == R.id.opportunities) {

        } else if (id == R.id.RSA) {

        } else if (id == R.id.goodSamaritan) {

        } else if (id == R.id.signout) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
