package com.example.demo_livestream.Api.comment.event_bus;

public class PostCommentEvent {
    String totalComment;
    public String getTotalComment() {
        return totalComment;
    }

    public PostCommentEvent(String totalComment) {
        this.totalComment = totalComment;
    }

    public void setTotalComment(String totalComment) {
        this.totalComment = totalComment;
    }
}