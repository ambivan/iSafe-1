package com.example.isafe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isafe.Classes.Meeting;
import com.example.isafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class MeetingReport extends AppCompatActivity {


    EditText agen, minutes, task;

    String sagen, sminutes, stask;

    Button ss;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_meeting_report);

    agen = (EditText) findViewById(R.id.agendaa);
    minutes = (EditText) findViewById(R.id.mins);
    task = (EditText) findViewById(R.id.task);
    ss = (Button) findViewById(R.id.ss);

    ss.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            sagen = agen.getText().toString();
            sminutes = minutes.getText().toString();
            stask = task.getText().toString();

            if (!TextUtils.isEmpty(sagen) && !TextUtils.isEmpty(sminutes) && !TextUtils.isEmpty(stask)){

                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Meeting Reports")
                        .push()
                        .setValue(new Meeting(sagen, sminutes, stask));


                Toast.makeText(MeetingReport.this, "Your meeting report has been recorded.", Toast.LENGTH_SHORT).show();
                HomePageActivity.frag = 1;
                startActivity(new Intent(MeetingReport.this, HomePageActivity.class));

            }else {
                Toast.makeText(MeetingReport.this, "Please fill out all fields! ", Toast.LENGTH_SHORT).show();
            }

        }
    });

  }
}