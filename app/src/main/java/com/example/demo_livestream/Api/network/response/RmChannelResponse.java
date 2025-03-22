package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;
import com.viettel.mocha.rmlivestream.model.RMChannel;

import java.util.ArrayList;
import java.util.List;

public class RmChannelResponse {
    @SerializedName("data")
    private ArrayList<RMChannel> data;

    public ArrayList<RMChannel> getData() {
        return data;
    }

    public void setData(ArrayList<RMChannel> data) {
        this.data = data;
    }
}
