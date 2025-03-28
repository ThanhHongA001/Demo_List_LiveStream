package com.example.demo_livestream.Api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoInfo {
    @SerializedName("id")
    @Expose
    private String id = "";
    @SerializedName("videoTitle")
    @Expose
    private String title = "";
    @SerializedName("videoDesc")
    @Expose
    private String description = "";
    @SerializedName("videoImage")
    @Expose
    private String imagePath = "";
    @SerializedName("videoMedia")
    @Expose
    private String originalPath = "";
    @SerializedName("videoTime")
    @Expose
    private String duration;
    @SerializedName("publishTime")
    @Expose
    private long publishTime = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
}
