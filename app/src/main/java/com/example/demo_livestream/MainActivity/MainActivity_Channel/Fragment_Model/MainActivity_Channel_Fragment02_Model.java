package com.example.demo_livestream.MainActivity.MainActivity_Channel.Fragment_Model;

public class MainActivity_Channel_Fragment02_Model {
    private String title;
    private String author;
    private String views;
    private String time;
    private int thumbnailResId;
    private int liveIconResId;

    public MainActivity_Channel_Fragment02_Model(String title, String author, String views, String time, int thumbnailResId, int liveIconResId) {
        this.title = title;
        this.author = author;
        this.views = views;
        this.time = time;
        this.thumbnailResId = thumbnailResId;
        this.liveIconResId = liveIconResId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getViews() {
        return views;
    }

    public String getTime() {
        return time;
    }

    public int getThumbnailResId() {
        return thumbnailResId;
    }

    public int getLiveIconResId() {
        return liveIconResId;
    }
}
