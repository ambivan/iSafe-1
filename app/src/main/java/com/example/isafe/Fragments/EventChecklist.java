package com.example.isafe.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.isafe.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        eimage = (ImageView) ve.findViewById(R.id.eimage);
        eimage2 = (ImageView) ve.findViewById(R.id.eimage2);
        eimage3 = (ImageView) ve.findViewById(R.id.eimage3);
        doc = (ImageView) ve.findViewById(R.id.edoc);
        doc2 = (ImageView) ve.findViewById(R.id.edoc2);
        doc3= (ImageView) ve.findViewById(R.id.edoc3);


        eimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictake(in1);
                count = 1;


            }
        });

        eimage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictake(in2);
                count = 2;
            }
        });

        eimage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictake(in3);
                count = 3;


            }
        });

        return ve;
    }

    private void pictake(Intent intent) {

        if(intent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null){
            File photofile = createPhotoFile();

            if (photofile != null){

                path = photofile.getAbsolutePath();

                photoURI = FileProvider.getUriForFile(getActivity(), "com.example.isafe.fileprovider", photofile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(intent, CAMERA_REQUEST);

            }
        }

    }

    private File createPhotoFile() {

        String name = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
        File storagedir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;

        try {
            image = File.createTempFile(name, ".jpg", storagedir);

        }catch (IOException e){
            Log.d("mine", e.toString());
        }

        return image;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {

                if(count==1){

                    ephoto = BitmapFactory.decodeFile(path);


                    eimage.setImageBitmap(ephoto);


                }else if (count==2){

                    ephoto2 = BitmapFactory.decodeFile(path);

                    eimage2.setImageBitmap(ephoto2);

                }else if (count==3){

                    ephoto3 = BitmapFactory.decodeFile(path);

                    eimage3.setImageBitmap(ephoto3);

                }

            }

        }

    }


}
