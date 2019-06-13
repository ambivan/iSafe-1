package com.example.isafe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
        return new Profile();

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