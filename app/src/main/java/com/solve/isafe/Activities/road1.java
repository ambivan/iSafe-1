package com.solve.isafe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.solve.isafe.Classes.Road1;
import com.solve.isafe.Fragments.RoadSafetyAudit;
import com.solve.isafe.R;

public class road1 extends AppCompatActivity {

    EditText e1, e2, e3, e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14;
    String et1, et2, et3, et4, et5, et6, et7, et8, et9, et10, et11, et12, et13, et14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road1);

        Button next1 = findViewById(R.id.next1);

        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);
        e4= findViewById(R.id.e4);
        e5 = findViewById(R.id.e5);
        e6 = findViewById(R.id.e6);
        e7 = findViewById(R.id.e7);
        e8 = findViewById(R.id.e8);
        e9 = findViewById(R.id.e9);
        e10 = findViewById(R.id.e10);
        e11 = findViewById(R.id.e11);
        e12 = findViewById(R.id.e12);
        e13 = findViewById(R.id.e13);
        e14 = findViewById(R.id.e14);

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et1 = e1.getText().toString();
                et2 = e2.getText().toString();
                et3 = e3.getText().toString();
                et4 = e4.getText().toString();
                et5 = e5.getText().toString();
                et6 = e6.getText().toString();
                et7 = e7.getText().toString();
                et8 = e8.getText().toString();
                et9 = e9.getText().toString();
                et10 = e10.getText().toString();
                et11 = e11.getText().toString();
                et12 = e12.getText().toString();
                et13 = e13.getText().toString();
                et14 = e14.getText().toString();

                if (TextUtils.isEmpty(et1) || TextUtils.isEmpty(et2) || TextUtils.isEmpty(et3) || TextUtils.isEmpty(et4) || TextUtils.isEmpty(et5) || TextUtils.isEmpty(et6) || TextUtils.isEmpty(et7) || TextUtils.isEmpty(et8) || TextUtils.isEmpty(et9) || TextUtils.isEmpty(et10)||TextUtils.isEmpty(et11)||TextUtils.isEmpty(et12)||TextUtils.isEmpty(et13)||TextUtils.isEmpty(et14)) {
                    Toast.makeText(road1.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("RoadSafetyAudit")
                            .child(RoadSafetyAudit.timestamp)
                            .child("Checklist1")
                            .setValue(new Road1(et1, et2, et3, et4, et5, et6, et7, et8, et9, et10, et11, et12, et13, et14));

                    startActivity(new Intent(road1.this, road2.class));
                }
            }
        });

    }
}
