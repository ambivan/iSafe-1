package com.example.isafe;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CamActivity extends AppCompatActivity {

    Button cam, attach;
    static final int CAMERA_REQUEST = 1;

    static ImageView image, image2, image3 ;
    String path;
    static Bitmap photo, photo2, photo3;
    static int i=0;
    TextView upload ;



    int count = 0;
    Intent camIntent, image2Int, image3Int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        upload = (TextView) findViewById(R.id.upload);

        cam = (Button) findViewById(R.id.clickpic);
        image = (ImageView) findViewById(R.id.image);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);

        attach = (Button) findViewById(R.id.attach);

        image2.setVisibility(View.INVISIBLE);
        image3.setVisibility(View.INVISIBLE);


        if (Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }


        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity.frag = 3;

                camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                pictake(camIntent);
                image2.setVisibility(View.VISIBLE);
                image3.setVisibility(View.VISIBLE);
                count = 1;
                upload.setVisibility(View.INVISIBLE);
                cam.setVisibility(View.INVISIBLE);
                attach.setVisibility(View.VISIBLE);

            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image2.setBackgroundResource(R.drawable.transbg);
                image2Int = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictake(image2Int);
                count++;
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attach.setVisibility(View.VISIBLE);
                image3.setBackgroundResource(R.drawable.transbg);
                image2.setBackgroundResource(R.drawable.transbg);

                image3Int = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictake(image3Int);
                count ++;
            }
        });


        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count <3){
                    Toast.makeText(CamActivity.this, "Please take 3 photos!", Toast.LENGTH_SHORT).show();
                }else{

                    i=1;

                    startActivity(new Intent(CamActivity.this, HomePageActivity.class));
                }
            }
        });

    }

    private void pictake(Intent intent) {

        if(intent.resolveActivity(getPackageManager()) != null){
            File photofile = createPhotoFile();

            if (photofile != null){
                path = photofile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(CamActivity.this, "com.example.isafe.fileprovider", photofile);
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
                    photo = BitmapFactory.decodeFile(path);

                    image.setImageBitmap(photo);
                }else if (count==2){
                    photo2 = BitmapFactory.decodeFile(path);

                    image2.setImageBitmap(photo2);
                }else if (count==3){
                    photo3 = BitmapFactory.decodeFile(path);

                    image3.setImageBitmap(photo3);
                }


            }
        }

    }
}
