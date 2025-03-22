package com.example.demo_livestream.MainActivity_Home.Fragment_Model;

public class MainActivity_Home_Fragment01_Model {
    private String id;
    private String title;
    private String thumbnail; // Ảnh đại diện của livestream

    public MainActivity_Home_Fragment01_Model(String id, String title, String thumbnail) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
