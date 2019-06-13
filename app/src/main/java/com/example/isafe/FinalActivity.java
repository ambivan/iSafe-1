package com.example.isafe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class FinalActivity extends AppCompatActivity {

  Runnable runnable = new Runnable() {
    @Override
    public void run() {

      Intent intent = new Intent(FinalActivity.this, HomePageActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);

    }
  };

  Handler handler = new Handler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    setContentView(R.layout.activity_final);

    HomePageActivity.frag = 3;

    handler.postDelayed(runnable, 3000);

  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();

    Intent intent = new Intent(FinalActivity.this, HomePageActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
  }
}