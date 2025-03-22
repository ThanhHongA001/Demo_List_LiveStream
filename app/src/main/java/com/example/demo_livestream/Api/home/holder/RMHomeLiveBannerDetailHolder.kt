package com.viettel.mocha.rmlivestream.home.holder

import com.bumptech.glide.Glide
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.databinding.RmHolderHomeBannerDetailBinding
import com.viettel.mocha.rmlivestream.home.RMHomeLiveListener
import com.viettel.mocha.rmlivestream.model.RMLivestream

class RMHomeLiveBannerDetailHolder(var binding: RmHolderHomeBannerDetailBinding, var listener: RMHomeLiveListener) : BaseAdapter.ViewHolder(binding.root) {
    override fun bindData(item: Any?, position: Int) {
        if(item is RMLivestream) {
            Glide.with(binding.getRoot())
                .load(item.linkAvatar)
                .centerCrop()
                .into(binding.ivCover)
            binding.ivCover.setOnClickListener {
                listener.onClickVideo(item)
            }
        }
    }
}