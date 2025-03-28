package com.example.demo_livestream.Api;

import android.content.Context;
//import android.support.annotation.NonNull;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.example.demo_livestream.Api.Http.HttpsTrustManager;
//import com.viettel.mocha.module.selfcare.network.HttpsTrustManager;
//
//import java.io.InputStream;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//

import java.io.InputStream;

import okhttp3.OkHttpClient;

//
///**
// * Created by dainv00 on 5/15/2020
// */
//
@GlideModule
public class MyIdGlideAppModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);

    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient okHttpClient= HttpsTrustManager.getUnsafeOkHttpClient();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }

}
