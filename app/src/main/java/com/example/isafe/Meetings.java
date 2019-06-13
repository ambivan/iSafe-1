package com.example.isafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Meetings extends Fragment {


  View vm;
  Button start;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


    vm = inflater.inflate(R.layout.meetings, container, false);

    start = (Button) vm.findViewById(R.id.start);

    start.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), AgendaMeeting.class));
      }
    });

    return vm;

  }

}