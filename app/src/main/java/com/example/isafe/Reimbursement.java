package com.example.isafe;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    private static final int SELECT_FILE = 2;


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
                            intent.setType("image*/application/pdf");

                            startActivityForResult(intent, 1212);


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


//        if (resultCode == RESULT_OK) {
//            if (requestCode == CAMERA_REQUEST) {
//
//                    photobill = BitmapFactory.decodeFile(path);
//                    bill.setImageBitmap(photobill);
//
//            }
//
//        }
        if (requestCode == 1212){

            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                Uri uri = data.getData();
                String uriString = uri.toString();
                File myFile = new File(uriString);
                String path = myFile.getAbsolutePath();
                String displayName = null;

                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.getName();
                }
            }

        }


        else if (requestCode == SELECT_FILE){
            if(resultCode == RESULT_OK){
                Uri selectedImage = Uri.parse(String.valueOf(data.getData()));
                String url = selectedImage.getLastPathSegment();
                final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Bleh").child(url);

                storageReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> img = storageReference.getDownloadUrl();




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
                                        .child("URL")
                                        .setValue(url);
                                System.out.println(url);
                                Glide.with(getContext()).load(url).into(bill);
                                mProgress.dismiss();

                            }
                        });

                    }

                });

            }

        }

    }

}
