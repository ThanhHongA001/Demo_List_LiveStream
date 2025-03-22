package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;

public class VoteResultResponse {
    @SerializedName("data")
    private boolean data;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
