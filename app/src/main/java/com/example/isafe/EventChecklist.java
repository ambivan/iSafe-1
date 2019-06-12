package com.example.isafe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EventChecklist extends Fragment {

    View ve;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ve = inflater.inflate(R.layout.event_checklist, container, false);



        return ve;
    }

}
