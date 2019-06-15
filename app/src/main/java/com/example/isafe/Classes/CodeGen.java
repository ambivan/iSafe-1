package com.example.isafe.Classes;

public class CodeGen {

    private String userid;
    private String code;


    public CodeGen (){

    }

    public CodeGen(String userid, String code){
        this.userid = userid;
        this.code = code;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
