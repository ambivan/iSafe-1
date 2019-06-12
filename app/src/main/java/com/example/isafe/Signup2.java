package com.example.isafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Signup2 extends AppCompatActivity {

    private FirebaseAuth auth;

    FirebaseAuth.AuthStateListener authStateListener;

    ProgressDialog mProgress;

    EditText emailid, npassword, confirm, collegeuid, name, teamleadname;

    ImageView pass1, pass2;

    static String userid;

    String em, pa, con, cuid;

    String post = "", profilename;

    Button signup;

    String id;

    int same;

    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);


        if (SignupActivity.i == 2){

            setContentView(R.layout.signup2_teamlead);

        }

        FirebaseApp.initializeApp(Signup2.this);

        count = 0;

        pass1 = (ImageView) findViewById(R.id.pass1);
        pass2 = (ImageView) findViewById(R.id.pass2);

        mProgress = new ProgressDialog(Signup2.this);

        emailid = (EditText) findViewById(R.id.emailid);
        npassword = (EditText) findViewById(R.id.newpassword);
        confirm = (EditText) findViewById(R.id.confirm);
        collegeuid = (EditText) findViewById(R.id.collegeuid);
        name = (EditText) findViewById(R.id.name);
        teamleadname = (EditText) findViewById(R.id.teamleadname);

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

            }
        };


        auth.addAuthStateListener(authStateListener);



        emailid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                emailid.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);


            }

            @Override
            public void afterTextChanged(Editable s) {

                em = emailid.getText().toString();

                if (isEmailValid(em))
                    emailid.getBackground().mutate().setColorFilter(getResources().getColor(R.color.correct), PorterDuff.Mode.SRC_ATOP);
            }


        });

        signup = (Button) findViewById(R.id.signup2);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                em = emailid.getText().toString();

                if (!isEmailValid(em)){

                    Toast.makeText(Signup2.this, "Invalid Email", Toast.LENGTH_SHORT).show();

                }else {



                    if (same == 1) {


                        mProgress.setCancelable(false);
                        mProgress.setCanceledOnTouchOutside(false);
                        mProgress.setTitle("Creating Account");
                        mProgress.setMessage("Please wait while account is being created...");
                        mProgress.show();

                        createUser();

                        count++;
                    }else if (same == 0){
                        Toast.makeText(Signup2.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        pass1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        npassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        npassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }

                return true;
            }
        });

        pass2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        confirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        confirm.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }

                return true;
            }
        });


        confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                con = confirm.getText().toString();
                pa = npassword.getText().toString();

                if (con.equals(pa)){
                    npassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.correct), PorterDuff.Mode.SRC_ATOP);
                    confirm.getBackground().mutate().setColorFilter(getResources().getColor(R.color.correct), PorterDuff.Mode.SRC_ATOP);

                    same = 1;

                }else {
                    npassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    confirm.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

                    same = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onBackPressed() {

        if (count >0){
            moveTaskToBack(true);
        }
    }






    private void createUser() {

        em = emailid.getText().toString().trim();
        pa = npassword.getText().toString().trim();
        con = confirm.getText().toString().trim();

        auth.createUserWithEmailAndPassword(em,pa)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            userid = auth.getCurrentUser().getUid();

                            if (SignupActivity.i == 1) {

                                post = "Volunteer";
                                profilename = name.getText().toString();

                            }
                            if (SignupActivity.i == 2) {

                                post = "Team Leader";
                                profilename = teamleadname.getText().toString();

                            }
                            if (SignupActivity.i == 3) {

                                post = "Team Member";
                                profilename = name.getText().toString();


                            }

                                FirebaseDatabase.getInstance()
                                        .getReference()
                                        .child(userid)
                                        .setValue(new UserPost(profilename, post));

                            if (SignupActivity.i == 3){

                            final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Code");

                            dbref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                                        System.out.println(child.getKey()); // "-key1", "-key2", etc
                                        System.out.println(child.getValue()); // true, true, etc

                                        String key = child.getKey();

                                        DatabaseReference d = dbref.child(key);

                                        d.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                CodeGen codeGen = dataSnapshot.getValue(CodeGen.class);
                                                System.out.println(codeGen.getCode());

                                                cuid = collegeuid.getText().toString();

                                                if (post.equals("Team Member")){
                                                    if (codeGen.getCode().equals(cuid)){
                                                        id = codeGen.getUserid();
                                                        System.out.println(id);
                                                        FirebaseDatabase.getInstance()
                                                                .getReference()
                                                                .child(id)
                                                                .child(userid)
                                                                .setValue(new UserPost(profilename, post));
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            }

                            Log.i("Success", "Signup completed");

                            if (SignupActivity.i==2){
                                Intent in = new Intent(Signup2.this, CodeGenerator.class);

                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(in);
                            }else {
                                Intent in = new Intent(Signup2.this, HomePageActivity.class);

                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(in);
                            }

                            mProgress.dismiss();

                        }else {
                            Log.i("Fail", "Signup not completed");
                        }

                    }
                });
            }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.addAuthStateListener(authStateListener);
    }
}
