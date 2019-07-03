package com.solve.isafe.Classes;

public class RSA {

    private String rname;
    private String id;
    private String length;
    private String startlat;
    private String startlong;
    private String endlat, endlong;

    public RSA(String rname, String id, String length, String startlat, String startlong, String endlat, String endlong) {
        this.rname = rname;
        this.id = id;
        this.length = length;
        this.startlat = startlat;
        this.startlong = startlong;
        this.endlat = endlat;
        this.endlong = endlong;
    }

    public RSA() {
    }

    public String getStartlat() {
        return startlat;
    }

    public void setStartlat(String startlat) {
        this.startlat = startlat;
    }

    public String getStartlong() {
        return startlong;
    }

    public void setStartlong(String startlong) {
        this.startlong = startlong;
    }

    public String getEndlat() {
        return endlat;
    }

    public void setEndlat(String endlat) {
        this.endlat = endlat;
    }

    public String getEndlong() {
        return endlong;
    }

    public void setEndlong(String endlong) {
        this.endlong = endlong;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
