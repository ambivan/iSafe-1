package com.example.isafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AgendaMeeting extends AppCompatActivity {




    Button invite;
    Button startmeet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_meeting);


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



    }
}
