package com.example.demo_livestream.Api.response;

import com.example.demo_livestream.Api.model.RMLivestream;
import com.google.gson.annotations.SerializedName;
//import com.viettel.mocha.rmlivestream.model.RMLivestream;

import java.util.ArrayList;
import java.util.List;

public class RmVideoInChannelResponse {
    @SerializedName("data")
    private ArrayList<RMLivestream> data;

    public ArrayList<RMLivestream> getData() {
        if(data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(ArrayList<RMLivestream> data) {
        this.data = data;
    }
}
