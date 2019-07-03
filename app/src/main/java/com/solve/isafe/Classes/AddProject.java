package com.solve.isafe.Classes;

public class AddProject {

    private String projname,description;

    public AddProject(String projname, String description) {
        this.projname = projname;
        this.description = description;
    }

    public AddProject(){

    }

    public String getProjname() {
        return projname;
    }

    public void setProjname(String projname) {
        this.projname = projname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
