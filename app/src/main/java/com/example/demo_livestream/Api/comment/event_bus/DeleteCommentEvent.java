package com.example.demo_livestream.Api.comment.event_bus;

import com.viettel.mocha.rmlivestream.model.RmCommentModel;

public class DeleteCommentEvent {
    RmCommentModel rmCommentModel;

    public RmCommentModel getRmCommentModel() {
        return rmCommentModel;
    }

    public void setRmCommentModel(RmCommentModel rmCommentModel) {
        this.rmCommentModel = rmCommentModel;
    }

    public DeleteCommentEvent(RmCommentModel rmCommentModel) {
        this.rmCommentModel=rmCommentModel;
    }
}
