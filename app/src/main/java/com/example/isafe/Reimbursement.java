package com.example.isafe;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.example.isafe.CamActivity.CAMERA_REQUEST;

public class Reimbursement extends Fragment {

    View vr;
    ImageView bill;
    Button attach, send;

    Uri photoURI;

    String path;

    Bitmap photobill;

    Intent camIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vr = inflater.inflate(R.layout.reimbursement, container, false);

        bill = (ImageView) vr.findViewById(R.id.bill);
        send = (Button) vr.findViewById(R.id.attachedsend);

        if (Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }

        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Blehhhh");
                camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictake(camIntent);
                
                send.setVisibility(View.VISIBLE);
                
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Your bill has been shared with us.", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getActivity(), HomePageActivity.class));


                    }
                });

            }
        });

        return  vr;

    }

    private void pictake(Intent intent) {

        System.out.println("Blehhhh");

        if(intent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null){
            File photofile = createPhotoFile();
            System.out.println("Blehhhh");


            if (photofile != null){

                path = photofile.getAbsolutePath();

                photoURI = FileProvider.getUriForFile(getContext(), "com.example.isafe.fileprovider", photofile);

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

                    photobill = BitmapFactory.decodeFile(path);
                    bill.setImageBitmap(photobill);

            }

        }

    }

}
