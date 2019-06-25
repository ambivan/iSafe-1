package com.example.isafe.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.isafe.Classes.Constants;
import com.example.isafe.R;
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

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class EventChecklist extends Fragment {

    View ve;

    ImageView eimage, eimage2, eimage3, doc, doc3, doc2;

    final int CAMERA_REQUEST = 1;

    Button photos, docs;

    final int PICK_PDF_CODE = 2342;
    private final int SELECT_FILE = 2;

    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;


    Uri photoURI;
    String path;

    ListView listdoc, listphoto;

    ArrayList<String> doclist, photolist;

    int count;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        doclist = new ArrayList<>();
        photolist = new ArrayList<>();


        ve = inflater.inflate(R.layout.event_checklist, container, false);

        listdoc = ve.findViewById(R.id.listdoc);
        listphoto = ve.findViewById(R.id.listpho);

        final DatabaseReference a = mDatabaseReference
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("EventDay")
                .child("Documents");


        a.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    if (d.getValue() != null) {
                        doclist.add(String.valueOf(d.getValue()));

                        System.out.println(doclist);

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, doclist);

                        listdoc.setAdapter(arrayAdapter);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference p = mDatabaseReference
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("EventDay")
                .child("Photos");

        p.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot p : dataSnapshot.getChildren()) {

                    if (p.getValue() != null) {
                        photolist.add(String.valueOf(p.getValue()));

                        System.out.println(photolist);

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, photolist);

                        listphoto.setAdapter(arrayAdapter);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        photos = ve.findViewById(R.id.photos);
        docs = ve.findViewById(R.id.docs);

        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, SELECT_FILE);

            }
        });

        docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);

            }
        });

        return ve;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_PDF_CODE) {
                Uri uri = data.getData();
                System.out.println(uri);

                String u = getFileName(uri);

                uploadFile(uri);

            } else if (requestCode == SELECT_FILE) {

                Uri selectedImage = Uri.parse(String.valueOf(data.getData()));
                String url = selectedImage.getLastPathSegment();
                final StorageReference storageReference = FirebaseStorage.getInstance()
                        .getReference()
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("EventDay")
                        .child("Photos")
                        .child(url);

                storageReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String url = uri.toString();
                                mDatabaseReference
                                        .child("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("EventDay")
                                        .child("Photos")
                                        .push()
                                        .setValue(url);

                            }
                        });
                    }

                });
            }
        }
    }


    private void uploadFile(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File .....");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis();

        final StorageReference sRef = mStorageReference
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("EventDay")
                .child("Documents")
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

                                mDatabaseReference
                                        .child("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("EventDay")
                                        .child("Documents")
                                        .push()
                                        .setValue(url);

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
