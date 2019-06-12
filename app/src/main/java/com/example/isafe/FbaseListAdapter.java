package com.example.isafe;

import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

 public class FbaseListAdapter extends FirebaseListAdapter<Chat> {

    ChatMeeting chat;

    public FbaseListAdapter(ChatMeeting chat, Class<Chat> modelClass, int modelLayout, DatabaseReference ref) {
        super(chat, modelClass, modelLayout, ref);
        this.chat = chat;
    }

    @Override
    protected void populateView(View v, Chat model, int position) {
        TextView messageText = (TextView) v.findViewById(R.id.message_text);
        TextView messageUser = (TextView) v.findViewById(R.id.message_user);
        TextView messageTime = (TextView) v.findViewById(R.id.message_time);

        messageText.setText(model.getMessageText());
        messageUser.setText(model.getMessageUser());

        // Format the date before showing it
        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Chat chatMessage = getItem(position);

        if (chatMessage.getMessageUserId().equals(chat.getLoggedInUserName()))
            view = chat.getLayoutInflater().inflate(R.layout.outgoingmessage, viewGroup, false);
        else
            view = chat.getLayoutInflater().inflate(R.layout.incomingmessage, viewGroup, false);

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