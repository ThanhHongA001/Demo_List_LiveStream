package com.example.demo_livestream.Api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RmCommentModel implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("commentId")
    @Expose
    private String commentId;
    @SerializedName("msisdn")
    @Expose
    private String msisdn;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contentId")
    @Expose
    private String contentId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("commentAt")
    @Expose
    private long commentAt;
    @SerializedName("serverTime")
    @Expose
    private long serverTime;
    @SerializedName("count")
    @Expose
    private long count;
    @SerializedName("level")
    @Expose
    private int level;
    @SerializedName("like")
    @Expose
    private long like;
    @SerializedName("dislike")
    @Expose
    private long dislike;
    @SerializedName("commentReplyId")
    @Expose
    private String commentReplyId;
    @SerializedName("actions")
    @Expose
    private List<Actions> actions;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    private boolean statusLike;
    private boolean statusDislike;
    private int oldStatusLike;

    public boolean isStatusLike() {
        if (actions == null || actions.isEmpty()) return false;
        else {
            return actions.get(0).getStatus() == 1;
        }
    }

    public void setStatusLike(boolean statusLike) {
        this.statusLike = statusLike;
    }

    public String getCommentReplyId() {
        return commentReplyId;
    }

    public void setCommentReplyId(String commentReplyId) {
        this.commentReplyId = commentReplyId;
    }

    public void setStatusAction(int type, String msisdn) {
        if (actions == null || actions.isEmpty()) {
            actions = new ArrayList<>();
            Actions action = new Actions();
            action.setStatus(type);
            action.setMsisdn(msisdn);
            actions.add(action);
            oldStatusLike = 0;
        } else {
            oldStatusLike = (int) actions.get(0).getStatus();
            actions.get(0).setStatus(type);
        }
    }

    public boolean isStatusDislike() {
        if (actions == null || actions.isEmpty()) return false;
        else {
            return actions.get(0).getStatus() == 2;
        }
    }

    public boolean isPhoneLike(String number) {
        if (actions == null || actions.isEmpty()) return false;
        if (actions.get(0).getMsisdn().equals(number)) return true;
        return false;
    }

    public void setStatusDislike(boolean statusDislike) {
        this.statusDislike = statusDislike;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCommentAt(long commentAt) {
        this.commentAt = commentAt;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getLike() {
        return like;
    }

    public void setAddLike() {
        like++;
    }

    public void setMuteLike() {
        like--;
    }

    public long getDislike() {
        return dislike;
    }

    public void setAddDislike() {
        dislike++;
    }

    public void setMuteDislike() {
        dislike--;
    }

    public List<Actions> getActions() {
        return actions;
    }

    public void setActions(List<Actions> actions) {
        this.actions = actions;
    }

    public String getId() {
        return id;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getName() {
        return name;
    }

    public String getContentId() {
        return contentId;
    }

    public String getContent() {
        return content;
    }

    public long getCommentAt() {
        return commentAt;
    }

    public long getServerTime() {
        return serverTime;
    }

    public int getOldStatusLike() {
        return oldStatusLike;
    }

    @Override
    public String toString() {
        return "RmCommentModel{" +
                "actions=" + actions +
                '}';
    }
}
