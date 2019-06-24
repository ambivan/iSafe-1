package com.example.isafe.Classes;

public class Upload {

    public String name1;
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String url) {
        this.name1 = name;
        this.url = url;
    }

    public String getName1() {
        return name1;
    }

    public String getUrl() {
        return url;
    }
}
