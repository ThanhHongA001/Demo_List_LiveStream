package com.example.demo_livestream.Api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RmHistoryDonate {
    @SerializedName("id")
    @Expose
    private String id = "";

    @SerializedName("type")
    @Expose
    private Integer type = 0;

    @SerializedName("userId")
    @Expose
    private String userId = "";

    @SerializedName("nameChannel")
    @Expose
    private String nameChannel = "";

    @SerializedName("titleLivestream")
    @Expose
    private String titleLivestream = "";


    @SerializedName("amountStar")
    @Expose
    private Integer amountStar ;

    @SerializedName("createdAt")
    @Expose
    private String createdAt = "";

    @SerializedName("pricePackage")
    @Expose
    private Integer pricePackage;

    @SerializedName("giftImage")
    @Expose
    private String giftImage = "";

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    @SerializedName("giftName")
    @Expose
    private String giftName;

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "RmHistoryDonate{" +
                "titleLivestream='" + titleLivestream + '\'' +
                ", nameChannel='" + nameChannel + '\'' +
                ", giftImage='" + giftImage + '\'' +
                ", giftName='" + giftName + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGiftImage() {
        return giftImage;
    }

    public void setGiftImage(String giftImage) {
        this.giftImage = giftImage;
    }

    @SerializedName("message")
    @Expose
    private String message = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNameChannel() {
        return nameChannel;
    }

    public void setNameChannel(String nameChannel) {
        this.nameChannel = nameChannel;
    }

    public String getTitleLivestream() {
        return titleLivestream;
    }

    public void setTitleLivestream(String titleLivestream) {
        this.titleLivestream = titleLivestream;
    }

    public Integer getAmountStar() {
        return amountStar;
    }

    public void setAmountStar(Integer amountStar) {
        this.amountStar = amountStar;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getPricePackage() {
        return pricePackage;
    }

    public void setPricePackage(Integer pricePackage) {
        this.pricePackage = pricePackage;
    }
}
