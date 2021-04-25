package com.rockstar.dilkhushstore.model.advertisement;

import java.util.ArrayList;

public class  AdvertisementResponse {
    private String Message;
    private int Responsecode;
    private ArrayList<AdvertismentData> Data;

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

    public ArrayList<AdvertismentData> getData( ) {
        if(this.Data==null){
            this.Data=new ArrayList<>();
        }
        return Data;
    }

    public void setData(ArrayList<AdvertismentData> data) {
        Data = data;
    }
}
