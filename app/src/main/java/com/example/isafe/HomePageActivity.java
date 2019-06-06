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
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
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

public class HomePageActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{


    private ViewPager viewPager;


    TabLayout.Tab t;

    private TabLayout tabLayout;

    private int[] imageResId = {

            R.drawable.newsfeed,

            R.drawable.attendance,

            R.drawable.accident,

            R.drawable.chat,

            R.drawable.profile};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        FirebaseApp.initializeApp(this);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        for (int i = 0; i<5; i++){
            tabLayout.addTab(tabLayout.newTab());
        }


        Pager viewPagerAdapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);





        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                t = tab;

                viewPager.setCurrentItem(tab.getPosition());
                tab.setIcon(imageResId[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                t = tab;
                tab.setIcon(imageResId[tab.getPosition()]);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {



    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.setIcon(imageResId[tab.getPosition()]);

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }



}
