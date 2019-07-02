package com.example.isafe.Classes;

public class EventChecklist2 {

    private String name, brief, impact, datee;

    public EventChecklist2(String name, String brief, String impact, String datee) {
        this.name = name;
        this.brief = brief;
        this.impact = impact;
        this.datee = datee;
    }

    public EventChecklist2(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }
}
