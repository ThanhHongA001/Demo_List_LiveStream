package com.example.demo_livestream.MainActivity.MainActivity_Search.Fragment_Model;

public class MainActivity_Search_Fragment02_Model {
    private String title;
    private String streamer;
    private String views;
    private String time;

    public MainActivity_Search_Fragment02_Model(String title, String streamer, String views, String time) {
        this.title = title;
        this.streamer = streamer;
        this.views = views;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getStreamer() {
        return streamer;
    }

    public String getViews() {
        return views;
    }

    public String getTime() {
        return time;
    }
}
