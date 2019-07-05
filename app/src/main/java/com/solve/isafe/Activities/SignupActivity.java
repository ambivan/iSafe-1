package com.solve.isafe.Activities;import android.content.Intent;import android.os.Bundle;import android.support.v4.content.ContextCompat;import android.support.v7.app.AppCompatActivity;import android.view.View;import android.view.Window;import android.view.WindowManager;import android.widget.AdapterView;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.Spinner;import com.solve.isafe.R;public class SignupActivity extends AppCompatActivity {    Button proceed;    public static int i;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(com.solve.isafe.R.layout.activity_signup);        Window window = SignupActivity.this.getWindow();        // clear FLAG_TRANSLUCENT_STATUS flag:        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);        // finally change the color        window.setStatusBarColor(ContextCompat.getColor(SignupActivity.this, R.color.mystatus));        proceed = (Button) findViewById(com.solve.isafe.R.id.proceed);        final Spinner spinner = (Spinner) findViewById(com.solve.isafe.R.id.spinner);        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(                this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.options1));        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        spinner.setAdapter(spinnerArrayAdapter);        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {            @Override            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {                String selectedItemText = (String) parent.getItemAtPosition(position);                if (position == 0) {                    i = 1;                } else if (position == 1) {                    i = 2;                } else if (position == 2) {                    i = 3;                }            }            @Override            public void onNothingSelected(AdapterView<?> parent) {            }        });        proceed.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Intent home = new Intent(SignupActivity.this, Signup2.class);                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);                startActivity(home);            }        });    }}