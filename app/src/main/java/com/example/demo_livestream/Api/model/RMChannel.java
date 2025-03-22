package com.example.demo_livestream.Api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RMChannel implements Serializable {
    @SerializedName("id")
    @Expose
    private String id = "";
    @SerializedName("channelName")
    @Expose
    private String name = "";
    @SerializedName("channelAvatar")
    @Expose
    private String imageUrl = "";
    @SerializedName("description")
    @Expose
    private String description = "";
    @SerializedName("headerBanner")
    @Expose
    private String imageCoverUrl = "";
    @SerializedName("numFollows")
    @Expose
    private long numfollow = 0;
    @SerializedName("numVideos")
    @Expose
    private int numVideo = 0;
    @SerializedName("isOfficial")
    @Expose
    private int isOfficial;
    @SerializedName("isFollow")
    @Expose
    private int isFollow;
    @SerializedName("createdFrom")
    @Expose
    private long createdDate;
    @SerializedName("isOwner")
    private int isMyChannel;
    @SerializedName("url")
    private String url="";
    @SerializedName("state")
    private String state;
    @SerializedName("statusLive")
    private boolean statusLive;
    @SerializedName("owner")
    private boolean owner;

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getImageCoverUrl() {
        return imageCoverUrl;
    }

    public long getNumfollow() {
        return numfollow;
    }

    public int getNumVideo() {
        return numVideo;
    }

    public int getIsOfficial() {
        return isOfficial;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public int getIsMyChannel() {
        return isMyChannel;
    }

    public String getUrl() {
        return url;
    }

    public String getState() {
        return state;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageCoverUrl(String imageCoverUrl) {
        this.imageCoverUrl = imageCoverUrl;
    }

    public void setNumfollow(long numfollow) {
        this.numfollow = numfollow;
    }

    public void setNumVideo(int numVideo) {
        this.numVideo = numVideo;
    }

    public void setIsOfficial(int isOfficial) {
        this.isOfficial = isOfficial;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public void setIsMyChannel(int isMyChannel) {
        this.isMyChannel = isMyChannel;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setState(String state) {
        this.state = state;
    }
    public void setFollow(boolean follow) {
        this.isFollow = follow ? 1 : 0;
    }

    public boolean getStatusLive() {
        return statusLive;
    }
}
