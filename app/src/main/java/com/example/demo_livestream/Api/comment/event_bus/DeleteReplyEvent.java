package com.example.demo_livestream.Api.comment.event_bus;

public class DeleteReplyEvent {
    String commentId;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public DeleteReplyEvent(String commentId) {
        this.commentId = commentId;
    }
}
