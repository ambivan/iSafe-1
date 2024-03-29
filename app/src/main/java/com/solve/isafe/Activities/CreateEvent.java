package com.solve.isafe.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.solve.isafe.Classes.MyListData;
import com.solve.isafe.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateEvent extends AppCompatActivity {

    EditText event,date, hrs, mins,topic, college, city;

    String sevent,sdate, stime,stopic, scollege, userid, eventid, scity, simage;

    FirebaseAuth auth;

    String ampm;

    Calendar myCalendar = Calendar.getInstance();

    Button createevent, cancel;

    Handler handler = new Handler();

    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.solve.isafe.R.layout.activity_create_event);

        Window window = CreateEvent.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(CreateEvent.this,R.color.mystatus));


        event = (EditText) findViewById(com.solve.isafe.R.id.eventtype);
        hrs = (EditText) findViewById(R.id.hrs);
        mins = (EditText) findViewById(R.id.mins);
        topic = (EditText) findViewById(com.solve.isafe.R.id.topic);
        college = (EditText) findViewById(com.solve.isafe.R.id.college);
        city = (EditText) findViewById(com.solve.isafe.R.id.cityname);

        final Spinner spinner = (Spinner) findViewById(R.id.spinnertime);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Times));

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position == 0){

                    ampm = "A.M.";

                }else if (position == 1){
                    ampm = "P.M.";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        runnable = new Runnable() {
            @Override
            public void run() {

                createevent.setTextColor(getResources().getColor(R.color.button));

            }
        };


        final EditText date = (EditText) findViewById(R.id.dateevent);
        final DatePickerDialog.OnDateSetListener datee = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        cancel = (Button) findViewById(R.id.cancel);
        sdate = date.getText().toString();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.frag = 1;

                Intent home = new Intent(CreateEvent.this, HomePageActivity.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
            }
        });


        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateEvent.this, datee, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        sevent = event.getText().toString();
        sdate = date.getText().toString();

        stime = hrs.getText().toString() + " : " + mins.getText().toString() + " " + ampm;
        stopic = topic.getText().toString();
        scollege = college.getText().toString();
        scity = city.getText().toString();

        createevent = (Button) findViewById(R.id.createeventbutton);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                createevent.setTextColor(getResources().getColor(R.color.button));

            }

            @Override
            public void afterTextChanged(Editable s) {

                createevent.setTextColor(getResources().getColor(R.color.button));

            }
        };

        topic.addTextChangedListener(textWatcher);

        handler.postDelayed(runnable, 7000);

        createevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sevent = event.getText().toString();
                sdate = date.getText().toString();

                stime = hrs.getText().toString() + " : " + mins.getText().toString() + " " + ampm;
                stopic = topic.getText().toString();
                scollege = college.getText().toString();
                scity = city.getText().toString();
                eventid = String.valueOf(System.currentTimeMillis());

                if (!TextUtils.isEmpty(sevent)||!TextUtils.isEmpty(sdate)||!TextUtils.isEmpty(stime)||!TextUtils.isEmpty(stopic)||!TextUtils.isEmpty(scollege)||!TextUtils.isEmpty(scity)) {

                    FirebaseDatabase.getInstance().getReference()
                            .child("Events")
                            .child(eventid)
                            .setValue(new MyListData(eventid, scollege, scity, sevent, sdate, stime, stopic, "0"));

                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Events")
                            .child(eventid)
                            .setValue(new MyListData(eventid, scollege, scity, sevent, sdate, stime, stopic, "0"));

                    finish();
                    Intent home = new Intent(CreateEvent.this, HomePageActivity.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(home);

                }else {
                    Toast.makeText(CreateEvent.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        topic.removeTextChangedListener(textWatcher);
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

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date = findViewById(R.id.dateevent);

        date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

