package com.solve.isafe.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.solve.isafe.Activities.HomePageActivity;
import com.solve.isafe.Classes.AddProject;
import com.solve.isafe.Classes.Constants;
import com.solve.isafe.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class Projects extends Fragment {

    View vp;

    private final int PICK_PDF_CODE = 2342;
    private final int SELECT_FILE = 2;

    String pname, pdesc;

    String filename, id;

    EditText projname, desc;

    StorageReference mStorageReference;

    static String t;
    ArrayList<String> project, projsub;
    DatabaseReference mDatabaseReference;
    ListView projectlist, subproj;

    Button submit, add;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vp = inflater.inflate(com.solve.isafe.R.layout.projects, container, false);

        add = (Button) vp.findViewById(R.id.addp);

        projsub = new ArrayList<>();

        project = new ArrayList<>();
        projectlist = (ListView) vp.findViewById(R.id.list5);
        subproj = (ListView) vp.findViewById(R.id.listproj);

        t = String.valueOf(System.currentTimeMillis());

        submit = vp.findViewById(R.id.submitt);

        desc = vp.findViewById(R.id.pro_desc);
        projname = vp.findViewById(R.id.pname);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        final DatabaseReference database = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Projects")
                .child("Project Details");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot d : dataSnapshot.getChildren()) {

                    AddProject addProject = d.getValue(AddProject.class);


                    projsub.add(addProject.getProjname());
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, projsub);
                    subproj.setAdapter(arrayAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final DatabaseReference a = mDatabaseReference
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Projects")
                .child("Files");


        a.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (final DataSnapshot d : dataSnapshot.getChildren()) {

                    if (d.getValue() != null) {

                        project.add(String.valueOf(d.getValue()));

                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, project);

                        projectlist.setAdapter(arrayAdapter);

                        projectlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                                new AlertDialog.Builder(getContext())
                                        .setIcon(android.R.drawable.ic_delete)
                                        .setTitle("Are You Sure?")
                                        .setMessage("Do you want to delete this file?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {


                                                if (project.get(position).equals(d.getValue())) {
                                                    String key = d.getKey();
                                                    a.child(key).removeValue();
                                                }

                                                project.remove(position);
                                                arrayAdapter.notifyDataSetChanged();


                                            }
                                        })

                                        .setNegativeButton("No", null)
                                        .show();

                                return false;
                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence options[] = new CharSequence[]{"PDF", "png/jpeg"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Select your option:");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if ("PDF".equals(options[which])) {

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/pdf");

                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);


                        } else if ("png/jpeg".equals(options[which])) {

                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, SELECT_FILE);

                        }
                    }
                });

                builder.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pname = projname.getText().toString();
                pdesc = desc.getText().toString();

                if (!TextUtils.isEmpty(pname) && !TextUtils.isEmpty(pdesc)) {

                    HashMap<String, Object> hashMap = new HashMap<>();

                    hashMap.put("description", pdesc);
                    hashMap.put("projname", pname);

                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Projects")
                            .child("Project Details")
                            .push()
                            .setValue(new AddProject(pname, pdesc));

                    mDatabaseReference
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Projects")
                            .child("Files").removeValue();

                    Toast.makeText(getActivity(), "Your project has been shared with us!", Toast.LENGTH_SHORT).show();

                    HomePageActivity.frag = 1;

                    startActivity(new Intent(getActivity(), HomePageActivity.class));
                } else {
                    Toast.makeText(getActivity(), "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return vp;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_CODE) {

            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                Uri uri = data.getData();
                uploadFile(uri);



                mDatabaseReference
                        .child("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Projects")
                        .child("Files")
                        .push()
                        .setValue(getFileName(data.getData()));


            }
        } else if (requestCode == SELECT_FILE) {
            if (resultCode == RESULT_OK) {
                final Uri selectedImage = Uri.parse(String.valueOf(data.getData()));
                String url = selectedImage.getLastPathSegment();
                final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Projects").child(url);

                storageReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                mDatabaseReference
                                        .child("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("Projects")
                                        .child("Files")
                                        .push()
                                        .setValue(getFileName(data.getData()));

                            }
                        });

                    }

                });

            }

        }

    }

    private void uploadFile(final Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File .....");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis();

        final StorageReference sRef = mStorageReference
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Projects")
                .child(fileName);

        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String url = uri.toString();

                                progressDialog.dismiss();


                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int progress = (int) ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(progress);
            }
        });


    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}
