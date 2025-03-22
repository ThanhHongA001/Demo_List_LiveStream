package com.viettel.mocha.rmlivestream.home.holder

import android.app.Activity
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.databinding.RmHolderHomeListBinding
import com.viettel.mocha.rmlivestream.home.RMHomeLiveChannelAdapter
import com.viettel.mocha.rmlivestream.home.RMHomeLiveListener
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.model.RMHomeLivestream

class RMHomeLiveChannelHolder(var binding: RmHolderHomeListBinding, var activity: Activity, var listener: RMHomeLiveListener): BaseAdapter.ViewHolder(binding.root) {
    private lateinit var adapter: RMHomeLiveChannelAdapter
    private var data = ArrayList<RMChannel>()
    init {

        adapter = RMHomeLiveChannelAdapter(activity, listener)
        adapter.items = data
        BaseAdapter.setupHorizontalRecycler(activity, binding.recyclerView, null, adapter, true)
    }
    override fun bindData(item: Any?, position: Int) {
        if(item is RMHomeLivestream){
            binding.tvTitle.text = item.title
            data.clear()
            data.addAll(item.listChannel)
            adapter.notifyDataSetChanged()
            binding.tvSeeAll.setOnClickListener { listener.onClickChannelAll() }
        }
    }
}