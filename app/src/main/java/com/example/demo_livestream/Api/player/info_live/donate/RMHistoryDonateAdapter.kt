package com.viettel.mocha.rmlivestream.player.info_live.donate

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmItemHistoryDonateBinding
import com.viettel.mocha.rmlivestream.RmUtils
import com.viettel.mocha.rmlivestream.channel.listner.RmOnClickChannel
import com.viettel.mocha.rmlivestream.model.RmHistoryDonate
import com.viettel.mocha.util.Log
import com.viettel.mocha.util.Utilities

class RMHistoryDonateAdapter(
    private val context: Context,
) : RecyclerView.Adapter<RMHistoryDonateAdapter.RmHistoryDonateHolder>() {
    private var list: ArrayList<RmHistoryDonate> = ArrayList()

    fun setList(list: ArrayList<RmHistoryDonate>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RmHistoryDonateHolder {
        val binding = RmItemHistoryDonateBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return RmHistoryDonateHolder(context, binding)
    }

    override fun onBindViewHolder(holder: RmHistoryDonateHolder, position: Int) {
        val item = list[position]
        holder.bindData(item)

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class RmHistoryDonateHolder(
        val context: Context,
        val binding: RmItemHistoryDonateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindData(rmHistoryDonate: RmHistoryDonate) {

            val numberDonate = Utilities.shortenLongNumber(rmHistoryDonate.amountStar.toLong())
            if (rmHistoryDonate.type != 1) {
                binding.ivAvatar.visibility = View.INVISIBLE
                binding.lineBuyStar.visibility = View.VISIBLE
                binding.tvTitle.text = context.getString(R.string.rm_buy_star)
                binding.tvDesc.visibility = View.GONE
                binding.tvNumDonate.text = "+" + numberDonate
            } else {
                binding.tvTitle.text = rmHistoryDonate.giftName
                binding.tvNumDonate.text = "-" + numberDonate
                binding.tvDesc.visibility = View.VISIBLE
                binding.tvDesc.text = context.getString(R.string.rm_go_to) + " " +
                        RmUtils.getInstance().truncateText(rmHistoryDonate.nameChannel, 30)
                binding.ivAvatar.visibility = View.VISIBLE
                binding.lineBuyStar.visibility = View.GONE
                Glide.with(context)
                    .load(rmHistoryDonate.giftImage)
                    .placeholder(R.drawable.bg_place_holder)
                    .into(binding.ivAvatar)

            }

            if (rmHistoryDonate.createdAt != "") {
                binding.tvTime.visibility = View.VISIBLE
                binding.tvTime.text = rmHistoryDonate.createdAt.toString()
            } else {
                binding.tvTime.visibility = View.GONE
            }

        }


    }
}

