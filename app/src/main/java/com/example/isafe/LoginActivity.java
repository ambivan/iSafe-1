package com.example.isafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    ProgressDialog pro;

    private FirebaseAuth auth;

    FirebaseAuth.AuthStateListener authStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pro = new ProgressDialog(LoginActivity.this);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.loginbutton);

        auth = FirebaseAuth.getInstance();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = auth.getCurrentUser();


            }

        };

        auth.addAuthStateListener(authStateListener);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
//
//                pro.setTitle("Logging in");
//                pro.setMessage("Please wait..");
//                pro.show();
//
//
//                loginuser();


            }

        });

    }

    private void loginuser() {

        String em, pa;

        em = email.getText().toString().trim();
        pa = password.getText().toString().trim();

        if (!TextUtils.isEmpty(em) && !TextUtils.isEmpty(pa)) {

            auth.signInWithEmailAndPassword(em, pa)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                pro.dismiss();


                                Intent home = new Intent(LoginActivity.this, HomePageActivity.class);
                                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(home);

                            } else {
                                Log.i("info", "unsuccessful");
                            }

                        }

                    });

        }

    }
}
