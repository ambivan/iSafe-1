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

import com.solve.isafe.Classes.Road6;
import com.solve.isafe.Fragments.RoadSafetyAudit;
import com.solve.isafe.MapActivity;
import com.solve.isafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class road6 extends AppCompatActivity {

    EditText e1, e2, e3;
    String et1, et2, et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.road_safety_audit6);

        Button next6= findViewById(R.id.next6);

        Window window = road6.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(road6.this,R.color.mystatus));
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);

        next6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et1 = e1.getText().toString();
                et2 = e2.getText().toString();
                et3 = e3.getText().toString();

                if (TextUtils.isEmpty(et1) || TextUtils.isEmpty(et2) || TextUtils.isEmpty(et3)) {
                    Toast.makeText(road6.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                } else {

                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("RoadSafetyAudit")
                            .child(RoadSafetyAudit.timestamp)
                            .child("Checklist6")
                            .setValue(new Road6(et1, et2, et3));


                    finish();
                    Intent home = new Intent(road6.this, road7.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(home);
                }
            }
        });
    }
}
