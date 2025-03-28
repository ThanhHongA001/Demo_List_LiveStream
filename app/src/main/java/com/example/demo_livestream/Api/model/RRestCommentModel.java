package com.example.demo_livestream.Api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RRestCommentModel implements Serializable {

    @SerializedName("commentItem")
    private RmCommentModel data;

    public RmCommentModel getData() {
        return data;
    }

    @Override
    public String toString() {
        return "RRestCommentModel{" +
                "data=" + data +
                '}';
    }
}