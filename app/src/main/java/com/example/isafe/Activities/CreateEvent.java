package com.example.isafe.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isafe.Classes.MyListData;
import com.example.isafe.HomePageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateEvent extends Activity {

    EditText event,date, time,topic, college, city;
    String sevent,sdate, stime,stopic, scollege, userid, scity;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference;

    Button createevent;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.example.isafe.R.layout.activity_create_event);

        event = (EditText) findViewById(com.example.isafe.R.id.eventtype);
        date = (EditText) findViewById(com.example.isafe.R.id.dateevent);
        time = (EditText) findViewById(com.example.isafe.R.id.starttime);
        topic = (EditText) findViewById(com.example.isafe.R.id.topic);
        college = (EditText) findViewById(com.example.isafe.R.id.college);
        city = (EditText) findViewById(com.example.isafe.R.id.cityname);

        sdate = date.getText().toString();


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

                int type = date.getInputType();

                if (type == (InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE))
                {
                    Toast.makeText(CreateEvent.this, "Corre", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createevent = (Button) findViewById(com.example.isafe.R.id.createeventbutton);

        createevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sevent = event.getText().toString();
                sdate = date.getText().toString();
                stime = time.getText().toString();
                stopic = topic.getText().toString();
                scollege = college.getText().toString();
                scity = city.getText().toString();

                if (!TextUtils.isEmpty(sevent)||!TextUtils.isEmpty(sdate)||!TextUtils.isEmpty(stime)||!TextUtils.isEmpty(stopic)||!TextUtils.isEmpty(scollege)||!TextUtils.isEmpty(scity)) {

                    FirebaseDatabase.getInstance().getReference()
                            .child("Events")
                            .push()
                            .setValue(new MyListData(scollege, scity, sevent, sdate, stime, stopic));

                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Events")
                            .push()
                            .setValue(new MyListData(scollege, scity, sevent, sdate, stime, stopic));

                    startActivity(new Intent(CreateEvent.this, HomePageActivity.class));

                }else {
                    Toast.makeText(CreateEvent.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public Date convertToDate(String input) {
        Date date = null;
        if(null == input) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
            try {
                format.setLenient(false);
                date = format.parse(input);
            } catch (ParseException e) {

                Toast.makeText(CreateEvent.this, "Enter a valid date", Toast.LENGTH_SHORT).show();

            }

        return date;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

