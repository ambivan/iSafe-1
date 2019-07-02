package com.example.isafe.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.isafe.Activities.HomePageActivity;
import com.example.isafe.Classes.Constants;
import com.example.isafe.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class Reimbursement extends Fragment {

    View vr;
    Button bill;
    Button attach, send;

    ImageView billimage;

    final int PICK_PDF_CODE = 2342;

    TextView file, support;

    String path;

    private static final int SELECT_FILE = 2;

    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vr = inflater.inflate(R.layout.reimbursement, container, false);


        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        bill = (Button) vr.findViewById(R.id.files);
        send = (Button) vr.findViewById(R.id.attachedsend);
        file = (TextView) vr.findViewById(R.id.filename);
        support = (TextView) vr.findViewById(R.id.support);
        billimage = vr.findViewById(R.id.bill);


        if (Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }

        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence options[] = new CharSequence[] {"PDF", "png/jpeg"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setTitle("Select your option:");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if ("PDF".equals(options[which])){

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/pdf");

                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);


                        }else if ("png/jpeg".equals(options[which])){

                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto , SELECT_FILE);

                        }
                    }
                });

                builder.show();

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





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_CODE) {

            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                Uri uri = data.getData();


                bill.setVisibility(View.GONE);
                support.setVisibility(View.GONE);

                String u = getFileName(uri);
                file.setText(u);

                uploadFile(uri);



            }
        }


        else if (requestCode == SELECT_FILE){
            if(resultCode == RESULT_OK){
                Uri selectedImage = Uri.parse(String.valueOf(data.getData()));
                String url = selectedImage.getLastPathSegment();
                final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Reimbursements").child(url);

                storageReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        final String fileName = Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis();

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String url = uri.toString();
                                mDatabaseReference
                                        .child("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("Reimbursements")
                                        .child(fileName)
                                        .setValue(url);

                                bill.setVisibility(View.VISIBLE);
                                Glide.with(getContext()).load(url).into(billimage);
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

        final StorageReference sRef = mStorageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Reimbursements").child(fileName);
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String url = sRef.getDownloadUrl().toString();
                        mDatabaseReference
                                .child("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("Reimbursements")
                                .child(fileName)
                                .setValue(url);

                        progressDialog.dismiss();

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
