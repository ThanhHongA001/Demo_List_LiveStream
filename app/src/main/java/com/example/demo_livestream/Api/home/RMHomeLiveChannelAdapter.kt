package com.viettel.mocha.rmlivestream.home

import android.app.Activity
import android.view.ViewGroup
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.databinding.RmHolderHomeChannelDetailBinding
import com.viettel.mocha.rmlivestream.home.holder.RMHomeLiveChannelDetailHolder
import com.viettel.mocha.rmlivestream.model.RMChannel

class RMHomeLiveChannelAdapter(activity: Activity, var listener: RMHomeLiveListener) : BaseAdapter<BaseAdapter.ViewHolder, RMChannel>(activity) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return RMHomeLiveChannelDetailHolder(RmHolderHomeChannelDetailBinding.inflate(layoutInflater, parent, false), listener)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position), position)
    }
}