package com.example.isafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.isafe.Profile.desig;

public class Signup2 extends AppCompatActivity {

    private FirebaseAuth auth;

    FirebaseAuth.AuthStateListener authStateListener;

    ProgressDialog mProgress;

    EditText emailid, npassword, confirm, uid;

    String userid ;

    DatabaseReference reference;

    static ArrayList<String> volunteer = new ArrayList<String>();
    static ArrayList<String> teamlead = new ArrayList<String>();
    static ArrayList<String> teammember = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        desig = (TextView) findViewById(R.id.designation);

        FirebaseApp.initializeApp(Signup2.this);

        mProgress = new ProgressDialog(Signup2.this);

        emailid = (EditText)findViewById(R.id.emailid);
        npassword = (EditText) findViewById(R.id.newpassword);
        confirm = (EditText) findViewById(R.id.confirm);
        uid = (EditText) findViewById(R.id.collegeuid);

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                userid = user.getUid();




            }
        };


        auth.addAuthStateListener(authStateListener);



        Button signup = (Button) findViewById(R.id.signup2);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgress.setTitle("Creating Account");
                mProgress.setMessage("Please wait while account is being created...");
                mProgress.show();

                createUser();

            }
        });



    }

    private void createUser() {

        String em, pa;
        em = emailid.getText().toString().trim();
        pa = npassword.getText().toString().trim();

        auth.createUserWithEmailAndPassword(em,pa)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Log.i("Success", "Signup completed");

                            Intent in = new Intent(Signup2.this, HomePageActivity.class);

                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(in);

                            mProgress.dismiss();

                        }else {
                            Log.i("Fail", "Signup not completed");
                        }

                    }
                });
            }
        }
