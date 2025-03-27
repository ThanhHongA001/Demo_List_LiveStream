package com.example.demo_livestream.Api;

import retrofit2.Response;

public interface APICallBack<T> {
    void onResponse(Response<T> response);

    void onError(Throwable error);
}
