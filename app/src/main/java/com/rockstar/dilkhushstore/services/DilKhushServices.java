package com.rockstar.dilkhushstore.services;

import com.rockstar.dilkhushstore.model.LoginResponse;

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

    //@GET("getAllMedicineDosage.php")
    //Call<DosageResponse> getAllMedicineDosage();

}
