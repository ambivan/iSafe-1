package com.solve.isafe.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.solve.isafe.R;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    ProgressDialog pro;

    private FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;

    TextView forgot;

    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pro = new ProgressDialog(LoginActivity.this);

        email = (EditText) findViewById(com.solve.isafe.R.id.email);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.loginbutton);
        forgot = (TextView) findViewById(R.id.forgot);

        Window window = LoginActivity.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.mystatus));

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarl);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        auth = FirebaseAuth.getInstance();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser user = auth.getCurrentUser();

                if ((user != null)) {

                    userid = user.getUid();

                    Intent home = new Intent(LoginActivity.this, HomePageActivity.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(home);
                }


            }

        };

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    pro.setCanceledOnTouchOutside(false);
                    pro.setCancelable(false);
                    pro.setTitle("Logging in");
                    pro.setMessage("Please wait..");
                    pro.show();


                    loginuser();
                }
                return false;
            }
        });

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

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
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
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                RemoteMessage rm = new RemoteMessage.Builder("test")
                                        .addData("message", "Hello")

                                        .build();
                                FirebaseMessaging.getInstance().send(rm);


                            } else {
                                pro.dismiss();
                                Toast.makeText(LoginActivity.this, "WRONG EMAIL OR PASSWORD!!", Toast.LENGTH_SHORT).show();
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
        super.onBackPressed();
    }
}