package com.example.isafe.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.isafe.R;

import static android.app.Activity.RESULT_OK;

public class EventChecklist extends Fragment {

    View ve;

    ImageView eimage, eimage2, eimage3, doc, doc3,doc2;

    final int CAMERA_REQUEST = 1;


    Uri photoURI;
    String path;

    Bitmap ephoto, ephoto2, ephoto3;

    int count;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ve = inflater.inflate(R.layout.event_checklist, container, false);


        return ve;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {

        }

    }


}
