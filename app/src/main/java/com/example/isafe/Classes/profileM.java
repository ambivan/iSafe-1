package com.example.isafe.Classes;

public class profileM {

    private String name;
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public profileM(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public  profileM(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
