package com.example.isafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();

        setContentView(R.layout.activity_final);
    }
}
