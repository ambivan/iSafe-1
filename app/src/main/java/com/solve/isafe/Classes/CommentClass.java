package com.solve.isafe.Classes;

public class CommentClass {
    private String messageText;
    private String messageUser;
    private String messageUserId;

    public CommentClass(String messageText, String messageUser, String messageUserId) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageUserId = messageUserId;
    }

    public CommentClass(){

    }

    public String getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(String messageUserId) {
        this.messageUserId = messageUserId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

}