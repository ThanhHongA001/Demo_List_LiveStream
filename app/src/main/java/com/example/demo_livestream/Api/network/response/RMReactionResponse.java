package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;
import com.viettel.mocha.rmlivestream.model.RMReaction;

import java.io.Serializable;

public class RMReactionResponse implements Serializable {
    @SerializedName("data")
    private RMReaction data;

    @SerializedName("code")
    private int code;

    public RMReaction getData() {
        return data;
    }

    public void setData(RMReaction data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
