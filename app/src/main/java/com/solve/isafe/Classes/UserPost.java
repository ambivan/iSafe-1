package com.solve.isafe.Classes;

public class UserPost {

  private String post;
  private String Name;
  private String teamname;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public UserPost(){
  }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public UserPost(String Name, String post, String teamname){
    this.Name = Name;
    this.post = post;
    this.teamname = teamname;

  }

  public String getPost(){
    return post;
  }

  public void setPost(String post){

    this.post = post;

  }

}