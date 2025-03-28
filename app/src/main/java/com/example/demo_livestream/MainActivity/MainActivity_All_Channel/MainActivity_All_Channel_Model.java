package com.example.demo_livestream.MainActivity.MainActivity_All_Channel;

public class MainActivity_All_Channel_Model {
    private int avatarResId;
    private String name;
    private int followers;
    private int videos;

    public MainActivity_All_Channel_Model(int avatarResId, String name, int followers, int videos) {
        this.avatarResId = avatarResId;
        this.name = name;
        this.followers = followers;
        this.videos = videos;
    }

    public int getAvatarResId() {
        return avatarResId;
    }

    public String getName() {
        return name;
    }

    public int getFollowers() {
        return followers;
    }

    public int getVideos() {
        return videos;
    }
}
