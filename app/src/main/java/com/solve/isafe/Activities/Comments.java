package com.solve.isafe.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solve.isafe.Adapters.CommentAdapter;
import com.solve.isafe.Adapters.MyListAdapter;
import com.solve.isafe.Classes.CommentClass;
import com.solve.isafe.Classes.Message;
import com.solve.isafe.Classes.UserPost;
import com.solve.isafe.R;

public class Comments extends AppCompatActivity {

    TextView event, date, time, city, topic, title;

    FirebaseListAdapter<CommentClass> adapter;

    ImageView send, profile, share, like;


    EditText comment;

    ListView lissss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar12);

        setSupportActionBar(toolbar);

        lissss = (ListView) findViewById(R.id.listteam);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        Window window = Comments.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(Comments.this, R.color.mystatus));

        city = findViewById(R.id.citynamec);
        title = findViewById(R.id.titlec);
        topic = findViewById(R.id.topicc);
        event = findViewById(R.id.eventc);
        date = findViewById(R.id.datec);
        time = findViewById(R.id.timec);
        profile = findViewById(R.id.commentimage);
        share = findViewById(R.id.send);
        like = findViewById(R.id.heart);


        city.setText(MyListAdapter.ccity);
        title.setText(MyListAdapter.ctitle);
        topic.setText(MyListAdapter.ctopic);
        event.setText(MyListAdapter.cevent);
        date.setText(MyListAdapter.cdate);
        time.setText(MyListAdapter.ctime);

        send = findViewById(R.id.comment);
        comment = findViewById(R.id.coments);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (comment.getText().toString().trim().equals("")) {
                    Toast.makeText(Comments.this, "Please enter some texts!", Toast.LENGTH_SHORT).show();
                } else {

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            UserPost userPost = dataSnapshot.getValue(UserPost.class);

                            FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child("Events")
                                    .child(MyListAdapter.cid)
                                    .child("Comments")
                                    .push()
                                    .setValue(new Message(comment.getText().toString().trim(),
                                            userPost.getName(),
                                            FirebaseAuth.getInstance().getCurrentUser().getUid()));

                            comment.setText("");
                            ((InputMethodManager)Comments.this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),0);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });

                }

            }

        });

        adapter = new CommentAdapter(Comments.this, CommentClass.class, R.layout.outgoingmessage,
                FirebaseDatabase.getInstance().getReference().child("Events").child(MyListAdapter.cid).child("Comments"));

        lissss.setAdapter(adapter);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(Intent.ACTION_SEND);

                String eventtext = MyListAdapter.ctitle + MyListAdapter.ccity + " is " + MyListAdapter.cevent + " " + MyListAdapter.cdate + ".\n" + MyListAdapter.ctime
                        + "\n" + MyListAdapter.ctopic;

                a.setType("text/plain");
                String shareBody = "Hey!!" + "\n" + eventtext + "\n" + "Would You like to be a part of this?";
                String shareSub = "iSAFE";
                a.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                a.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(a, "Share Using"));
            }
        });

    }
}
