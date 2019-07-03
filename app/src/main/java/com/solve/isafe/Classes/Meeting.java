package com.solve.isafe.Classes;

import java.util.Date;

public class Meeting {

    private String agenda, minutes, tasks;
    private long date;

    public Meeting (){
        date = new Date().getTime();
    }

    public String getMinutes() {
        return minutes;
    }

    public long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public Meeting(String agenda, String minutes, String tasks){

        this.agenda = agenda;
        this.minutes = minutes;
        this.tasks = tasks;
        date = new Date().getTime();

    }

}
