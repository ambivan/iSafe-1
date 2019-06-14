package com.example.isafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CreateEvent extends Activity {

    EditText event,date, time,topic, college, city;
    String sevent,sdate, stime,stopic, scollege, userid, scity;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference;

    Button createevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_event);

        event = (EditText) findViewById(R.id.eventtype);
        date = (EditText) findViewById(R.id.dateevent);
        time = (EditText) findViewById(R.id.starttime);
        topic = (EditText) findViewById(R.id.topic);
        college = (EditText) findViewById(R.id.college);
        city = (EditText) findViewById(R.id.cityname);


        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String working = s.toString();
                if (working.length()==2 && before ==0) {
                    {
                        working+="/";
                        date.setText(working);
                        date.setSelection(working.length());
                    }
                } else if (working.length()==5 && before ==0) {
                    {
                        working+="/";
                        date.setText(working);
                        date.setSelection(working.length());
                    }
                }
//                else if (working.length()== && before ==0) {
//                    String enteredYear = working.substring(3);
//                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        createevent = (Button) findViewById(R.id.createeventbutton);

        createevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sevent = event.getText().toString();
                sdate = date.getText().toString();
                stime = time.getText().toString();
                stopic = topic.getText().toString();
                scollege = college.getText().toString();
                scity = city.getText().toString();

                FirebaseDatabase.getInstance().getReference()
                        .child("Events")
                        .push()
                        .setValue(new MyListData(scollege, scity, sevent, sdate, stime, stopic));

                startActivity(new Intent(CreateEvent.this, HomePageActivity.class));


            }
        });
    }
}
