package com.example.isafe.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.isafe.R;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

  private Context context;
  private List<String> expandableListTitle;
  private HashMap<String, List<String>> expandableListDetail;

  TextView listTitleTextView;

  public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                     HashMap<String, List<String>> expandableListDetail) {
    this.context = context;
    this.expandableListTitle = expandableListTitle;
    this.expandableListDetail = expandableListDetail;
  }

  @Override
  public Object getChild(int listPosition, int expandedListPosition) {
    return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
            .get(expandedListPosition);
  }

  @Override
  public long getChildId(int listPosition, int expandedListPosition) {
    return expandedListPosition;
  }

  @Override
  public View getChildView(int listPosition, final int expandedListPosition,
                           boolean isLastChild, View convertView, ViewGroup parent) {
    final String expandedListText = (String) getChild(listPosition, expandedListPosition);


    if (convertView == null) {
      LayoutInflater layoutInflater = (LayoutInflater) this.context
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = layoutInflater.inflate(com.example.isafe.R.layout.childtext, null);

      Button know = convertView.findViewById(R.id.readmore);

      know.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ww2.road-safety.co.in/home/"));
            context.startActivity(browserIntent);
        }
      });

    }

    TextView expandedListTextView = (TextView) convertView.findViewById(com.example.isafe.R.id.expandedListItem);

    expandedListTextView.setText(expandedListText);

    return convertView;
  }

  @Override
  public int getChildrenCount(int listPosition) {
    return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
            .size();
  }

  @Override
  public Object getGroup(int listPosition) {
    return this.expandableListTitle.get(listPosition);
  }

  @Override
  public int getGroupCount() {
    return this.expandableListTitle.size();
  }

  @Override
  public long getGroupId(int listPosition) {
    return listPosition;
  }

  @Override
  public View getGroupView(int listPosition, boolean isExpanded,
                           View convertView, ViewGroup parent) {

    String listTitle = (String) getGroup(listPosition);
    if (convertView == null) {
      LayoutInflater layoutInflater = (LayoutInflater) this.context.
              getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = layoutInflater.inflate(com.example.isafe.R.layout.listheading, null);


    }
    TextView listTitleTextView = (TextView) convertView
            .findViewById(com.example.isafe.R.id.listTitle);
    listTitleTextView.setTypeface(null, Typeface.BOLD);

    listTitleTextView.setText(listTitle);
    return convertView;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int listPosition, int expandedListPosition) {
    return true;
  }
}