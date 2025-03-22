package com.viettel.mocha.rmlivestream.home.holder

import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmHolderHomeChannelDetailBinding
import com.viettel.mocha.rmlivestream.home.RMHomeLiveListener
import com.viettel.mocha.rmlivestream.model.RMChannel

class RMHomeLiveChannelDetailHolder (var binding: RmHolderHomeChannelDetailBinding, var listener: RMHomeLiveListener) : BaseAdapter.ViewHolder(binding.root) {
    override fun bindData(item: Any?, position: Int) {
        if(item is RMChannel){
            binding.tvName.text = item.name
            Glide.with(binding.getRoot())
                .load(item.imageUrl)
                .centerCrop()
                .into(binding.ivAvatar)
            if(item.statusLive) {
                binding.layoutAvatar.setStroke(ContextCompat.getColor(binding.root.context, R.color.rm_color_FF70), 1)
                binding.tvStateLive.visibility = View.VISIBLE
            } else {
                binding.layoutAvatar.setStroke(ContextCompat.getColor(binding.root.context, R.color.color_8B8B), 1)
                binding.tvStateLive.visibility = View.GONE
            }
            binding.root.setOnClickListener {
                listener.onClickChannel(item)
            }
        }
    }
}