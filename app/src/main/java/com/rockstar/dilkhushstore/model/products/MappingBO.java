package com.rockstar.dilkhushstore.model.products;

public class MappingBO {
   private String mapid;
   private String productid;
   private String pvalue;
   private String punit;
   private String price;

    public String getMapid( ) {
        return mapid;
    }

    public void setMapid(String mapid) {
        this.mapid = mapid;
    }

    public String getProductid( ) {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPvalue( ) {
        return pvalue;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue;
    }

    public String getPunit( ) {
        return punit;
    }

    public void setPunit(String punit) {
        this.punit = punit;
    }

    public String getPrice( ) {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}