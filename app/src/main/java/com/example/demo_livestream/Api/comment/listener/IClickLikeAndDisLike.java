package com.example.demo_livestream.Api.comment.listener;


import com.viettel.mocha.rmlivestream.model.RmCommentModel;

public interface IClickLikeAndDisLike {
    void onClickLike(RmCommentModel model, int position);

    void onClickDisLike(RmCommentModel model, int position);

    void onClickLikeReply(RmCommentModel model, int position);

    void onClickDisLikeReply(RmCommentModel model, int position);
}
