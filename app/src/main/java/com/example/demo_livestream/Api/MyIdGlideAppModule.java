package com.viettel.mocha.rmlivestream;

import android.content.Context;
//import android.support.annotation.NonNull;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.viettel.mocha.module.selfcare.network.HttpsTrustManager;
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
//        int diskCacheSizeBytes = BITMAP_DISK_CACHE_EXTRA_SIZE;
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "MyIDGlideCache", diskCacheSizeBytes));
//        int memoryCacheSizeBytes = 1024 * 1024 * 100;// 100MB
//        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes))
//                .setDefaultTransitionOptions(Drawable.class, DrawableTransitionOptions.withCrossFade())
//                .setDefaultTransitionOptions(Bitmap.class, BitmapTransitionOptions.withCrossFade())
//                .setDefaultRequestOptions(new RequestOptions()
//                        .format(DecodeFormat.PREFER_RGB_565)
//                        .placeholder(R.color.sc_color_place_holder)
//                        .error(R.color.sc_color_place_holder)
//                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                        .fitCenter());
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient okHttpClient= HttpsTrustManager.getUnsafeOkHttpClient();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }

}
