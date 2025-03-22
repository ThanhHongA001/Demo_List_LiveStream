package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;
import com.viettel.mocha.rmlivestream.model.RMChannel;

import java.io.Serializable;

public class RRestChannelInfoModel implements Serializable {

    @SerializedName("data")
    private RMChannel data;

    public RMChannel getData() {
        return data;
    }

    @Override
    public String toString() {
        return "RRestChannelInfoModel{" +
                "data=" + data.toString() +
                '}';
    }
}
