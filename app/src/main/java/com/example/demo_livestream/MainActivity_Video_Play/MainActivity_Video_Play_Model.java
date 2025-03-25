package com.example.demo_livestream.MainActivity_Video_Play;

public class MainActivity_Video_Play_Model {
    private String title;
    private String username;
    private int views;
    private String time;

    public MainActivity_Video_Play_Model(String title, String username, int views, String time) {
        this.title = title;
        this.username = username;
        this.views = views;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public int getViews() {
        return views;
    }

    public String getTime() {
        return time;
    }
}
