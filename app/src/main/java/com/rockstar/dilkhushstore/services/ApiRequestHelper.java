package com.rockstar.dilkhushstore.services;


import android.text.Html;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rockstar.dilkhushstore.model.LoginResponse;
import com.rockstar.dilkhushstore.model.advertisement.AdvertisementResponse;
import com.rockstar.dilkhushstore.model.products.ProductsResponse;
import com.rockstar.dilkhushstore.utility.AllKeys;
import com.rockstar.dilkhushstore.utility.ConfigUrl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequestHelper {

    public interface OnRequestComplete {
        void onSuccess(Object object);

        void onFailure(String apiResponse);
    }

    private static ApiRequestHelper instance;
    private DilKhush application;
    private DilKhushServices dilKhushServices;
    static Gson gson;

    public void login(Map<String, String> params, final OnRequestComplete onRequestComplete) {
        Call<LoginResponse> call = dilKhushServices.login(params);
        get_login_api(onRequestComplete, call);
    }

    public void addCustomer(Map<String, String> params, final OnRequestComplete onRequestComplete) {
        Call<LoginResponse> call = dilKhushServices.addCustomer(params);
        get_login_api(onRequestComplete, call);
    }

    private void get_login_api(final OnRequestComplete onRequestComplete, Call<LoginResponse> call) {
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure(AllKeys.UNPROPER_RESPONSE);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    public void loadAds( final OnRequestComplete onRequestComplete) {
        Call<AdvertisementResponse> call = dilKhushServices.loadAds();
        get_advertisement_api(onRequestComplete, call);
    }

    private void get_advertisement_api(final OnRequestComplete onRequestComplete, Call<AdvertisementResponse> call) {
        call.enqueue(new Callback<AdvertisementResponse>() {
            @Override
            public void onResponse(Call<AdvertisementResponse> call, Response<AdvertisementResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure(AllKeys.UNPROPER_RESPONSE);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AdvertisementResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    public void loadProducts( final OnRequestComplete onRequestComplete) {
        Call<ProductsResponse> call = dilKhushServices.loadProducts();
        get_load_products_api(onRequestComplete, call);
    }

    private void get_load_products_api(final OnRequestComplete onRequestComplete, Call<ProductsResponse> call) {
        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure(AllKeys.UNPROPER_RESPONSE);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }



    public static synchronized ApiRequestHelper init(DilKhush application) {
        if (null == instance) {
            instance = new ApiRequestHelper();
            instance.setApplication(application);

            gson = new GsonBuilder()
                    .setLenient()
                    .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
//                    .setExclusionStrategies(new ExclusionStrategy() {
//                        @Override
//                        public boolean shouldSkipField(FieldAttributes f) {
//                            return f.getDeclaringClass().equals(RealmObject.class);
//                        }
//
//                        @Override
//                        public boolean shouldSkipClass(Class<?> clazz) {
//                            return false;
//                        }
//                    })
                    .create();
            instance.createRestAdapter();
        }
        return instance;
    }


    private void handle_fail_response(Throwable t, OnRequestComplete onRequestComplete) {
        if (t.getMessage() != null) {
            if (t.getMessage().contains("Unable to resolve host")) {
                onRequestComplete.onFailure(AllKeys.NO_INTERNET_AVAILABLE);
            } else {
                onRequestComplete.onFailure(Html.fromHtml(t.getLocalizedMessage()) + "");
            }
        } else
            onRequestComplete.onFailure(AllKeys.UNPROPER_RESPONSE);
    }

    private static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringAdapter();
        }
    }

    public static class StringAdapter extends TypeAdapter<String> {
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    /**
     * REST Adapter Configuration
     */
    private void createRestAdapter( ) {
//        ObjectMapper objectMapper = new ObjectMapper();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
// add your other interceptors …

// add logging as last interceptor
        httpClient.interceptors().add(logging);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(ConfigUrl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.client(httpClient.build()).build();
        dilKhushServices = retrofit.create(DilKhushServices.class);
    }

    /**
     * End REST Adapter Configuration
     */

    public DilKhush getApplication( ) {
        return application;
    }

    public void setApplication(DilKhush application) {
        this.application = application;
    }

}
