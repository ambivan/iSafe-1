package com.example.isafe;

import android.provider.MediaStore;

public class UserPost {

  private String post;
  private String uid;

  public UserPost(){
    this.post = getPost();
  }


  public UserPost(String post, String uid){
    this.post = post;
    this.uid = uid;


  }

  public String getPost(){
    return post;
  }

  public void setPost(String post){

    this.post = post;

  }

  public void setuid(String uid){

    this.uid = uid;

  }

  public String getuid(){
    return uid;
  }

}