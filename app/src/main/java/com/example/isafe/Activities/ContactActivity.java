package com.example.isafe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.isafe.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactActivity extends AppCompatActivity {

    EditText name, phone;

    Button save;

    public static String contactName;
    public static String number;

    public static int c = 0;

    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        FirebaseApp.initializeApp(this);

        HomePageActivity.frag = 3;



        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.contactno);

        save = (Button)findViewById(R.id.save);


        auth = FirebaseAuth.getInstance();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser user = auth.getCurrentUser();

                if ((user != null)){

                    userid = user.getUid();

                }
            }

        };

        auth.addAuthStateListener(authStateListener);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactName = name.getText().toString();
                number = phone.getText().toString();

                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Accident Report").push().child("Contact Details");
                databaseReference.child("Name").setValue(contactName);
                databaseReference.child("Phone Number").setValue(number);

                c = 1;

                startActivity(new Intent(ContactActivity.this, HomePageActivity.class));

            }
        });

    }
}