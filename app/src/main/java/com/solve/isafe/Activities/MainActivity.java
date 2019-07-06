package com.solve.isafe.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solve.isafe.R;
import com.solve.isafe.Services.NotificationService;

public class MainActivity extends AppCompatActivity {

    LinearLayout linlay2, linearLayout;

    Button login, signup;

    Intent intent;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    Animation frombottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linlay1);
        linlay2 = findViewById(R.id.linlay2);
        linlay2.setVisibility(View.GONE);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        linearLayout.animate().translationY(140).setDuration(1000).setStartDelay(1000);
        linlay2.setVisibility(View.VISIBLE);
        linlay2.animate().translationY(-140).setDuration(1000).setStartDelay(1000);

        linlay2.startAnimation(frombottom);
        linearLayout.startAnimation(frombottom);

        login = findViewById(com.solve.isafe.R.id.login);
        signup = findViewById(com.solve.isafe.R.id.signup);

        Window window = MainActivity.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.mystatus));
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {

                    Intent home = new Intent(MainActivity.this, HomePageActivity.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(home);

                }

            }
        };

        mAuth.addAuthStateListener(mAuthListener);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home = new Intent(MainActivity.this, LoginActivity.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home = new Intent(MainActivity.this, SignupActivity.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);


            }
        });


        Intent intent = new Intent(MainActivity.this, NotificationService.class);
        startService(intent);
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My notifications", "My notifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}