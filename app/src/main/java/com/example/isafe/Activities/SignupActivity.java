package com.example.isafe.Activities;import android.content.Intent;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.view.View;import android.widget.AdapterView;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.Spinner;import com.example.isafe.Signup2;public class SignupActivity extends AppCompatActivity {    Button proceed;    public static int i;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(com.example.isafe.R.layout.activity_signup);        proceed = (Button) findViewById(com.example.isafe.R.id.proceed);        final Spinner spinner = (Spinner) findViewById(com.example.isafe.R.id.spinner);        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(                this,android.R.layout.simple_list_item_1,getResources().getStringArray(com.example.isafe.R.array.options1));        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        spinner.setAdapter(spinnerArrayAdapter);        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {            @Override            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {                String selectedItemText = (String) parent.getItemAtPosition(position);                if (position == 0){                    i=1;                }else if (position == 1){                    i=2;                }else if (position == 2){                    i=3;                }            }            @Override            public void onNothingSelected(AdapterView<?> parent) {            }        });        proceed.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Intent new1 = new Intent(SignupActivity.this, Signup2.class);                startActivity(new1);            }        });    }}