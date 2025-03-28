package com.example.demo_livestream.Api.response;

import com.example.demo_livestream.Api.model.RmCommentModel;
import com.google.gson.annotations.SerializedName;
//import com.viettel.mocha.rmlivestream.model.RmCommentModel;


import java.io.Serializable;

public class RReplyModel implements Serializable {

    @SerializedName("data")
    private RmCommentModel data;

    public RmCommentModel getData() {
        return data;
    }

    @Override
    public String toString() {
        return "RReplyModel{" +
                "data=" + data +
                '}';
    }
}