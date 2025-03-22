package com.viettel.mocha.rmlivestream.home.holder

import android.app.Activity
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.databinding.RmHolderHomeListBinding
import com.viettel.mocha.rmlivestream.RmUtils
import com.viettel.mocha.rmlivestream.home.RMHomeLiveListener
import com.viettel.mocha.rmlivestream.home.RMHomeLiveVideoAdapter
import com.viettel.mocha.rmlivestream.model.RMHomeLivestream
import com.viettel.mocha.rmlivestream.model.RMLivestream

class RMHomeLiveVideoHolder(var binding: RmHolderHomeListBinding, var activity: Activity, var listener: RMHomeLiveListener): BaseAdapter.ViewHolder(binding.root) {
    private lateinit var adapter: RMHomeLiveVideoAdapter
    private var data = ArrayList<RMLivestream>()
    init {

        adapter = RMHomeLiveVideoAdapter(activity, listener)
        adapter.items = data
        BaseAdapter.setupHorizontalRecycler(activity, binding.recyclerView, null, adapter, true)
    }
    override fun bindData(item: Any?, position: Int) {
        if(item is RMHomeLivestream){
            binding.tvTitle.text = RmUtils.getInstance().truncateText(item.title, 30)
            data.clear()
            data.addAll(item.listVideo)
            adapter.notifyDataSetChanged()
            binding.tvSeeAll.setOnClickListener { listener.onClickVideoAll() }
        }
    }
}