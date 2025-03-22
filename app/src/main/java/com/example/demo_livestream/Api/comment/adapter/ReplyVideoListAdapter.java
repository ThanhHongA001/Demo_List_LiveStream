package com.example.demo_livestream.Api.comment.adapter;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.viettel.mocha.app.ApplicationController;
import com.viettel.mocha.app.R;
import com.viettel.mocha.app.databinding.RmHolderOnmediaCommentStatusBinding;
import com.viettel.mocha.database.model.ReengAccount;
import com.viettel.mocha.listeners.OnSingleClickListener;
import com.viettel.mocha.module.keeng.utils.DateTimeUtils;
import com.viettel.mocha.rmlivestream.comment.listener.IClickLikeAndDisLike;
import com.viettel.mocha.rmlivestream.comment.listener.IClickMoreComment;
import com.viettel.mocha.rmlivestream.model.RmCommentModel;
import com.viettel.mocha.rmlivestream.model.RMLivestream;

import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public class ReplyVideoListAdapter extends RecyclerView.Adapter<ReplyVideoListAdapter.ReplyVideoListHolder> {
    private List<RmCommentModel> data;
    private ReengAccount account;
    private Activity activity;
    private ApplicationController mApp;
    private IClickMoreComment iClickMoreComment;
    private RMLivestream video;
    private IClickLikeAndDisLike mListener;
    private boolean checkLevel;
    boolean like, dislike;

    public void setData(List<RmCommentModel> data) {
        this.data = data;
        notifyDataSetChanged();

    }

    public void removeItem(String commentId) {
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getCommentId().equals(commentId)) {
                this.data.remove(i);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public ReplyVideoListAdapter(List<RmCommentModel> movieData, Activity activity, ApplicationController mApp, RMLivestream video, IClickMoreComment iClickMoreComment, IClickLikeAndDisLike mListener, boolean checkLevel) {
        this.data = movieData;
        this.mApp = mApp;
        this.activity = activity;
        this.iClickMoreComment = iClickMoreComment;
        this.video=video;
        this.checkLevel = checkLevel;
        this.mListener = mListener;
    }

    public static class ReplyVideoListHolder extends RecyclerView.ViewHolder {

        private Resources res;
        private final RmHolderOnmediaCommentStatusBinding binding;
        private String myName;
        private String myNumber;
        private ReengAccount account;

        private ApplicationController mApp;
        private IClickLikeAndDisLike mListener;
        private boolean like, dislike;
        private int checKClick = 0;


        public ReplyVideoListHolder(@NonNull RmHolderOnmediaCommentStatusBinding binding, Activity activity, ApplicationController mApp, IClickLikeAndDisLike mListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.mApp = mApp;
            this.mListener = mListener;
            res = activity.getResources();
            account = mApp.getReengAccountBusiness().getCurrentAccount();
            myName = account.getName();
            myNumber = mApp.getReengAccountBusiness().getPhoneNumberLogin();
        }

        private void onDislike() {
            binding.ivDislikeComment.setImageResource(R.drawable.rm_ic_dislike_new_blue);
            binding.ivlikeComment.setImageResource(R.drawable.rm_ic_like_movie_new);
        }

        private void onLike() {
            binding.ivDislikeComment.setImageResource(R.drawable.rm_ic_dislike_movie_new);
            binding.ivlikeComment.setImageResource(R.drawable.rm_ic_like_new_blue);
        }

        private void unLikeDislike() {
            binding.ivDislikeComment.setImageResource(R.drawable.rm_ic_dislike_movie_new);
            binding.ivlikeComment.setImageResource(R.drawable.rm_ic_like_movie_new);
        }

        public void bindData(RmCommentModel model, int position) {
            binding.tvwCommentName.setText(model.getName());
            binding.tvwCommentContent.setText(model.getContent());
            if (System.currentTimeMillis() - model.getCommentAt() < 6000) {
                binding.tvwCommentTime.setText(res.getString(R.string.rm_text_view_just_now));
            } else
                binding.tvwCommentTime.setText(DateTimeUtils.calculateTime(res, model.getCommentAt()));


            int sizeAvatar = (int) res.getDimension(R.dimen.avatar_small_size);
            binding.tvwCommentAvatar.setCompoundDrawables(null, null, null, null);
            if (model.getLike() <= 0)
                binding.tvNumberLikeComment.setText("");
            else binding.tvNumberLikeComment.setText(String.valueOf(model.getLike()));
            if (model.getDislike() <= 0)
                binding.tvNumberDisLikeComment.setText("");
            else binding.tvNumberDisLikeComment.setText(String.valueOf(model.getDislike()));

            if (model.isStatusLike() && model.isPhoneLike(myNumber)) {
                onLike();
                checKClick = 1;
                like = true;
            } else if (model.isStatusDislike() && model.isPhoneLike(myNumber)) {
                onDislike();
                checKClick = 2;
                dislike = true;
            } else {
                checKClick = 0;
                unLikeDislike();
                like = false;
                dislike = false;
            }

            binding.llLikeComment.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    if (model.isStatusLike()) {
                        model.setStatusAction(0, myNumber);
                        model.setMuteLike();
                    } else {
                        model.setStatusAction(1, myNumber);
                        model.setAddLike();
                        if (dislike) {
                            model.setMuteDislike();
                        }

                    }
                    //chuyen event ve fragment
                    if (mListener != null) {
                        mListener.onClickLikeReply(model, position);
                    }
                }
            });

            binding.llDislikeComment.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    if (model.isStatusDislike()) {
                        model.setStatusAction(0, myNumber);
                        model.setMuteDislike();
                    } else {
                        model.setStatusAction(2, myNumber);
                        model.setAddDislike();
                        if (like) {
                            model.setMuteLike();
                        }
                    }
                    if (mListener != null) {
                        mListener.onClickDisLikeReply(model, position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ReplyVideoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReplyVideoListHolder(RmHolderOnmediaCommentStatusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), activity, mApp, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyVideoListHolder holder, int position) {
        RmCommentModel item = data.get(position);
        holder.bindData(item, position);
        holder.binding.ivMore.setOnClickListener(view -> iClickMoreComment.onClickMoreComment(item, video));
        if (checkLevel) holder.binding.ivReplyComment.setVisibility(View.GONE);
        else holder.binding.ivReplyComment.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }


}