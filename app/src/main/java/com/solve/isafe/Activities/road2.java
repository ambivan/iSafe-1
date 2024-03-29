package com.solve.isafe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.solve.isafe.Classes.Road2;
import com.solve.isafe.Fragments.RoadSafetyAudit;
import com.solve.isafe.R;

public class road2 extends AppCompatActivity {

    EditText e1, e2, e3, e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14, e15, e16,e17,e18,e19,e20,e21,e22,e23,e24,e25;
    String et1, et2, et3, et4, et5, et6, et7, et8, et9, et10, et11, et12, et13, et14,et15, et16,et17,et18,et19,et20,et21,et22,et23,et24,et25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.road_safety_audit2);

        Button next2 = findViewById(R.id.next2);

        Window window = road2.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(road2.this,R.color.mystatus));

        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);
        e4= findViewById(R.id.e4);
        e5 =findViewById(R.id.e5);
        e6 = findViewById(R.id.e20);
        e7 = findViewById(R.id.e6);
        e8 = findViewById(R.id.e7);
        e9 = findViewById(R.id.e8);
        e10 = findViewById(R.id.e9);
        e11 = findViewById(R.id.e10);
        e12 = findViewById(R.id.e11);
        e13 = findViewById(R.id.e12);
        e14 = findViewById(R.id.e13);
        e15 = findViewById(R.id.e14);
        e16 = findViewById(R.id.e15);
        e17 = findViewById(R.id.e16);
        e18 = findViewById(R.id.e17);
        e19 = findViewById(R.id.e18);
        e20 = findViewById(R.id.e19);
        e21 = findViewById(R.id.e21);
        e22 = findViewById(R.id.e22);
        e23 = findViewById(R.id.e23);
        e24 = findViewById(R.id.e24);
        e25 = findViewById(R.id.e25);


        next2.setOnClickListener(new View.OnClickListener() {
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
                et15 = e15.getText().toString();
                et16 = e16.getText().toString();
                et17 = e17.getText().toString();
                et18 = e18.getText().toString();
                et19 = e19.getText().toString();
                et20 = e20.getText().toString();
                et21 = e21.getText().toString();
                et22 = e22.getText().toString();
                et23 = e23.getText().toString();
                et24 = e24.getText().toString();
                et25 = e25.getText().toString();

                if (TextUtils.isEmpty(et1) || TextUtils.isEmpty(et2) || TextUtils.isEmpty(et3) || TextUtils.isEmpty(et4) || TextUtils.isEmpty(et5) || TextUtils.isEmpty(et6) || TextUtils.isEmpty(et7) || TextUtils.isEmpty(et8) || TextUtils.isEmpty(et9) || TextUtils.isEmpty(et10)||TextUtils.isEmpty(et11)||TextUtils.isEmpty(et12)||TextUtils.isEmpty(et13)||TextUtils.isEmpty(et14)||TextUtils.isEmpty(et15)||TextUtils.isEmpty(et16)||TextUtils.isEmpty(et17)||TextUtils.isEmpty(et18)||TextUtils.isEmpty(et19)||TextUtils.isEmpty(et20)||TextUtils.isEmpty(et21)||TextUtils.isEmpty(et22)||TextUtils.isEmpty(et23)||TextUtils.isEmpty(et24)||TextUtils.isEmpty(et25)) {
                    Toast.makeText(road2.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                } else {

                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("RoadSafetyAudit")
                            .child(RoadSafetyAudit.timestamp)
                            .child("Checklist2")
                            .setValue(new Road2(et1, et2, et3, et4, et5, et6, et7, et8, et9, et10, et11, et12, et13, et14, et15, et16, et17, et18, et19, et20, et21, et22, et23, et24, et25));

                    Intent home = new Intent(road2.this, road3.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(home);
                }
            }
        });


    }
}
