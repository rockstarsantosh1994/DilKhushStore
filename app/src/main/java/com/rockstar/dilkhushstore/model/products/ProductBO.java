package com.rockstar.dilkhushstore.model.products;

import java.util.ArrayList;

public class ProductBO {
    private String productid;
    private String productname;
    private String price;
    private String imagepath;
    private String unitprice;
    private ArrayList<MappingBO> mapping;

    public String getProductid( ) {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname( ) {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice( ) {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImagepath( ) {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getUnitprice( ) {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public ArrayList<MappingBO> getMapping( ) {
        if (this.mapping == null) {
            this.mapping = new ArrayList<MappingBO>();
        }
        return mapping;
    }

    public void setMapping(ArrayList<MappingBO> mapping) {
        this.mapping = mapping;
    }
}
