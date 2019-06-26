package com.example.isafe;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isafe.Activities.HomePageActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CamActivity extends AppCompatActivity {

    Button cam, attach;
    private int CAMERA_REQUEST = 1;


    DatabaseReference d;

    LinearLayout l ;


    static ImageView image, image2, image3;
    String path, userid;
    public static Bitmap photo;
    public static Bitmap photo2;
    public static Bitmap photo3;
    public static int i = 0;
    TextView upload;

    UploadTask uploadTask;

    int count;
    Intent camIntent, image2Int, image3Int;

    Uri photoURI;

    ByteArrayOutputStream baos;
    byte[] bytes;

    private FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;

    StorageReference storageref, imagesref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        baos = new ByteArrayOutputStream();

        d =  FirebaseDatabase.getInstance().getReference();

        l = findViewById(R.id.twocam);

        bytes = baos.toByteArray();

        storageref = FirebaseStorage.getInstance().getReference();

        HomePageActivity.frag = 3;

        cam = (Button) findViewById(R.id.clickpic);
        image = (ImageView) findViewById(R.id.image);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);

        attach = (Button) findViewById(R.id.attach);

        image2.setVisibility(View.INVISIBLE);
        image3.setVisibility(View.INVISIBLE);

        upload = (TextView) findViewById(R.id.upload);


        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = auth.getCurrentUser();

                userid = user.getUid();

            }
        };


        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 1;

                camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictake(camIntent);

                l.setVisibility(View.VISIBLE);
                image2.setVisibility(View.VISIBLE);
                image3.setVisibility(View.VISIBLE);
                upload.setVisibility(View.INVISIBLE);
                cam.setVisibility(View.INVISIBLE);
                attach.setVisibility(View.VISIBLE);

            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                image2.setBackgroundResource(R.drawable.transbg);
                image2Int = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictake(image2Int);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                attach.setVisibility(View.VISIBLE);
                image3.setBackgroundResource(R.drawable.transbg);
                image2.setBackgroundResource(R.drawable.transbg);
                image3Int = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictake(image3Int);


            }
        });


        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count < 3) {

                    Toast.makeText(CamActivity.this, "Please take 3 photos!", Toast.LENGTH_SHORT).show();

                } else {

                    i = 1;

                    startActivity(new Intent(CamActivity.this, HomePageActivity.class));

                }
            }
        });

    }

    private void pictake(Intent intent) {

        if (intent.resolveActivity(getPackageManager()) != null) {
            File photofile = createPhotoFile();

            if (photofile != null) {

                path = photofile.getAbsolutePath();

                photoURI = FileProvider.getUriForFile(CamActivity.this, "com.example.isafe.fileprovider", photofile);

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

        } catch (IOException e) {
            Log.d("mine", e.toString());
        }

        return image;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {

                if (count == 1) {

                    photo = BitmapFactory.decodeFile(path);

                    photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    bytes = baos.toByteArray();

                    imagesref = storageref.child(userid).child("images/" + System.currentTimeMillis());

                    uploadTask = imagesref.putBytes(bytes);

                    uploadTask = imagesref.putFile(photoURI);

                    imagesref.putFile(photoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imagesref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    System.out.println(uri.toString());

                                    d.child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Accident Report")
                                            .child("Photos")
                                            .child("1")
                                            .setValue(uri.toString());

                                }
                            });

                        }
                    });

                    image.setImageBitmap(photo);


                } else if (count == 2) {

                    photo2 = BitmapFactory.decodeFile(path);

                    photo2.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    bytes = baos.toByteArray();

                    imagesref = storageref.child(userid).child("images/" + System.currentTimeMillis());

                    uploadTask = imagesref.putBytes(bytes);

                    uploadTask = imagesref.putFile(photoURI);


                    imagesref.putFile(photoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imagesref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    System.out.println(uri.toString());

                                    d.child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Accident Report")
                                            .child("Photos")
                                            .child("2")
                                            .setValue(uri.toString());

                                }
                            });

                        }
                    });


                    image2.setImageBitmap(photo2);

                } else if (count == 3) {

                    photo3 = BitmapFactory.decodeFile(path);

                    photo3.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    bytes = baos.toByteArray();

                    imagesref = storageref.child(userid).child("images/" + System.currentTimeMillis());

                    uploadTask = imagesref.putBytes(bytes);

                    uploadTask = imagesref.putFile(photoURI);

                    imagesref.putFile(photoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imagesref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    System.out.println(uri.toString());

                                    d.child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Accident Report")
                                            .child("Photos")
                                            .child("3")
                                            .setValue(uri.toString());

                                }
                            });

                        }
                    });


                    image3.setImageBitmap(photo3);

                }

            }

        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}