package com.solve.isafe.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.solve.isafe.Activities.HomePageActivity;
import com.solve.isafe.Fragments.Assist;
import com.solve.isafe.Fragments.Attendance;
import com.solve.isafe.Fragments.NewsFeed;
import com.solve.isafe.Fragments.teamProfile;
import com.solve.isafe.Profile;
import com.solve.isafe.Fragments.ReportAccident;

public class Pager extends FragmentPagerAdapter {
  //integer to count number of tabs
  int tabCount;

  //Constructor to the class
  public Pager(FragmentManager fm, int tabCount) {
    super(fm);
    //Initializing tab count
    this.tabCount= tabCount;
  }

  //Overriding method getItem
  @Override
  public Fragment getItem(int position) {
    //Returning the current tabs
    switch (position) {
      case 0:
        return new NewsFeed();

      case 1:
        return new Attendance();

      case 2:
        return new ReportAccident();

      case 3:
        return new Assist();

      case 4:
        if (HomePageActivity.h == 1){

            return new teamProfile();

        } else{

            return new Profile();

        }

      default:
        return null;
    }
  }

  //Overriden method getCount to get the number of tabs
  @Override
  public int getCount() {
    return tabCount;
  }
}