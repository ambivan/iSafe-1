package com.example.isafe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;

public class CodeGenerator extends AppCompatActivity {

    String AB;
    SecureRandom rnd;

    LinearLayout first,second;

    EditText collegename, code;

    String userid;

    static String codeg;
    Button share, generate, continuee;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_generator);

        first = (LinearLayout)findViewById(R.id.first_layer);
        second = (LinearLayout)findViewById(R.id.second_layer);
        collegename = (EditText) findViewById(R.id.collegename);
        code = (EditText) findViewById(R.id.code);
        share = (Button) findViewById(R.id.share);
        generate = (Button) findViewById(R.id.generate);
        continuee = (Button) findViewById(R.id.conntinue);

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

                first.setVisibility(View.INVISIBLE);
                second.setVisibility(View.VISIBLE);

                AB = "012345678901234567890123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                rnd = new SecureRandom();

                code.setText(randomString());
                codeg = code.getText().toString();

               final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                       .getReference()
                       .child("Codes")
                       .push();

               String key = databaseReference.getKey();

                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Code");

                dbref.child(key).setValue(new CodeGen(userid, codeg));

                System.out.println(key);

                DatabaseReference db = dbref.child(key);

                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot child: dataSnapshot.getChildren()) {
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
                        System.out.println(codeGen.getCode());
                        System.out.println(codeGen.getUserid());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                continuee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CodeGenerator.this, HomePageActivity.class));
                    }
                });

                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CodeGenerator.this, Share_popup.class) );
                    }
                });

            }
        });

    }

    String randomString(){
        StringBuilder sb = new StringBuilder(5);
        for(int i = 0; i < 5; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
