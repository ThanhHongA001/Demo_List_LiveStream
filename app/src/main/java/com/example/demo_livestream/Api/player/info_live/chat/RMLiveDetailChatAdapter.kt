package com.viettel.mocha.rmlivestream.player.info_live.chat

import android.app.Activity
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.ApplicationController
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmHolderLiveChatBinding
import com.viettel.mocha.rmlivestream.RMConstants
import com.viettel.mocha.rmlivestream.model.LivestreamChatModel
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.xgaming.utils.Log
import java.lang.String
import kotlin.Any
import kotlin.Int
import kotlin.math.log

class RMLiveDetailChatAdapter(activity: Activity, var listener: RMLiveDetailChatListener) :
    BaseAdapter<BaseAdapter.ViewHolder, LivestreamChatModel>(activity) {

    private var rmLivestream: RMLivestream? = null

    fun setVideoLiveStream(rmLivestream: RMLivestream) {
        this.rmLivestream = rmLivestream
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return RMLiveDetailChatHolder(
            RmHolderLiveChatBinding.inflate(
                layoutInflater,
                parent,
                false
            ), listener, rmLivestream
        )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position), position)
    }

    class RMLiveDetailChatHolder(
        var binding: RmHolderLiveChatBinding,
        var listener: RMLiveDetailChatListener,
        var rmLivestream: RMLivestream? = null
    ) : BaseAdapter.ViewHolder(binding.root) {
        override fun bindData(item: Any?, position: Int) {
            if (item is LivestreamChatModel) {

                when (item.type) {
                    RMConstants.WebSocket.typeMessage -> {
                        if (rmLivestream != null) {

                            if (rmLivestream?.isOwner == true) {
                                binding.ivMore.visibility = View.VISIBLE
                            } else {
                                val msisdn =
                                    ApplicationController.self().getReengAccountBusiness()
                                        .phoneNumberLogin;
                                if (item.chatMessage != null && item.chatMessage.userId != null
                                    && item.getChatMessage()?.getUserId()
                                        .equals(msisdn)
                                ) {
                                    binding.ivMore.visibility = View.VISIBLE
                                } else {
                                    binding.ivMore.visibility = View.GONE
                                }
                            }
                        }

                        if (item.chatMessage == null || item.getChatMessage()
                                .getNumberReaction() < 1 || item.getChatMessage()
                                .getReactions() == null
                        ) {
                            binding.layoutListReact.setVisibility(View.GONE)
                            binding.ivReactionNon.setVisibility(View.GONE)
                        } else {
                            binding.ivReactionNon.setVisibility(View.VISIBLE)
                            binding.layoutListReact.setVisibility(View.VISIBLE)
                            binding.tvReactionNumber.setText(
                                String.valueOf(
                                    item.getChatMessage().getNumberReaction()
                                )
                            )
                            val reactionCommentAdapter = ReactionCommentAdapter()
                            reactionCommentAdapter.setList(
                                item.getChatMessage().getReactions().getListReaction()
                            )
                            binding.rcvListReaction.setAdapter(reactionCommentAdapter)
                            binding.rcvListReaction.setLayoutManager(
                                LinearLayoutManager(
                                    binding.root.context,
                                    RecyclerView.HORIZONTAL,
                                    false
                                )
                            )
                        }
                        binding.tvName.text = item.chatMessage.userName
                        binding.tvComment.text = item.chatMessage.msgBody
                        Glide.with(binding.root.getContext())
                            .load(item.chatMessage.avatar).centerCrop()
                            .placeholder(R.drawable.ic_avatar_default)
                            .into(binding.ivAvatar)
                        binding.layoutDonate.visibility = View.GONE
                        binding.ivReactionNon.setOnClickListener {
                            binding.popupReaction.setVisibility(View.VISIBLE)
                            binding.popupReaction.animate().scaleX(1f).setDuration(250)
                            binding.popupReaction.animate().scaleY(1f).setDuration(250)
                        }
                        binding.btnCancelReact.setOnClickListener { view ->
                            var chatId = ""
                            if (!TextUtils.isEmpty(item.getChatMessage().getSmsgId())) {
                                chatId = item.getChatMessage().getSmsgId()
                            } else if (!TextUtils.isEmpty(
                                    item.getChatMessage().getsIdMessage()
                                )
                            ) {
                                chatId = item.getChatMessage().getsIdMessage()
                            }
                            if (item.getReactionType() !== 0) {
                                listener.onClickReactionComment(
                                    item.getReactionType(),
                                    chatId,
                                    "UNLIKE",
                                    position
                                )
                            }
                            hideView(binding.popupReaction)
                        }
                        binding.btnLike.setOnClickListener { view ->
                            var chatId = ""
                            if (!TextUtils.isEmpty(item.getChatMessage().getSmsgId())) {
                                chatId = item.getChatMessage().getSmsgId()
                            } else if (!TextUtils.isEmpty(
                                    item.getChatMessage().getsIdMessage()
                                )
                            ) {
                                chatId = item.getChatMessage().getsIdMessage()
                            }
                            hideView(binding.popupReaction)
                            listener.onClickReactionComment(1, chatId, "LIKE", position)
                        }
                        binding.btnHeart.setOnClickListener { view ->
                            var chatId = ""
                            if (!TextUtils.isEmpty(item.getChatMessage().getSmsgId())) {
                                chatId = item.getChatMessage().getSmsgId()
                            } else if (!TextUtils.isEmpty(
                                    item.getChatMessage().getsIdMessage()
                                )
                            ) {
                                chatId = item.getChatMessage().getsIdMessage()
                            }
                            hideView(binding.popupReaction)
                            listener.onClickReactionComment(2, chatId, "LIKE", position)
                        }
                        binding.btnHaha.setOnClickListener { view ->
                            var chatId = ""
                            if (!TextUtils.isEmpty(item.getChatMessage().getSmsgId())) {
                                chatId = item.getChatMessage().getSmsgId()
                            } else if (!TextUtils.isEmpty(
                                    item.getChatMessage().getsIdMessage()
                                )
                            ) {
                                chatId = item.getChatMessage().getsIdMessage()
                            }
                            hideView(binding.popupReaction)
                            listener.onClickReactionComment(3, chatId, "LIKE", position)
                        }
                        binding.btnWow.setOnClickListener { view ->
                            var chatId = ""
                            if (!TextUtils.isEmpty(item.getChatMessage().getSmsgId())) {
                                chatId = item.getChatMessage().getSmsgId()
                            } else if (!TextUtils.isEmpty(
                                    item.getChatMessage().getsIdMessage()
                                )
                            ) {
                                chatId = item.getChatMessage().getsIdMessage()
                            }
                            hideView(binding.popupReaction)
                            listener.onClickReactionComment(4, chatId, "LIKE", position)
                        }
                        binding.btnSad.setOnClickListener { view ->
                            var chatId = ""
                            if (!TextUtils.isEmpty(item.getChatMessage().getSmsgId())) {
                                chatId = item.getChatMessage().getSmsgId()
                            } else if (!TextUtils.isEmpty(
                                    item.getChatMessage().getsIdMessage()
                                )
                            ) {
                                chatId = item.getChatMessage().getsIdMessage()
                            }
                            hideView(binding.popupReaction)
                            listener.onClickReactionComment(5, chatId, "LIKE", position)
                        }
                        binding.btnAngry.setOnClickListener { view ->
                            var chatId = ""
                            if (!TextUtils.isEmpty(item.getChatMessage().getSmsgId())) {
                                chatId = item.getChatMessage().getSmsgId()
                            } else if (!TextUtils.isEmpty(
                                    item.getChatMessage().getsIdMessage()
                                )
                            ) {
                                chatId = item.getChatMessage().getsIdMessage()
                            }
                            hideView(binding.popupReaction)
                            listener.onClickReactionComment(6, chatId, "LIKE", position)
                        }
                        binding.layoutComment.setOnLongClickListener(View.OnLongClickListener {
                            binding.popupReaction.setVisibility(View.VISIBLE)
                            binding.popupReaction.animate().scaleX(1f).setDuration(250)
                            binding.popupReaction.animate().scaleY(1f).setDuration(250)
                            true
                        })
                        binding.ivMore.setOnClickListener {
                            listener.onClickMore(item)
                        }
                    }

                    RMConstants.WebSocket.typeGift -> {
                        binding.layoutListReact.visibility = View.GONE
                        binding.ivReactionNon.visibility = View.GONE
                        binding.ivMore.visibility = View.GONE
                        Glide.with(binding.root.getContext())
                            .load(item.getStreamGift().getAvatar()).centerCrop()
                            .placeholder(R.drawable.ic_avatar_default)
                            .into(binding.ivAvatar)
                        if (!TextUtils.isEmpty(item.streamGift.message)) {
                            binding.tvComment.setText(item.streamGift.message)
                            binding.tvComment.visibility = View.VISIBLE
                        } else {
                            binding.tvComment.visibility = View.GONE
                        }
                        binding.tvName
                            .setText(item.getStreamGift().getUserName())
                        binding.layoutDonate.visibility = View.VISIBLE
                        Glide.with(binding.root.getContext())
                            .load(item.getStreamGift().giftImg).centerCrop()
                            .placeholder(R.drawable.ic_avatar_default)
                            .into(binding.ivDonate)
                        binding.tvDonate.setText("+ ${item.streamGift.amountStar} stars")
                    }
                }


            }
        }

        private fun hideView(view: View) {
            view.animate().scaleX(0f).setDuration(250)
            view.animate().scaleY(0f).setDuration(250)
            view.postDelayed({ view.visibility = View.GONE }, 250)
        }
    }
}
