package com.rockstar.dilkhushstore.model;

public class CommonResponse {

    private String Message;
    private int Responsecode;

    public String getMessage( ) {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getResponsecode( ) {
        return Responsecode;
    }

    public void setResponsecode(int responsecode) {
        Responsecode = responsecode;
    }
}
