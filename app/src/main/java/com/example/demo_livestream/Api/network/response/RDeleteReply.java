package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RDeleteReply implements Serializable {
    @SerializedName("data")
    private int data;

    public RDeleteReply(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
