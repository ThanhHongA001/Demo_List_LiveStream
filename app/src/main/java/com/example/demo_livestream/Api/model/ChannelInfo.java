package com.example.demo_livestream.Api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChannelInfo {
    @SerializedName("id")
    @Expose
    private String id = "";
    @SerializedName("channelName")
    @Expose
    private String name = "";
    @SerializedName("channelAvatar")
    @Expose
    private String imageUrl = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
