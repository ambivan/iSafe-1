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
import com.solve.isafe.Classes.Road7;
import com.solve.isafe.Fragments.RoadSafetyAudit;
import com.solve.isafe.R;

public class road7 extends AppCompatActivity {

    EditText e1, e2, e3, e4,e5,e6,e7,e8,e9,e10;
    String et1, et2, et3, et4, et5, et6, et7, et8, et9, et10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.road_safety_audit7);

        Window window = road7.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(road7.this,R.color.mystatus));

        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);
        e4= findViewById(R.id.e4);
        e5 =findViewById(R.id.e5);
        e6 = findViewById(R.id.e6);
        e7 = findViewById(R.id.e7);
        e8 = findViewById(R.id.e8);
        e9 = findViewById(R.id.e9);
        e10 = findViewById(R.id.e10);

        Button next7 = findViewById(R.id.next7);

        next7.setOnClickListener(new View.OnClickListener() {
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

                if (TextUtils.isEmpty(et1) || TextUtils.isEmpty(et2) || TextUtils.isEmpty(et3) || TextUtils.isEmpty(et4) || TextUtils.isEmpty(et5) || TextUtils.isEmpty(et6) || TextUtils.isEmpty(et7) || TextUtils.isEmpty(et8) || TextUtils.isEmpty(et9) || TextUtils.isEmpty(et10)) {
                    Toast.makeText(road7.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                } else {

                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("RoadSafetyAudit")
                            .child(RoadSafetyAudit.timestamp)
                            .child("Checklist7").setValue(new Road7(et1, et2, et3, et4, et5, et6, et7, et8, et9, et10));

                    Intent home = new Intent(road7.this, road8.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(home);
                }
            }
        });
    }
}
