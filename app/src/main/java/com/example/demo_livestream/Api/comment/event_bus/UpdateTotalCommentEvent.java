package com.example.demo_livestream.Api.comment.event_bus;

import com.viettel.mocha.rmlivestream.model.RmCommentModel;

public class UpdateTotalCommentEvent {
    long totalCmt;

    public long getTotalCmt() {
        return totalCmt;
    }

    public void setTotalCmt(long totalCmt) {
        this.totalCmt = totalCmt;
    }

    public UpdateTotalCommentEvent(long totalCmt) {
        this.totalCmt=totalCmt;
    }
}
