package com.example.demo_livestream.Api.response;

import com.example.demo_livestream.Api.model.RMLivestream;
import com.google.gson.annotations.SerializedName;
//import com.viettel.mocha.rmlivestream.model.RMLivestream;

import java.io.Serializable;

public class RMLivestreamDetailResponse implements Serializable {
    @SerializedName("data")
    private RMLivestream model;

    public RMLivestream getModel() {
        return model;
    }
}
