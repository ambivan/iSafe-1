package com.solve.isafe.Services;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.solve.isafe.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

    }

    public void showNotification(String title, String message){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My notifications")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.notif)
                .setAutoCancel(true)
                .setContentText(message);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());

        String topic = "highScores";


        String messageid = String.valueOf(System.currentTimeMillis());

        RemoteMessage remoteMessage = new RemoteMessage.Builder("86299482060" + "@gcm.googleapis.com")
                .setMessageId(messageid)
                .addData("Yaya", "yayay")
                .build();

        FirebaseMessaging.getInstance()
                .send(remoteMessage);


        // See documentation on defining a message payload.

    }
}

