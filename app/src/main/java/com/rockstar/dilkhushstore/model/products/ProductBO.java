package com.rockstar.dilkhushstore.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ProductBO implements Parcelable {
    private String productid;
    private String productname;
    private String price;
    private String imagepath;
    private String unitprice;
    private ArrayList<MappingBO> mapping;
    private MappingBO mappingBO;

    public ProductBO(String productid, String productname, String price, String imagepath, String unitprice, MappingBO mapping) {
        this.productid = productid;
        this.productname = productname;
        this.price = price;
        this.imagepath = imagepath;
        this.unitprice = unitprice;
        this.mappingBO = mapping;
    }

    public ProductBO(String productid, String productname, String price, String imagepath, String unitprice, ArrayList<MappingBO> mapping, MappingBO mappingBO) {
        this.productid = productid;
        this.productname = productname;
        this.price = price;
        this.imagepath = imagepath;
        this.unitprice = unitprice;
        this.mapping = mapping;
        this.mappingBO = mappingBO;
    }

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
        return mapping;
    }

    public void setMapping(ArrayList<MappingBO> mapping) {
        this.mapping = mapping;
    }

    public MappingBO getMappingBO( ) {
        return mappingBO;
    }

    public void setMappingBO(MappingBO mappingBO) {
        this.mappingBO = mappingBO;
    }

    protected ProductBO(Parcel in) {
        productid = in.readString();
        productname = in.readString();
        price = in.readString();
        imagepath = in.readString();
        unitprice = in.readString();
    }

    public static final Creator<ProductBO> CREATOR = new Creator<ProductBO>() {
        @Override
        public ProductBO createFromParcel(Parcel in) {
            return new ProductBO(in);
        }

        @Override
        public ProductBO[] newArray(int size) {
            return new ProductBO[size];
        }
    };

    @Override
    public int describeContents( ) {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productid);
        parcel.writeString(productname);
        parcel.writeString(price);
        parcel.writeString(imagepath);
        parcel.writeString(unitprice);
    }
}
