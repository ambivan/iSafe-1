package com.example.isafe.Activities;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.isafe.NotificationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    LinearLayout linlay2;

    Button login, signup;

    Handler handler = new Handler();

    private NotificationService notificationService;

    Intent intent;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            linlay2.setVisibility(View.VISIBLE);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.isafe.R.layout.activity_main);

        linlay2 = (LinearLayout) findViewById(com.example.isafe.R.id.linlay2);

        handler.postDelayed(runnable, 1000);

        login = (Button) findViewById(com.example.isafe.R.id.login);
        signup = (Button) findViewById(com.example.isafe.R.id.signup);


        login.setOnClickListener(   new View.OnClickListener() {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My notifications", "My notifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = mAuth.getCurrentUser();

                if(user != null){

                    System.out.println("user" + user.getUid());

                    DatabaseReference d = FirebaseDatabase.getInstance().getReference().child(user.getUid())
                            .child("Agendas");

                    d.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            for(DataSnapshot child: children){

                                System.out.println(child);

                                notificationService = new NotificationService();

                                intent = new Intent(MainActivity.this, NotificationService.class);
                                startService(intent);

                                if (!isMyServiceRunning(notificationService.getClass())){
                                    startService(intent);
                                }


                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        };

        mAuth.addAuthStateListener(mAuthListener);

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    protected void onDestroy() {
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }

}