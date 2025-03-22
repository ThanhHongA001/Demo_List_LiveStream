package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;
import com.viettel.mocha.module.community.model.response.BaseResponse;

public class LikeResponse extends BaseResponse {
    @SerializedName("data")
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
