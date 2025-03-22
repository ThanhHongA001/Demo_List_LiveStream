package com.example.demo_livestream.Api.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RMHomeLivestream implements Serializable {
    public static final int TYPE_BANNER = 1;
    public static final int TYPE_LIVESTREAM = 2;
    public static final int TYPE_CHANNEL = 3;
    private int type;
    private ArrayList<RMChannel> listChannel;
    private ArrayList<RMLivestream> listBanner;
    private ArrayList<RMLivestream> listVideo;
    private String title;

    public void setType(int type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setListVideo(ArrayList<RMLivestream> listVideo) {
        this.listVideo = listVideo;
    }

    public void setListBanner(ArrayList<RMLivestream> listBanner) {
        this.listBanner = listBanner;
    }

    public void setListChannel(ArrayList<RMChannel> listChannel) {
        this.listChannel = listChannel;
    }

    public int getType() {
        return type;
    }

    public ArrayList<RMChannel> getListChannel() {
        if(listChannel == null) {
            listChannel = new ArrayList<>();
        }
        return listChannel;
    }

    public ArrayList<RMLivestream> getListBanner() {
        if(listBanner == null) {
            listBanner = new ArrayList<>();
        }
        return listBanner;
    }

    public ArrayList<RMLivestream> getListVideo() {
        if(listVideo == null) {
            listVideo = new ArrayList<>();
        }
        return listVideo;
    }

    public String getTitle() {
        return title;
    }
}
