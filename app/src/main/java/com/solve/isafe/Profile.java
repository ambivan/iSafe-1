package com.solve.isafe;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.solve.isafe.Classes.UserPost;
import com.solve.isafe.Classes.Utility;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment {

    View v1;

    TextView desig, profile_name;

    private FirebaseAuth auth;

    String userChoosenTask, path, userid;

    Intent intent;

    Uri photoURI;

    DatabaseReference databaseReference;

    ImageView profilpic;

    FirebaseAuth.AuthStateListener authStateListener;
    private final int REQUEST_CAMERA = 3;
    private final int SELECT_FILE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v1 = inflater.inflate(R.layout.tab5, container, false);

        desig = (TextView) v1.findViewById(R.id.designation);
        profile_name = (TextView) v1.findViewById(R.id.name_pro);
        profilpic = (ImageView) v1.findViewById(R.id.profpic);

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = auth.getCurrentUser();

                userid = user.getUid();

                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        UserPost userPost = dataSnapshot.getValue(UserPost.class);

                        desig.setText(userPost.getPost());
                        profile_name.setText(userPost.getName());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        };

        auth.addAuthStateListener(authStateListener);

        DatabaseReference d = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Profile URL");

        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getValue() != null) {

                        Glide.with(getContext()).load(dataSnapshot.getValue()).into(profilpic);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        profilpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        return v1;
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

    private void cameraIntent() {
        intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        pictake(intent);
    }

    private void pictake(Intent intent) {

        if (intent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
            File photofile = createPhotoFile();

            if (photofile != null) {

                path = photofile.getAbsolutePath();

                photoURI = FileProvider.getUriForFile(getContext(), "com.solve.isafe.fileprovider", photofile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(intent, REQUEST_CAMERA);

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

    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, SELECT_FILE);
    }


    @Override
    public void onStart() {
        super.onStart();

        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        auth.removeAuthStateListener(authStateListener);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {

                    Uri selected = photoURI;
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

                                    String url = uri.toString();
                                    FirebaseDatabase.getInstance()
                                            .getReference()
                                            .child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Profile URL")
                                            .setValue(url);


                                    Glide.with(getContext()).load(url).into(profilpic);

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
                                    Glide.with(getContext()).load(url).into(profilpic);
                                    mProgress.dismiss();

                                }
                            });
                        }

                    });
                }
                break;
        }
    }
}

