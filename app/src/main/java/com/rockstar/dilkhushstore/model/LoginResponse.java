package com.rockstar.dilkhushstore.model;

import java.util.ArrayList;

public class LoginResponse {
    private String Message;
    private ArrayList<LoginBO> Data;
    private int Responsecode;

    public String getMessage( ) {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<LoginBO> getData( ) {
        if(this.Data==null){
            this.Data=new ArrayList<LoginBO>();
        }
        return Data;
    }

    public void setData(ArrayList<LoginBO> data) {
        Data = data;
    }

    public int getResponsecode( ) {
        return Responsecode;
    }

    public void setResponsecode(int responsecode) {
        Responsecode = responsecode;
    }
}
