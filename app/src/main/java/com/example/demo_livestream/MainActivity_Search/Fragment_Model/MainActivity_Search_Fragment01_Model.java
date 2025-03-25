package com.example.demo_livestream.MainActivity_Search.Fragment_Model;

public class MainActivity_Search_Fragment01_Model {
    private String name;
    private String followerCount;
    private String videoCount;
    private int imageResource;

    public MainActivity_Search_Fragment01_Model(String name, String followerCount, String videoCount, int imageResource) {
        this.name = name;
        this.followerCount = followerCount;
        this.videoCount = videoCount;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getFollowerCount() {
        return followerCount;
    }

    public String getVideoCount() {
        return videoCount;
    }

    public int getImageResource() {
        return imageResource;
    }
}
