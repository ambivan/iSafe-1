package com.example.isafe.Classes;

public class MyListData{
    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }

    private  int image;
    private  String title;
    private String event;
    private String date;
    private String time;
    private String topic;
    private String city;
    private String is_liked;
    private String eventid;

    public void setImage(int image) {
        this.image = image;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public MyListData(String eventid, String title, String city, String event, String date, String time, String topic, String is_liked) {

    this.title = title;
    this.date = date;
    this.event = event;
    this.time = time;
    this.topic = topic;
    this.city = city;
    this.is_liked = is_liked;
    this.eventid = eventid;

  }

  public MyListData(){

  }

    public void setCity(String city) {
        this.city = city;
    }

    public int getImage() {
    return image;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setImg(int img) {
    this.image = img;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public void setTopic(String topic) {
    this.topic = topic;
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

  public String getCity() {
        return city;
    }

}