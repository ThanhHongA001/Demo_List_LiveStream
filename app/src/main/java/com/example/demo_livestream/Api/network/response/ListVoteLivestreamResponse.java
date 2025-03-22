package com.example.demo_livestream.Api.network.response;

import com.google.gson.annotations.SerializedName;
import com.viettel.mocha.rmlivestream.model.LivestreamVoteModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ListVoteLivestreamResponse implements Serializable {
    @SerializedName("data")
    private ArrayList<LivestreamVoteModel> data;

    public ArrayList<LivestreamVoteModel> getData() {
        if(data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(ArrayList<LivestreamVoteModel> data) {
        this.data = data;
    }
}
