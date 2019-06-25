package com.example.isafe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {


    EditText email;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar6);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        email = (EditText) findViewById(R.id.emailforgot);
        reset = (Button) findViewById(R.id.reset);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String semail = email.getText().toString().trim();

                if (!TextUtils.isEmpty(semail)){

                    FirebaseAuth.getInstance().sendPasswordResetEmail(semail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("tag", "Email sent.");

                                        Toast.makeText(ForgotPasswordActivity.this, "Reset password link sent to your email", Toast.LENGTH_SHORT).show();

                                        finish();
                                        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));

                                    } else{
                                        Toast.makeText(ForgotPasswordActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }else{

                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}
