package com.example.demo_livestream.Api.comment.event_bus;

import com.viettel.mocha.rmlivestream.model.RmCommentModel;

public class ShowLayoutReplyEvent {

    RmCommentModel model;
    int checkClick;

    public ShowLayoutReplyEvent(RmCommentModel model, int checkClick) {
        this.model = model;
        this.checkClick = checkClick;
    }

    public int getCheckClick() {
        return checkClick;
    }

    public void setCheckClick(int checkClick) {
        this.checkClick = checkClick;
    }

    public RmCommentModel getModel() {
        return model;
    }

    public void setModel(RmCommentModel model) {
        this.model = model;
    }

}
