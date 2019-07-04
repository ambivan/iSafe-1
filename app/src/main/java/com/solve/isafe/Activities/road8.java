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
import com.solve.isafe.Classes.Road8;
import com.solve.isafe.Fragments.RoadSafetyAudit;
import com.solve.isafe.R;

public class road8 extends AppCompatActivity {

    EditText e1, e2, e3, e4,e5;
    String et1, et2, et3, et4, et5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.road_safety_audit8);

        Button next8= findViewById(R.id.next8);

        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);
        e4= findViewById(R.id.e4);
        e5 =findViewById(R.id.e5);

        Window window = road8.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(road8.this,R.color.mystatus));


        next8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et1 = e1.getText().toString();
                et2 = e2.getText().toString();
                et3 = e3.getText().toString();
                et4 = e4.getText().toString();
                et5 = e5.getText().toString();

                if (TextUtils.isEmpty(et1) || TextUtils.isEmpty(et2) || TextUtils.isEmpty(et3) || TextUtils.isEmpty(et4) || TextUtils.isEmpty(et5)) {
                    Toast.makeText(road8.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("RoadSafetyAudit")
                            .child(RoadSafetyAudit.timestamp)
                            .child("Checklist8")
                            .setValue(new Road8(et1, et2, et3, et4, et5));

                    finish();
                    Intent home = new Intent(road8.this, road9.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(home);
                }
            }
        });
    }
}
