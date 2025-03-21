package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;
import com.viettel.mocha.rmlivestream.model.RMLivestream;

import java.io.Serializable;
import java.util.ArrayList;

public class RMLivestreamResponse implements Serializable {
    @SerializedName("data")
    ArrayList<RMLivestream> listLivStream;

    public ArrayList<RMLivestream> getListLivStream() {
        if(listLivStream == null) {
            listLivStream = new ArrayList<>();
        }
        return listLivStream;
    }
}
