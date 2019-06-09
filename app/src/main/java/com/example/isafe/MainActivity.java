package com.example.isafe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

  LinearLayout linlay2;

  Button login, signup;


  Handler handler = new Handler();

  Runnable runnable = new Runnable() {
    @Override
    public void run() {

      linlay2.setVisibility(View.VISIBLE);

    }
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    linlay2 = (LinearLayout) findViewById(R.id.linlay2);

    handler.postDelayed(runnable, 1000);

    login = (Button) findViewById(R.id.login);
    signup = (Button) findViewById(R.id.signup);


    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
      }
    });

    signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        startActivity(new Intent(MainActivity.this, SignupActivity.class));

      }
    });


  }
}