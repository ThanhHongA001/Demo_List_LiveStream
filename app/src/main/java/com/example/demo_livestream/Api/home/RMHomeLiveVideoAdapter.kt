package com.viettel.mocha.rmlivestream.home

import android.app.Activity
import android.view.ViewGroup
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.databinding.RmHolderHomeLiveDetailBinding
import com.viettel.mocha.rmlivestream.home.holder.RMHomeLiveVideoDetailHolder
import com.viettel.mocha.rmlivestream.model.RMLivestream

class RMHomeLiveVideoAdapter(activity: Activity, var liveListener: RMHomeLiveListener) :
    BaseAdapter<BaseAdapter.ViewHolder, RMLivestream>(activity) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return RMHomeLiveVideoDetailHolder(
            RmHolderHomeLiveDetailBinding.inflate(
                layoutInflater,
                parent,
                false
            ), liveListener
        )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position), position)
    }
}