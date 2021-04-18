package com.rockstar.dilkhushstore.model;

public class LoginBO {
    private String contactnumber;
    private String fname;
    private String lname;
    private String locationAddress;
    private String customerid;

    public String getContactnumber( ) {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getFname( ) {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname( ) {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLocationAddress( ) {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getCustomerid( ) {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }
}