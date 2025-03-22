package com.viettel.mocha.rmlivestream.channel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmItemLivestreamBinding
import com.viettel.mocha.helper.TimeHelper
import com.viettel.mocha.module.keeng.utils.DateTimeUtils
import com.viettel.mocha.rmlivestream.RmUtils
import com.viettel.mocha.rmlivestream.channel.listner.RmCallApiGetNotyListener
import com.viettel.mocha.rmlivestream.channel.listner.RmOnClickLivestream
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.util.Utilities
import com.viettel.xgaming.utils.Log
import com.viettel.xgaming.utils.setMarginStart
import org.greenrobot.eventbus.EventBus

class RmLivestreamAdapter(
    private val context: Context,
    private val listener: RmOnClickLivestream
) : RecyclerView.Adapter<RmLivestreamAdapter.RmCallApiLivestreamHolder>() {
    private var list: ArrayList<RMLivestream> = ArrayList()

    fun setList(list: ArrayList<RMLivestream>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RmCallApiLivestreamHolder {
        val binding = RmItemLivestreamBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return RmCallApiLivestreamHolder(context, binding, listener)
    }

    override fun onBindViewHolder(holder: RmCallApiLivestreamHolder, position: Int) {
        val item = list[position]
        holder.bindData(item)

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class RmCallApiLivestreamHolder(
        val context: Context,
        val binding: RmItemLivestreamBinding,
        val listener: RmOnClickLivestream
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(rMLivestream: RMLivestream) {
            if (rMLivestream.status == 0) {
                binding.lineLiveFuture.visibility = View.VISIBLE
                binding.rmLineLive.visibility = View.GONE
                binding.tvDatetime.visibility = View.VISIBLE
                binding.tvTotalView.visibility = View.GONE
                if (!rMLivestream.isNotified) {
                    binding.lineGetNoty.visibility = View.VISIBLE
                    binding.lineRemoveNoty.visibility = View.GONE
                } else {
                    binding.lineGetNoty.visibility = View.GONE
                    binding.lineRemoveNoty.visibility = View.VISIBLE
                }

                binding.tvDatetime.text = context.getString(
                    R.string.rm_live_at,
                    TimeHelper.formatTimeEventMessage(rMLivestream.timeEventStart)
                )

                binding.tvDatetime.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null, null, null, null
                )

                binding.tvDatetime.setMarginStart(0)

            } else if (rMLivestream.status == 1) {
                binding.lineLiveFuture.visibility = View.GONE
                binding.rmLineLive.visibility = View.VISIBLE
                binding.tvTotalView.visibility = View.VISIBLE
                binding.lineGetNoty.visibility = View.GONE

                val totalViewStr = Utilities.shortenLongNumber(rMLivestream.totalView.toLong())
                if (rMLivestream.totalView <= 1) {
                    binding.tvTotalView.text =
                        totalViewStr + " " + context.getString(R.string.rm_view)
                } else {
                    binding.tvTotalView.text =
                        totalViewStr + " " + context.getString(R.string.rm_views)
                }

                if (rMLivestream.timeStart > 0) {
                    binding.tvDatetime.visibility = View.VISIBLE
                    binding.tvDatetime.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        context.resources.getDrawable(R.drawable.rm_ic_v5_dos_video),
                        null,
                        null,
                        null
                    )
                    binding.tvDatetime.text = DateTimeUtils.calculateTime(
                        context.resources,
                        rMLivestream.timeStart
                    )
                } else {
                    binding.tvDatetime.visibility = View.GONE
                }

            } else {
                binding.lineLiveFuture.visibility = View.GONE
                binding.rmLineLive.visibility = View.GONE
                binding.tvTotalView.visibility = View.VISIBLE
                binding.lineGetNoty.visibility = View.GONE

                val totalViewStr = Utilities.shortenLongNumber(rMLivestream.totalView.toLong())
                if (rMLivestream.totalView <= 1) {
                    binding.tvTotalView.text =
                        totalViewStr + " " + context.getString(R.string.rm_view)
                } else {
                    binding.tvTotalView.text =
                        totalViewStr + " " + context.getString(R.string.rm_views)
                }

                if (rMLivestream.timeStart > 0) {
                    binding.tvDatetime.visibility = View.VISIBLE
                    binding.tvDatetime.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        context.resources
                            .getDrawable(R.drawable.rm_ic_v5_dos_video), null, null, null
                    )
                    binding.tvDatetime.text = DateTimeUtils.calculateTime(
                        context.resources,
                        rMLivestream.timeStart
                    )
                } else {
                    binding.tvDatetime.visibility = View.GONE
                }
            }


            binding.tvNameChannel.text =
                RmUtils.getInstance().truncateText(rMLivestream.channel.name, 30)
            binding.tvDesc.text = RmUtils.getInstance().truncateText(rMLivestream.description, 60)

            Glide.with(binding.ivBanner)
                .load(rMLivestream.linkAvatar)
                .centerCrop()
                .placeholder(R.drawable.bg_place_holder)
                .into(binding.ivBanner)

            Glide.with(binding.ivAvatarChannel)
                .load(rMLivestream.channel.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.bg_place_holder)
                .into(binding.ivAvatarChannel)


            binding.root.setOnClickListener {
                listener.onClickItemVideo(rMLivestream)
            }

            binding.lineGetNoty.setOnClickListener {
                listener.onClickGetNoty(rMLivestream, object : RmCallApiGetNotyListener {
                    override fun onSusses(isGetNoty: Boolean) {
                        if (isGetNoty) {
                            rMLivestream.isNotified = true
                            binding.lineGetNoty.visibility = View.GONE
                            binding.lineRemoveNoty.visibility = View.VISIBLE
                        } else {
                            rMLivestream.isNotified = false
                            binding.lineGetNoty.visibility = View.VISIBLE
                            binding.lineRemoveNoty.visibility = View.INVISIBLE
                        }
                    }

                    override fun onFall() {

                    }

                })
            }


            binding.lineRemoveNoty.setOnClickListener {
                listener.onClickGetNoty(rMLivestream, object : RmCallApiGetNotyListener {
                    override fun onSusses(isGetNoty: Boolean) {
                        if (isGetNoty) {
                            rMLivestream.isNotified = true
                            binding.lineGetNoty.visibility = View.GONE
                            binding.lineRemoveNoty.visibility = View.VISIBLE
                        } else {
                            rMLivestream.isNotified = false
                            binding.lineGetNoty.visibility = View.VISIBLE
                            binding.lineRemoveNoty.visibility = View.INVISIBLE
                        }
                    }

                    override fun onFall() {

                    }
                })
            }

        }

    }
}

