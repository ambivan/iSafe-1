package com.example.isafe;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreatEvent extends Activity {

    EditText event,date, time,topic, college;
    String sevent,sdate, stime,stopic, scollege, userid;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference;

    Button createevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_event);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.6));
        getWindow().setBackgroundDrawableResource(R.drawable.share_code);

        event = (EditText) findViewById(R.id.eventtype);
        date = (EditText) findViewById(R.id.dateevent);
        time = (EditText) findViewById(R.id.starttime);
        topic = (EditText) findViewById(R.id.topic);
        college = (EditText) findViewById(R.id.college);


        createevent = (Button) findViewById(R.id.createeventbutton);

        createevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sevent = event.getText().toString();
                sdate = date.getText().toString();
                stime = time.getText().toString();
                stopic = topic.getText().toString();
                scollege = college.getText().toString();

                FirebaseDatabase.getInstance().getReference()
                        .child("Events")
                        .push()
                        .setValue(new MyListData(scollege, sevent, sdate, stime, stopic));


            }
        });



    }
}
