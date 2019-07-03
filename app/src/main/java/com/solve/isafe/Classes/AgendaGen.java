package com.solve.isafe.Classes;

public class AgendaGen {

    private String userid;
    private String agenda;


    public AgendaGen (){

    }

    public AgendaGen(String userid, String agenda){
        this.userid = userid;
        this.agenda = agenda;

    }

    public String getCode() {
        return agenda;
    }

    public void setCode(String agenda) {
        this.agenda = agenda;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
