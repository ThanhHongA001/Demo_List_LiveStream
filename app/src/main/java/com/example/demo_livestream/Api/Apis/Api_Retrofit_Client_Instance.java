package com.example.demo_livestream.Api.Apis;

import com.example.demo_livestream.Api.Http.HttpsTrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_Retrofit_Client_Instance {
    private static final String TAG = Api_Retrofit_Client_Instance.class.getSimpleName();
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = HttpsTrustManager.getUnsafeOkHttpClient();
            retrofit = new Retrofit.Builder()
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(Api_Live.Domain_LiveStream)                    // Sử dụng domain chính của API livestream
                    .addConverterFactory(GsonConverterFactory.create())     // Chuyển đổi JSON sang Object
                    .build();
        }
        return retrofit;
    }

    private String generateSecurityToken() {
        return "secure_token_example";
    }
}
