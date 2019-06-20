package com.example.isafe.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isafe.HomePageActivity;
import com.example.isafe.NotificationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class LoginActivity extends AppCompatActivity {

  EditText email, password;
  Button login;
  ProgressDialog pro;

  private FirebaseAuth auth;
  FirebaseAuth.AuthStateListener authStateListener;

  DatabaseReference databaseReference;

  String userid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(com.example.isafe.R.layout.activity_login);

    pro = new ProgressDialog(LoginActivity.this);

    email = (EditText) findViewById(com.example.isafe.R.id.email);
    password = (EditText) findViewById(com.example.isafe.R.id.password);

    login = (Button) findViewById(com.example.isafe.R.id.loginbutton);

    auth = FirebaseAuth.getInstance();


    authStateListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        final FirebaseUser user = auth.getCurrentUser();

        if ((user != null)) {

          userid = user.getUid();

          databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Agendas");

          databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child: children) {

                    System.out.println(child);

                    NotificationService notificationService = new NotificationService();

                    Intent intent = new Intent(LoginActivity.this, NotificationService.class);
                    startService(intent);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
          });


          Intent home = new Intent(LoginActivity.this, HomePageActivity.class);
          home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(home);
        }


      }

    };

    auth.addAuthStateListener(authStateListener);

    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        pro.setCanceledOnTouchOutside(false);
        pro.setCancelable(false);
        pro.setTitle("Logging in");
        pro.setMessage("Please wait..");
        pro.show();


        loginuser();


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

                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finishAndRemoveTask();

                    RemoteMessage rm = new RemoteMessage.Builder("test")
                            .addData("message", "Hello")

                            .build();
                    FirebaseMessaging.getInstance().send(rm);



                  } else {
                    pro.dismiss();
                    Toast.makeText(LoginActivity.this, "WRONG EMAIL OR PASSWORD!!", Toast.LENGTH_SHORT).show();
                    Log.i("info", "unsuccessful");
                  }

                }

              });


    } else {
      pro.dismiss();

      Toast.makeText(LoginActivity.this, "Please enter email and password ", Toast.LENGTH_SHORT).show();

    }


  }



  @Override
  public void onBackPressed() {
    moveTaskToBack(true);
  }
}