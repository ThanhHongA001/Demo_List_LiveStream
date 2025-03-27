package com.example.demo_livestream.Api.Model;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class LiveStream_List_Model {

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("modifiedDate")
    private String modifiedDate;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("videoId")
    private String videoId;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private int type;

    @SerializedName("status")
    private int status;

    @SerializedName("privacy")
    private int privacy;

    @SerializedName("hlsPlayLink")
    private String hlsPlayLink;

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
    private String timeStart;

    @SerializedName("timeEventStart")
    private String timeEventStart;

    @SerializedName("time_finish")
    private String timeFinish;

    @SerializedName("ageLimit")
    private int ageLimit;

    @SerializedName("enableChat")
    private boolean enableChat;

    @SerializedName("chatSetting")
    private int chatSetting;

    @SerializedName("channel")
    private Channel channel;

    @SerializedName("adaptiveLinks")
    private Map<String, String> adaptiveLinks;

    @SerializedName("follow")
    private boolean follow;

    @SerializedName("like")
    private boolean like;

    @SerializedName("blockComment")
    private boolean blockComment;

    @SerializedName("notify")
    private boolean notify;

    @SerializedName("numberPeopleWait")
    private int numberPeopleWait;

    @SerializedName("surveyDto")
    private Object surveyDto;

    @SerializedName("enableDonate")
    private boolean enableDonate;

    @SerializedName("owner")
    private boolean owner;

    // Lớp con Channel chứa thông tin kênh
    public static class Channel {
        @SerializedName("id")
        private int id;

        @SerializedName("msisdn")
        private String msisdn;

        @SerializedName("channelName")
        private String channelName;

        @SerializedName("channelAvatar")
        private String channelAvatar;

        @SerializedName("numFollows")
        private int numFollows;

        @SerializedName("isFollow")
        private int isFollow;

        // Getter và Setter cho Channel
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMsisdn() {
            return msisdn;
        }

        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getChannelAvatar() {
            return channelAvatar;
        }

        public void setChannelAvatar(String channelAvatar) {
            this.channelAvatar = channelAvatar;
        }

        public int getNumFollows() {
            return numFollows;
        }

        public void setNumFollows(int numFollows) {
            this.numFollows = numFollows;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }
    }

    // Getter và Setter cho LiveStream_List_Model
    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    public String getHlsPlayLink() {
        return hlsPlayLink;
    }

    public void setHlsPlayLink(String hlsPlayLink) {
        this.hlsPlayLink = hlsPlayLink;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public int getTotalView() {
        return totalView;
    }

    public void setTotalView(int totalView) {
        this.totalView = totalView;
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

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public int getTotalShare() {
        return totalShare;
    }

    public void setTotalShare(int totalShare) {
        this.totalShare = totalShare;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEventStart() {
        return timeEventStart;
    }

    public void setTimeEventStart(String timeEventStart) {
        this.timeEventStart = timeEventStart;
    }

    public String getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(String timeFinish) {
        this.timeFinish = timeFinish;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public boolean isEnableChat() {
        return enableChat;
    }

    public void setEnableChat(boolean enableChat) {
        this.enableChat = enableChat;
    }

    public int getChatSetting() {
        return chatSetting;
    }

    public void setChatSetting(int chatSetting) {
        this.chatSetting = chatSetting;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Map<String, String> getAdaptiveLinks() {
        return adaptiveLinks;
    }

    public void setAdaptiveLinks(Map<String, String> adaptiveLinks) {
        this.adaptiveLinks = adaptiveLinks;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isEnableDonate() {
        return enableDonate;
    }

    public void setEnableDonate(boolean enableDonate) {
        this.enableDonate = enableDonate;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }


}
