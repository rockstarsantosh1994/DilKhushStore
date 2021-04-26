package com.rockstar.dilkhushstore.services;

import com.rockstar.dilkhushstore.model.CommonResponse;
import com.rockstar.dilkhushstore.model.LoginResponse;
import com.rockstar.dilkhushstore.model.advertisement.AdvertisementResponse;
import com.rockstar.dilkhushstore.model.products.ProductsResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DilKhushServices {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("addcustomer")
    Call<LoginResponse> addCustomer(@FieldMap Map<String, String> params);

    @GET("loadads")
    Call<AdvertisementResponse> loadAds();

    @GET("loadproducts")
    Call<ProductsResponse> loadProducts();

    @FormUrlEncoded
    @POST("placeorder")
    Call<CommonResponse> placeOrder(@FieldMap Map<String, String> params);

}
