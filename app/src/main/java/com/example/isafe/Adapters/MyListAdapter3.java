package com.example.isafe.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isafe.Activities.ChatMeeting;
import com.example.isafe.Classes.Message;
import com.example.isafe.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

public class MyListAdapter3 extends FirebaseListAdapter<Message> {

    private ChatMeeting activity;

    public MyListAdapter3(ChatMeeting activity, Class<Message> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
        this.activity = activity;
    }



    @Override
    protected void populateView(View v, Message model, int position) {
        TextView messageText = (TextView) v.findViewById(R.id.message_text);

        messageText.setText(model.getMessageText());

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Message chatMessage = getItem(position);

        if (chatMessage.getMessageUserId().equals(activity.getLoggedInUserName()))
            view = activity.getLayoutInflater().inflate(R.layout.outgoingmessage, viewGroup, false);
        else
            view = activity.getLayoutInflater().inflate(R.layout.incomingmessage, viewGroup, false);

        //generating view
        populateView(view, chatMessage, position);

        return view;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }
}