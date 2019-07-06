package com.solve.isafe.Classes;

public class AddFile {

    String id, filename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public AddFile(String id, String filename) {
        this.id = id;
        this.filename = filename;
    }
}
