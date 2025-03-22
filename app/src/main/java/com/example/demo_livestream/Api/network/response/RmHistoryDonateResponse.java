package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;
import com.viettel.mocha.rmlivestream.model.RmHistoryDonate;
import com.viettel.mocha.rmlivestream.model.RmHistoryDonate;

import java.util.ArrayList;

public class RmHistoryDonateResponse {
    @SerializedName("data")
    private ArrayList<RmHistoryDonate> data;

    public ArrayList<RmHistoryDonate> getData() {
        return data;
    }

    public void setData(ArrayList<RmHistoryDonate> data) {
        this.data = data;
    }
}
