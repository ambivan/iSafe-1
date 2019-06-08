package com.example.isafe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class opp extends Fragment {

  ExpandableListView expandableListView;
  ExpandableListAdapter expandableListAdapter;
  List<String> expandableListTitle;
  HashMap<String, List<String>> expandableListDetail;

  View v2;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //returning our layout file
    v2 = inflater.inflate(R.layout.opp, container, false);


    expandableListView = (ExpandableListView) v2.findViewById(R.id.expandableview);
    expandableListDetail = oppurtunitiesData.getData();
    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
    expandableListAdapter = new oppurtunitiesAdapter(getContext(), expandableListTitle, expandableListDetail);
    expandableListView.setAdapter(expandableListAdapter);
    expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

      @Override
      public void onGroupExpand(int groupPosition) {

      }
    });

    expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

      @Override
      public void onGroupCollapse(int groupPosition) {

      }
    });

    expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
      @Override
      public boolean onChildClick(ExpandableListView parent, View v,
                                  int groupPosition, int childPosition, long id) {

        return false;
      }
    });

    return v2;
  }
}