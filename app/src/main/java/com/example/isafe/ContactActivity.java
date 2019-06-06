package com.example.isafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactActivity extends AppCompatActivity {

    EditText name, phone;

    Button save;

    static String contactName="", number = "";

    static DatabaseReference rootRef, dataRef;

    static int c = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        FirebaseApp.initializeApp(this);

        HomePageActivity.frag = 3;



        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.contactno);

        save = (Button)findViewById(R.id.save);


        rootRef = FirebaseDatabase.getInstance().getReference();

        dataRef = rootRef.child("Contact_Details");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactName = name.getText().toString();
                number = phone.getText().toString();

                dataRef.child("Name").setValue(contactName);
                dataRef.child("Number").setValue(number);
                c = 1;

                startActivity(new Intent(ContactActivity.this, HomePageActivity.class));
            }
        });

    }
}
