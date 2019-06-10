package com.example.isafe;

import android.provider.MediaStore;

public class UserPost {

  private String post;
  private String uid;
  private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public UserPost(){
  }

  public UserPost(String Name, String post){
    this.Name = Name;
    this.post = post;

  }

  public String getPost(){
    return post;
  }

  public void setPost(String post){

    this.post = post;

  }

}