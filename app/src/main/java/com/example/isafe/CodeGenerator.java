package com.example.isafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.security.SecureRandom;

public class CodeGenerator extends AppCompatActivity {

    String AB;
    SecureRandom rnd;

    LinearLayout first,second;

    EditText collegename, code;

    static String codeg;
    Button share, generate, continuee;


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

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                first.setVisibility(View.INVISIBLE);
                second.setVisibility(View.VISIBLE);

                AB = "012345678901234567890123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                rnd = new SecureRandom();

                code.setText(randomString());
                codeg = code.getText().toString();

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
