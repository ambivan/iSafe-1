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
        setContentView(R.layout.activity_home_page);


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





}
