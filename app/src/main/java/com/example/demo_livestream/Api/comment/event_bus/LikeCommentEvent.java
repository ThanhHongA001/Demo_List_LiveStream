package com.example.demo_livestream.Api.comment.event_bus;

public class LikeCommentEvent {
    String commentId;
    int level;
    int status;
    int old_status;

    public LikeCommentEvent(String commentId, int level, int status, int old_status) {
        this.commentId = commentId;
        this.level = level;
        this.status = status;
        this.old_status = old_status;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOld_status() {
        return old_status;
    }

    public void setOld_status(int old_status) {
        this.old_status = old_status;
    }
}
