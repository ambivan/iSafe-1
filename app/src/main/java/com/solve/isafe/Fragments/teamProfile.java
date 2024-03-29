package com.solve.isafe.Fragments;

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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.solve.isafe.Classes.UserPost;
import com.solve.isafe.Classes.Utility;
import com.solve.isafe.Classes.profileM;
import com.solve.isafe.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class teamProfile extends Fragment {

    View vt;

    TextView team_name, team_code;
    RecyclerView recyclerView;
    private ArrayList<profileM> profileMArrayList;
    TextView prof_name;

    String str;

    String path, userChoosenTask;

    Button share;

    ListView listView;

    ImageView pro_pic;

    Intent intent;

    private final int REQUEST_CAMERA = 3;
    private final int SELECT_FILE = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vt = inflater.inflate(com.solve.isafe.R.layout.tab5team, container, false);

        team_name = (TextView) vt.findViewById(R.id.team_name);
        prof_name = (TextView) vt.findViewById(R.id.pro_name);
        pro_pic = (ImageView) vt.findViewById(R.id.prof_ilepic);
        team_code = (TextView) vt.findViewById(R.id.team_code);

        share = vt.findViewById(R.id.sharer);

        listView = vt.findViewById(R.id.listteam);

        final ArrayList<String> arrayList = new ArrayList<>();
        final String[] c = new String[1];


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        final String[] b = new String[1];
        databaseReference
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        UserPost userPost = dataSnapshot.getValue(UserPost.class);

                        str = userPost.getTeamname();

                        team_code.setText(str);

                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent a = new Intent(Intent.ACTION_SEND);
                                a.setType("text/plain");
                                String shareBody = "Hi, join the college team using this code: " + str;
                                String shareSub = "iSAFE";
                                a.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                a.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(a, "Share Using"));
                            }
                        });

                        String[] arrOfStr = str.split("/", 2);

                        for (String a : arrOfStr) {

                            b[0] = a;

                        }
                        String[] arr = b[0].split("/", 2);
                        for (String a : arr) {

                            team_name.setText(a);

                        }

                        if (userPost != null) {
                            prof_name.setText(userPost.getName());

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserPost userPost = dataSnapshot.getValue(UserPost.class);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();

                    if (!key.equals("name")
                            && !key.equals("Status")
                            && !key.equals("Notifications")
                            && !key.equals("post")
                            && !key.equals("MemberCount")
                            && !key.equals("Meeting Reports")
                            && !key.equals("teamname")
                            && !key.equals("RoadSafetyAudit")
                            && !key.equals("AttendedEvents")
                            && !key.equals("Registered Events")
                            && !key.equals("Feedbacks")
                            && !key.equals("Domain")
                            && !key.equals("Projects")
                            && !key.equals("Accident Report")
                            && !key.equals("EventDay")
                            && !key.equals("Reimbursements")
                            && !key.equals("Events")
                            && !key.equals("Liked Events")
                            && !key.equals("Profile URL")) {

                        if (key != null) {
                            DatabaseReference a = dbref.child(key);

                            a.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    UserPost userPost = dataSnapshot.getValue(UserPost.class);

                                    if (userPost.getName() == null) {

                                        arrayList.add("You have no team members yet.");

                                    } else {

                                        arrayList.add(userPost.getName());

                                    }

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                                    listView.setAdapter(arrayAdapter);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }

                            });

                        }

                    }

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

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.getValue() != null) {

                        Glide.with(getContext()).load(dataSnapshot.getValue()).into(pro_pic);


                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        pictake(intent);

    }

    private void pictake(Intent intent) {


        if (intent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
            File photofile = createPhotoFile();

            if (photofile != null) {

                path = photofile.getAbsolutePath();

                Uri photoURI = FileProvider.getUriForFile(getContext(), "com.solve.isafe.fileprovider", photofile);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {

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
