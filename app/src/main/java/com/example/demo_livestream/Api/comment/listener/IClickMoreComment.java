package com.example.demo_livestream.Api.comment.listener;

import com.viettel.mocha.rmlivestream.model.RMLivestream;
import com.viettel.mocha.rmlivestream.model.RmCommentModel;

public interface IClickMoreComment {
    void onClickMoreComment(RmCommentModel rmCommentModel, RMLivestream video);
    void onClickMoreReply(RmCommentModel rmCommentModel, RMLivestream video);
}
