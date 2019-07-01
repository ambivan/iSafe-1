package com.example.isafe.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.isafe.Activities.HomePageActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NotificationService extends Service {

    private NotificationManager notificationManager;
    private Notification myNotification;
    NotifyServiceReceiver notifyServiceReceiver;

    FirebaseAuth mAuth;

    View view;



    static ArrayList<String> al;
    TextView badge;

    int count1 = 0;


    public static final String ACTION_REQUEST_ACCEPTED = "ACTION-REQUEST-ACCEPTED";


    final static String ACTION = "NotifyServiceAction";
    final static String STOP_SERVICE = "";
    final static int RQS_STOP_SERVICE = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onCreate() {

        FirebaseApp.initializeApp(this);

        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        FirebaseApp.initializeApp(this);

        al = new ArrayList<>();

        intent = new Intent(getApplicationContext(), HomePageActivity.class);

        final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        registerReceiver(notifyServiceReceiver, intentFilter);

        mAuth = FirebaseAuth.getInstance();

        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = mAuth.getCurrentUser();


                if (user != null) {

                    final DatabaseReference a = FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


                    //WHEN EVENT IS CREATED
                    DatabaseReference b = FirebaseDatabase.getInstance().getReference()
                            .child("Events");

                    a.child("Status").setValue("0");


                    b.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                            count1+=1;
                            System.out.println("count"+count1);
                            a.child("Status").setValue(String.valueOf(count1));
                            Notification notification = new Notification.Builder(getApplicationContext())
                                    .setContentTitle("iSAFE New Event!")
                                    .setContentText("Check to see if it is near you!")
                                    .setContentIntent(pendingIntent)
                                    .addAction(android.R.drawable.ic_dialog_map, "View", pendingIntent)
                                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                                    .build();


                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(1, notification);
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    // WHEN MEMBER ADDED TO A TEAM
                    a.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                            String key = dataSnapshot.getKey();
                            if (!key.equals("name")
                                    && !key.equals("RoadSafetyAudit")
                                    && !key.equals("AttendedEvents")
                                    && !key.equals("MemberCount")
                                    && !key.equals("post")
                                    && !key.equals("Meeting Reports")
                                    && !key.equals("teamname")
                                    && !key.equals("Registered Events")
                                    && !key.equals("Domain")
                                    && !key.equals("Status")
                                    && !key.equals("Projects")
                                    && !key.equals("Accident Report")
                                    && !key.equals("EventDay")
                                    && !key.equals("Events")
                                    && !key.equals("Liked Events")
                                    && !key.equals("Reimbursements")
                                    && !key.equals("Profile URL")) {

                                count1+=1;
                                System.out.println("count"+count1);

                                a.child("Status").setValue(String.valueOf(count1));

                                Notification notification = new Notification.Builder(getApplicationContext())
                                        .setContentTitle("iSAFE")
                                        .setContentText("You have a new member!")
                                        .setContentIntent(pendingIntent)
                                        .addAction(android.R.drawable.ic_input_add, "View", pendingIntent)
                                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                                        .build();

                                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(1, notification);

                            }
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        };

        mAuth.addAuthStateListener(mAuthListener);

        super.onStartCommand(intent, flags, startId);

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent(this, NotifyServiceReceiver.class);

        sendBroadcast(broadcastIntent);
    }

    public class NotifyServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            int rqs = arg1.getIntExtra("RQS", 0);
            if (rqs == RQS_STOP_SERVICE) {
                stopSelf();
            }

            arg0.startService(new Intent(arg0, NotificationService.class));

        }
    }
}
