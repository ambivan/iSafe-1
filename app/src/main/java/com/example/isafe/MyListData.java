package com.example.isafe;

public class MyListData{
    private  int image;
    private  String title, event, date, time, topic;



    public MyListData(int image ,String title, String event, String date, String time, String topic) {

        this.image = image;
        this.title = title;
        this.date = date;
        this.event = event;
        this.time = time;
        this.topic = topic;
    }


    public int getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(int img) {
        this.image = image;
    }

    public void setEvent(String title) {
        this.title = title;
    }

    public void setDate(String title) {
        this.title = title;
    }

    public void setTime(String title) {
        this.title = title;
    }

    public void setTopic(String title) {
        this.title = title;
    }


    public String getTitle(){
        return title;
    }

    public String getEvent(){
        return event;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getTopic(){
        return topic;
    }









}

