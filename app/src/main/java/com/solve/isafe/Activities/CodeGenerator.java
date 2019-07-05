package com.solve.isafe.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solve.isafe.Classes.CodeGen;
import com.solve.isafe.Classes.UserPost;
import com.solve.isafe.R;

import java.util.ArrayList;
import java.util.Iterator;

public class CodeGenerator extends AppCompatActivity {

    LinearLayout first, second;

    EditText collegename, code;

    String userid;

    static String codeg;

    String code_created;
    Button share, generate, continuee;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_generator);

        first = (LinearLayout) findViewById(R.id.first_layer);
        second = (LinearLayout) findViewById(R.id.second_layer);
        collegename = (EditText) findViewById(R.id.collegename);
        code = (EditText) findViewById(R.id.code);
        share = (Button) findViewById(R.id.share);
        generate = (Button) findViewById(R.id.generate);
        continuee = (Button) findViewById(R.id.conntinue);

        Window window = CodeGenerator.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(CodeGenerator.this, R.color.mystatus));

        code.setEnabled(false);

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                userid = user.getUid();

            }
        };


        auth.addAuthStateListener(authStateListener);


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String cn = collegename.getText().toString();

                if (!TextUtils.isEmpty(cn)) {

                    FirebaseDatabase.getInstance().getReference()
                            .child("Colleges")
                            .push()
                            .setValue(cn);

                    first.setVisibility(View.INVISIBLE);
                    second.setVisibility(View.VISIBLE);
                    code_created = "isafe/" + cn + "/" + Signup2.team;

                    code.setText(code_created);
                    codeg = code.getText().toString();

                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Users")
                            .child(userid)
                            .setValue(new UserPost(Signup2.profilename, "Team Leader", code_created));

                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(userid)
                            .child("Domain")
                            .setValue(Signup2.s);

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Codes")
                            .push();


                    String key = databaseReference.getKey();

                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Code");

                    dbref.child(key).setValue(new CodeGen(userid, codeg));

                    DatabaseReference db = dbref.child(key);

                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                System.out.println(child.getKey()); // "-key1", "-key2", etc
                                System.out.println(child.getValue()); // true, true, etc
                            }

                            Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                            Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

                            ArrayList<String> list = new ArrayList<>();


                            while (iterator.hasNext()) {

                                DataSnapshot next = (DataSnapshot) iterator.next();
                                Log.i("cdsjk", "Value = " + next.getValue());
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            CodeGen codeGen = dataSnapshot.getValue(CodeGen.class);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    continuee.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent home = new Intent(CodeGenerator.this, HomePageActivity.class);
                            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(home);
                        }
                    });

                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            final CharSequence options[] = new CharSequence[]{"Share ", "Share as Text Message", "Copy Code"};

                            AlertDialog.Builder builder = new AlertDialog.Builder(CodeGenerator.this);
                            builder.setCancelable(true);
                            builder.setTitle("Select your option:");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @SuppressLint("IntentReset")
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if ("Share ".equals(options[which])) {


                                        Intent a = new Intent(Intent.ACTION_SEND);
                                        a.setType("text/plain");
                                        String shareBody = "Hi, join the college team using this code: " + codeg;
                                        String shareSub = "iSAFE";
                                        a.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                        a.putExtra(Intent.EXTRA_TEXT, shareBody);
                                        startActivity(Intent.createChooser(a, "Share Using"));

                                    } else if ("Share as Text Message".equals(options[which])) {

                                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                        sendIntent.setType("vnd.android-dir/mms-sms");
                                        sendIntent.setData(Uri.parse("smsto:"));
                                        sendIntent.putExtra("sms_body", "Hi, join the college team using this code: " + codeg);
                                        startActivity(sendIntent);


                                    } else if ("Copy Code".equals(options[which])) {

                                        ClipboardManager clipboard = (ClipboardManager) CodeGenerator.this.getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("Copied Text", codeg);
                                        clipboard.setPrimaryClip(clip);

                                        Toast.makeText(CodeGenerator.this, "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            builder.show();
                        }
                    });
                } else {
                    Toast.makeText(CodeGenerator.this, "Please enter your college name", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
