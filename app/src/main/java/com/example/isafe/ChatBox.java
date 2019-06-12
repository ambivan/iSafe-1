package com.example.isafe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChatBox extends Fragment {


    View vc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        vc = inflater.inflate(R.layout.chatbox, container, false);

        return vc;


    }

}
