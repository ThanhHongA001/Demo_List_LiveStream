package com.viettel.mocha.rmlivestream.home

import android.app.Activity
import android.view.ViewGroup
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.databinding.RmHolderHomeBannerBinding
import com.viettel.mocha.app.databinding.RmHolderHomeListBinding
import com.viettel.mocha.rmlivestream.home.holder.RMHomeLiveBannerHolder
import com.viettel.mocha.rmlivestream.home.holder.RMHomeLiveChannelHolder
import com.viettel.mocha.rmlivestream.home.holder.RMHomeLiveVideoHolder
import com.viettel.mocha.rmlivestream.model.RMHomeLivestream

class RMHomeLivestreamAdapter(activity: Activity, var listener: RMHomeLiveListener) : BaseAdapter<BaseAdapter.ViewHolder, RMHomeLivestream>(activity) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            RMHomeLivestream.TYPE_BANNER->{
                RMHomeLiveBannerHolder(RmHolderHomeBannerBinding.inflate(layoutInflater, parent, false), activity, listener)
            }

            RMHomeLivestream.TYPE_LIVESTREAM->{
                RMHomeLiveVideoHolder(RmHolderHomeListBinding.inflate(layoutInflater, parent, false), activity, listener)
            }

            RMHomeLivestream.TYPE_CHANNEL->{
                RMHomeLiveChannelHolder(RmHolderHomeListBinding.inflate(layoutInflater, parent, false), activity, listener)
            }

            else ->{
                EmptyHolder(layoutInflater, parent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        var model = getItem(position)
        if(model != null && model.type == RMHomeLivestream.TYPE_BANNER && model.listBanner.size > 0) {
            return RMHomeLivestream.TYPE_BANNER
        } else if(model != null && model.type == RMHomeLivestream.TYPE_LIVESTREAM && model.listVideo.size > 0) {
            return RMHomeLivestream.TYPE_LIVESTREAM
        } else if(model != null && model.type == RMHomeLivestream.TYPE_CHANNEL && model.listChannel.size > 0) {
            return RMHomeLivestream.TYPE_CHANNEL
        }
        return TYPE_EMPTY
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position), position)
    }
}