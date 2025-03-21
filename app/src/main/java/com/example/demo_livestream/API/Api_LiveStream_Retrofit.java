package com.example.demo_livestream.API;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_LiveStream_Retrofit {
    private static final String DOMAIN_LIVESTREAM = "https://mytelapi-live.ringme.vn";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // Tạo interceptor để log request & response
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Cấu hình OkHttpClient với timeout
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            // Khởi tạo Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(DOMAIN_LIVESTREAM)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static Api_LiveStream_Interface getApiService() {
        return getClient().create(Api_LiveStream_Interface.class);
    }
}
