package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;
import com.viettel.mocha.rmlivestream.model.TopDonate;

import java.io.Serializable;
import java.util.ArrayList;

public class TopDonateResponse implements Serializable {
    @SerializedName("data")
    ArrayList<TopDonate> data;

    public ArrayList<TopDonate> getData() {
        if(data == null) {
             data = new ArrayList<>();
        }
        return data;
    }
}
