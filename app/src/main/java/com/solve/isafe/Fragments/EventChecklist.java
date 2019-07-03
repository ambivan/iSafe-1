package com.solve.isafe.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.solve.isafe.Activities.HomePageActivity;
import com.solve.isafe.Classes.Constants;
import com.solve.isafe.Classes.EventChecklist2;
import com.solve.isafe.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class EventChecklist extends Fragment {

    View ve;

    Button photos, docs, save;

    final int PICK_PDF_CODE = 2342;
    private final int SELECT_FILE = 2;

    Calendar myCalendar = Calendar.getInstance();

    String sname, sdate, sbrief, simpact;

    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;



    EditText name, date, notes, brief, impact;

    ListView listdoc, listphoto;

    ArrayList<String> doclist, photolist;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ve = inflater.inflate(R.layout.event_checklist, container, false);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        final DatePickerDialog.OnDateSetListener datee = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        name = ve.findViewById(R.id.ename);
        date = ve.findViewById(R.id.edate);
        brief = ve.findViewById(R.id.brief);
        impact = ve.findViewById(R.id.impact);



        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), datee, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        doclist = new ArrayList<>();
        photolist = new ArrayList<>();

        listdoc = ve.findViewById(R.id.listdoc);
        listphoto = ve.findViewById(R.id.listpho);

        save = ve.findViewById(R.id.eventsave);

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sname = name.getText().toString();
                sdate = date.getText().toString();
                sbrief = brief.getText().toString();
                simpact = impact.getText().toString();


                if (!TextUtils.isEmpty(sname) && !TextUtils.isEmpty(sdate) && !TextUtils.isEmpty(sbrief) && !TextUtils.isEmpty(simpact)) {

                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("EventDay")
                            .push()
                            .setValue(new EventChecklist2(sname, sdate, sbrief, simpact));

                    Toast.makeText(getActivity(), "Your event details have been saved!", Toast.LENGTH_SHORT).show();

                    HomePageActivity.frag = 1;

                    startActivity(new Intent(getActivity(), HomePageActivity.class));
                } else {
                    Toast.makeText(getActivity(), "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                }

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

                doclist.add(getFileName(uri));

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, doclist);

                listdoc.setAdapter(arrayAdapter);

                uploadFile(uri);

            } else if (requestCode == SELECT_FILE) {

                Uri selectedImage = Uri.parse(String.valueOf(data.getData()));

                photolist.add(getFileName(selectedImage));

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, photolist);

                listphoto.setAdapter(arrayAdapter);

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

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date = ve.findViewById(R.id.edate);

        date.setText(sdf.format(myCalendar.getTime()));
    }

}
