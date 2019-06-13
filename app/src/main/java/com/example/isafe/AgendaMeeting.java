package com.example.isafe;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AgendaMeeting extends AppCompatActivity {

    ImageView chat;

    Button invite;
    Button startmeet;

//    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_meeting);


        chat = (ImageView) findViewById(R.id.chatnotif);
        invite = (Button) findViewById(R.id.inviteall);
        startmeet = (Button) findViewById(R.id.startmeet);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invite.setVisibility(View.INVISIBLE);

                startmeet.setVisibility(View.VISIBLE);
                startmeet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(AgendaMeeting.this, ChatMeeting.class));

                    }
                });

            }
        });

//        chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 fragment = new ChatBox();
//            }
//        });
//
//        if (fragment != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame2, fragment).addToBackStack("My fragments");
//            ft.commit();
//        }


    }
}
