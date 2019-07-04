package com.solve.isafe.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.solve.isafe.R;

public class FinalActivity extends AppCompatActivity {

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            finish();
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

        Window window = FinalActivity.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(FinalActivity.this, R.color.mystatus));

        handler.postDelayed(runnable, 3000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        Intent intent = new Intent(FinalActivity.this, HomePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}