package com.solve.isafe.Classes;

public class Reimbursements {

    private String eventtype, eventdate;

    public Reimbursements() {
    }

    public Reimbursements(String eventtype, String eventdate) {
        this.eventtype = eventtype;
        this.eventdate = eventdate;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }
}
