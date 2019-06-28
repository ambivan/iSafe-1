package com.example.isafe.Activities;

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

import com.example.isafe.CamActivity;
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

      Window window = MeetingReport.this.getWindow();

      // clear FLAG_TRANSLUCENT_STATUS flag:
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

      // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

      // finally change the color
      window.setStatusBarColor(ContextCompat.getColor(MeetingReport.this,R.color.mystatus));

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