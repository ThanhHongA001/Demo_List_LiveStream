package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;
import com.viettel.mocha.rmlivestream.model.RMLivestream;

import java.io.Serializable;

public class RMLivestreamDetailResponse implements Serializable {
    @SerializedName("data")
    private RMLivestream model;

    public RMLivestream getModel() {
        return model;
    }
}
