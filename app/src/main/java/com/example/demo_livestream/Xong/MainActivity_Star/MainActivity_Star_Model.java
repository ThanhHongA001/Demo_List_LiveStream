package com.example.demo_livestream.Xong.MainActivity_Star;

public class MainActivity_Star_Model {
    public static final int TYPE_ITEM_08 = 0;
    public static final int TYPE_ITEM_09 = 1;

    private int icon;
    private String title;
    private String time;
    private int starCount;
    private int viewType;

    public MainActivity_Star_Model(int icon, String title, String time, int starCount, int viewType) {
        this.icon = icon;
        this.title = title;
        this.time = time;
        this.starCount = starCount;
        this.viewType = viewType;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public int getStarCount() {
        return starCount;
    }

    public int getViewType() {
        return viewType;
    }
}
