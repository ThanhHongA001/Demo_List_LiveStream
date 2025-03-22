package com.example.demo_livestream.Api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.viettel.mocha.rmlivestream.model.LivestreamVoteModel;
import com.viettel.mocha.rmlivestream.model.RMAdaptipLink;

import java.io.Serializable;
import java.util.ArrayList;

public class RMLivestream implements Serializable {
    public static final int TYPE_LIVE = 1;
    public static final int TYPE_UPCOMING = 0;
    public static final int TYPE_VOD = 5;
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("status")
    private int status; //todo 1live, 0 upcoming, 5 vod
    @SerializedName("privacy")
    private int privacy;
    @SerializedName("hlsPlayLink")
    private String hlsPlaylink;
    @SerializedName("linkAvatar")
    private String linkAvatar;
    @SerializedName("totalView")
    private int totalView;
    @SerializedName("totalLike")
    private int totalLike;
    @SerializedName("totalComment")
    private int totalComment;
    @SerializedName("totalShare")
    private int totalShare;
    @SerializedName("timeStart")
    private long timeStart;
    @SerializedName("ageLimit")
    private int ageLimit;
    @SerializedName("enableChat")
    private int enableChat;
    @SerializedName("chatSetting")
    private int chatSetting;
    @SerializedName("channelId")
    private String channelId;
    @SerializedName("channelName")
    private String channelName;
    @SerializedName("totalViewStr")
    private String totalViewStr;
    @SerializedName("follow")
    private boolean follow;
    @SerializedName("enableDvr")
    private int enableDvr;
    @SerializedName("like")
    private boolean like;
    @SerializedName("blockComment")
    private boolean blockComment;
    @SerializedName("adaptiveLinks")
    private RMAdaptipLink adaptiveLinks;
    @SerializedName("bannerLink")
    private String bannerLnk;
    @SerializedName("totalFollow")
    private int totalFollow;
    @SerializedName("channel")
    private RMChannel channel;
    @SerializedName("videoId")
    private String videoId;
    @SerializedName("typeScreen")
    private int screenType = 0;
    @SerializedName("type")
    private int type;
    @SerializedName("timeEventStart")
    private long timeEventStart;
    @SerializedName("notify")
    private boolean isNotified;
    @SerializedName("numberPeopleWait")
    private long numberPeopleWait;
    @SerializedName("surveyDto")
    private ArrayList<LivestreamVoteModel> listVote;
    @SerializedName("enableDonate")
    private boolean enableDonate;
    @SerializedName("owner")
    private boolean owner;

    public String getId() {
        return id;
    }

//    public String getId() {
//        if(status!=0 && status!=1){
//            id=videoId;
//        }
//        return id;
//    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public int getPrivacy() {
        return privacy;
    }

    public String getHlsPlaylink() {
        return hlsPlaylink;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public int getTotalView() {
        return totalView;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public int getTotalShare() {
        return totalShare;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public int getEnableChat() {
        return enableChat;
    }

    public int getChatSetting() {
        return chatSetting;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getTotalViewStr() {
        return totalViewStr;
    }

    public boolean isFollow() {
        follow=channel.getIsFollow()==1;
        return follow;
    }

    public int getEnableDvr() {
        return enableDvr;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isBlockComment() {
        return blockComment;
    }

    public RMAdaptipLink getAdaptiveLinks() {
        return adaptiveLinks;
    }

    public String getBannerLnk() {
        return bannerLnk;
    }

    public int getTotalFollow() {
        return totalFollow;
    }

    public RMChannel getChannel() {
        return channel;
    }

//    public String getVideoId() {
//        return videoId;
//    }

    public int getScreenType() {
        return screenType;
    }

    public int getType() {
        return type;
    }

    public long getTimeEventStart() {
        return timeEventStart;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }

    @SerializedName("videoTitle")
    @Expose
    private String videoTitle = "";
    @SerializedName("videoImage")
    @Expose
    private String imagePath = "";

    @SerializedName("totalViews")
    @Expose
    private long totalViews = 0; //totalView cua video

    @SerializedName("lastIdStr")
    private String lastId = "";

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public long getTotalViews() {
        return totalViews;
    }

    public void setNumberPeopleWait(long numberPeopleWait) {
        this.numberPeopleWait = numberPeopleWait;
    }

    public long getNumberPeopleWait() {
        return numberPeopleWait;
    }

    public ArrayList<LivestreamVoteModel> getListVote() {
        if (listVote == null) {
            listVote = new ArrayList<>();
        }
        return listVote;
    }

    public boolean isEnableDonate() {
        return enableDonate;
    }

    public void setEnableDonate(boolean enableDonate) {
        this.enableDonate = enableDonate;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }
}
