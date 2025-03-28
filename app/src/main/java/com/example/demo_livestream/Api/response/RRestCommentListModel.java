package com.example.demo_livestream.Api.response;

import com.example.demo_livestream.Api.model.RmCommentModel;
import com.google.gson.annotations.SerializedName;
//import com.viettel.mocha.rmlivestream.model.RmCommentModel;

import java.io.Serializable;
import java.util.ArrayList;

public class RRestCommentListModel implements Serializable {

    @SerializedName("data")
    private ArrayList<RmCommentModel> data;

    public ArrayList<RmCommentModel> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    @Override
    public String toString() {
        return "RRestCommentModel{" +
                "data=" + data +
                '}';
    }
}