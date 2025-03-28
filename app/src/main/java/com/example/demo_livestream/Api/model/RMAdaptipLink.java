package com.example.demo_livestream.Api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RMAdaptipLink implements Serializable {
    @SerializedName("240")
    private String link240;
    @SerializedName("360")
    private String link360;
    @SerializedName("480")
    private String link480;
    @SerializedName("720")
    private String link720;
    @SerializedName("1080")
    private String link1080;
    @SerializedName("src")
    private String linkSrc;

    public String getLink240() {
        return link240;
    }

    public String getLink360() {
        return link360;
    }

    public String getLink480() {
        return link480;
    }

    public String getLink720() {
        return link720;
    }

    public String getLink1080() {
        return link1080;
    }

    public String getLinkSrc() {
        return linkSrc;
    }
}
