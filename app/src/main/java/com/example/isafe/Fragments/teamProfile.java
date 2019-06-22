package com.example.isafe.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.isafe.Adapters.profileAdapter;
import com.example.isafe.Classes.UserPost;
import com.example.isafe.Classes.Utility;
import com.example.isafe.Classes.profileM;
import com.example.isafe.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class teamProfile extends Fragment {


    View vt;

    TextView team_name;
    RecyclerView recyclerView;
    private ArrayList<profileM> profileMArrayList;
    TextView prof_name;
    private profileAdapter adapter;

    Uri photoURI;
    String path, userChoosenTask;

    ImageView pro_pic;

    Intent intent;

    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vt = inflater.inflate(com.example.isafe.R.layout.tab5team, container, false);

        team_name = (TextView) vt.findViewById(R.id.team_name);
        recyclerView = (RecyclerView) vt.findViewById(R.id.recyclerViewh);
        prof_name = (TextView) vt.findViewById(R.id.pro_name);
        pro_pic = (ImageView) vt.findViewById(R.id.prof_ilepic);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        UserPost userPost = dataSnapshot.getValue(UserPost.class);

                        if (userPost != null) {
                            prof_name.setText(userPost.getName());
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


        pro_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        DatabaseReference d = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Profile URL");

        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println(dataSnapshot.getValue());

                if (dataSnapshot.getValue() != null) {

                    Glide.with(getContext()).load(dataSnapshot.getValue()).into(pro_pic);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        profileM[] profileMS = new profileM[]{
                new profileM("Shambhavi", R.drawable.profile),
                new profileM("Varun", R.drawable.profile),
                new profileM("Mansi", R.drawable.profile),
                new profileM("Manasvi", R.drawable.profile)
        };

        adapter = new profileAdapter(getContext(), profileMS);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        team_name.setText("Roadies");

        return vt;

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        System.out.println("Yes");
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        System.out.println("Yes");

        intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        pictake(intent);

    }

    private void pictake(Intent intent) {

        System.out.println("Yes");


        if (intent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
            File photofile = createPhotoFile();

            if (photofile != null) {

                path = photofile.getAbsolutePath();

                Uri photoURI = FileProvider.getUriForFile(getContext(), "com.example.isafe.fileprovider", photofile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(intent, REQUEST_CAMERA);
            }
        }
    }

    private File createPhotoFile() {

        System.out.println("Yes");

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

    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {

                    System.out.println("Yes");

                    Uri selected = Uri.parse(String.valueOf(data.getData()));
                    String url = selected.getLastPathSegment();

                    final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                            .child("Profile Pictures")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(url);

                    storageReference.putFile(selected).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    ProgressDialog mProgress = new ProgressDialog(getActivity());
                                    mProgress.setCancelable(false);
                                    mProgress.setCanceledOnTouchOutside(false);
                                    mProgress.setTitle("Creating Account");
                                    mProgress.setMessage("Please wait while account is being created...");
                                    mProgress.show();
                                    String url = uri.toString();
                                    FirebaseDatabase.getInstance()
                                            .getReference()
                                            .child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Profile URL")
                                            .setValue(url);
                                    System.out.println(url);
                                    Glide.with(getContext()).load(url).into(pro_pic);
                                    mProgress.dismiss();

                                }
                            });

                        }

                    });
                }

                break;
            case SELECT_FILE:
                if (resultCode == RESULT_OK) {

                    Uri selectedImage = Uri.parse(String.valueOf(data.getData()));
                    String url = selectedImage.getLastPathSegment();
                    final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                            .child("Profile Pictures")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(url);

                    storageReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    ProgressDialog mProgress = new ProgressDialog(getActivity());
                                    mProgress.setCancelable(false);
                                    mProgress.setCanceledOnTouchOutside(false);
                                    mProgress.setTitle("Creating Account");
                                    mProgress.setMessage("Please wait while account is being created...");
                                    mProgress.show();
                                    String url = uri.toString();
                                    FirebaseDatabase.getInstance()
                                            .getReference()
                                            .child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Profile URL")
                                            .setValue(url);
                                    System.out.println(url);
                                    mProgress.dismiss();

                                }
                            });

                        }

                    });
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

}
