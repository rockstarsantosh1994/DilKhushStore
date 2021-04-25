package com.rockstar.dilkhushstore.model.products;

import java.util.ArrayList;

public class ProductsResponse {
    private String Message;
    private int Responsecode;
    private ArrayList<ProductBO> Data;

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

    public ArrayList<ProductBO> getData( ) {
        if(this.Data==null){
            this.Data=new ArrayList<>();
        }
        return Data;
    }

    public void setData(ArrayList<ProductBO> data) {
        Data = data;
    }
}
