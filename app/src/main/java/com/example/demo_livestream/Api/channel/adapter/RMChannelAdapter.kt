package com.viettel.mocha.rmlivestream.channel.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.demo_livestream.Api_Support.RMChannel
import com.example.demo_livestream.R
import com.viettel.mocha.app.databinding.RmItemChannelBinding
import com.viettel.mocha.rmlivestream.RmUtils
import com.viettel.mocha.rmlivestream.channel.listner.RmCallApiFollowChannelListener
import com.viettel.mocha.rmlivestream.channel.listner.RmOnClickChannel
import com.example.demo_livestream.Api_Support.Utilities

class RMChannelAdapter // Constructor
    (private val context: Context, private val listener: RmOnClickChannel) :
    RecyclerView.Adapter<RMChannelAdapter.RmCallApiChannelHolder>() {
    private var list: ArrayList<RMChannel> = ArrayList<RMChannel>()

    // Set danh sách dữ liệu
    fun setList(list: ArrayList<RMChannel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RmCallApiChannelHolder {
        val binding: RmItemChannelBinding = RmItemChannelBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return RmCallApiChannelHolder(
            context, binding,
            listener
        )
    }

    override fun onBindViewHolder(holder: RmCallApiChannelHolder, position: Int) {
        val item: RMChannel = list[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // ViewHolder Class
    class RmCallApiChannelHolder(
        private val context: Context, binding: RmItemChannelBinding,
        private val listener: RmOnClickChannel
    ) :
        RecyclerView.ViewHolder(binding.getRoot()), RmCallApiFollowChannelListener {
        private val binding: RmItemChannelBinding = binding

        fun bindData(rmChannel: RMChannel) {
            Glide.with(context)
                .load(rmChannel.getImageUrl())
                .placeholder(R.drawable.bg_place_holder)
                .into<Target<Drawable>>(binding.rmIvAvatarChannel)

            val numberVideoStr: String = Utilities.shortenLongNumber(rmChannel.getNumVideo())

            if (rmChannel.getNumVideo() > 1) {
                binding.tvTotalVideo.setText(numberVideoStr + " " + context.getString(R.string.rm_videos))
            } else {
                binding.tvTotalVideo.setText(numberVideoStr + " " + context.getString(R.string.rm_video))
            }

            binding.tvTotalVideo.setCompoundDrawablesRelativeWithIntrinsicBounds(
                ContextCompat.getDrawable(context, R.drawable.rm_ic_v5_dos_video),
                null, null, null
            )

            binding.tvNameChannel.setText(
                RmUtils.getInstance().truncateText(rmChannel.getName(), 16)
            )

            if (rmChannel.getIsFollow() === 1) {
                setViewIsFollow(true, rmChannel, false)
            } else {
                setViewIsFollow(false, rmChannel, false)
            }

            if (rmChannel.isStatusLive()) {
                binding.layoutAvatar.setStroke(
                    ContextCompat.getColor(binding.getRoot().getContext(), R.color.rm_color_FF70), 1
                )
                binding.rmLineLive.setVisibility(View.VISIBLE)
                binding.layoutAvatar.setVisibility(View.VISIBLE)
            } else {
                binding.layoutAvatar.setStroke(
                    ContextCompat.getColor(binding.getRoot().getContext(), R.color.color_8B8B), 1
                )
                binding.rmLineLive.setVisibility(View.GONE)
                binding.layoutAvatar.setVisibility(View.INVISIBLE)
            }

            binding.getRoot().setOnClickListener { view -> listener.onItemChannelClick(rmChannel) }

            binding.rmLayoutBtnFollow.getRoot().setOnClickListener { view ->
                listener.onClickFollowChannel(
                    rmChannel,
                    true,
                    this
                )
            }

            binding.rmLayoutBtnUnfollow.getRoot().setOnClickListener { view ->
                listener.onClickFollowChannel(
                    rmChannel,
                    false,
                    this
                )
            }
        }

        private fun setViewIsFollow(isFollow: Boolean, rmChannel: RMChannel, isChange: Boolean) {
            if (rmChannel.isOwner()) {
                binding.rmLayoutBtnFollow.getRoot().setVisibility(View.GONE)
                binding.rmLayoutBtnUnfollow.getRoot().setVisibility(View.GONE)
            } else {
                if (isFollow) {
                    rmChannel.setFollow(true)
                    if (isChange) {
                        rmChannel.setNumfollow(rmChannel.getNumfollow() + 1)
                    }
                    binding.rmLayoutBtnFollow.getRoot().setVisibility(View.GONE)
                    binding.rmLayoutBtnUnfollow.getRoot().setVisibility(View.VISIBLE)
                } else {
                    rmChannel.setFollow(false)
                    if (isChange) {
                        rmChannel.setNumfollow(rmChannel.getNumfollow() - 1)
                    }
                    binding.rmLayoutBtnFollow.getRoot().setVisibility(View.VISIBLE)
                    binding.rmLayoutBtnUnfollow.getRoot().setVisibility(View.GONE)
                }
            }
            setViewFollower(rmChannel)
        }

        override fun onSusses(isFollow: Boolean, rmChannel: RMChannel) {
            setViewIsFollow(isFollow, rmChannel, true)
        }

        override fun onFall() {
            // Xử lý khi follow/unfollow thất bại (nếu cần)
        }

        fun setViewFollower(rmChannel: RMChannel) {
            val totalFollower: String = Utilities.shortenLongNumber(rmChannel.getNumfollow())

            if (rmChannel.getNumfollow() > 1) {
                binding.tvTotalFollower.setText(totalFollower + " " + context.getString(R.string.rm_followers))
            } else {
                binding.tvTotalFollower.setText(totalFollower + " " + context.getString(R.string.rm_follower))
            }
        }
    }
}