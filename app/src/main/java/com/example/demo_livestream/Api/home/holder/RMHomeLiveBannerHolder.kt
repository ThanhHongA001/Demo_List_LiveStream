package com.viettel.mocha.rmlivestream.home.holder

import android.annotation.SuppressLint
import android.app.Activity
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.databinding.RmHolderHomeBannerBinding
import com.viettel.mocha.rmlivestream.home.RMHomeLiveBannerAdapter
import com.viettel.mocha.rmlivestream.home.RMHomeLiveListener
import com.viettel.mocha.rmlivestream.model.RMHomeLivestream
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.module.keeng.widget.CustomLinearLayoutManager
import com.viettel.mocha.ui.view.indicator.SimpleSnapHelper
import com.viettel.mocha.util.Utilities

@SuppressLint("ClickableViewAccessibility")
class RMHomeLiveBannerHolder(var binding: RmHolderHomeBannerBinding, var activity: Activity, var listener: RMHomeLiveListener): BaseAdapter.ViewHolder(binding.root) {
    private lateinit var adapter: RMHomeLiveBannerAdapter
    private var data = ArrayList<RMLivestream>()
    private var next = 0
    private val isSlideBanner = false
    private var layoutManager: LinearLayoutManager? = null
    private val runnableAutoScroll: Runnable = object : Runnable {
        override fun run() {
            if(layoutManager == null) return
            if (Utilities.notEmpty(data) && data.size > 1) {
                try {
                    val current: Int = layoutManager!!.findFirstVisibleItemPosition()
                    if (next == 0) {
                        next = 1
                    } else if (current == 0 && next == -1) {
                        next = 1
                    } else if (current == data.size - 1 && next == 1) {
                        next = -1
                    }
                    val positionNext = current + next
                    layoutManager!!.smoothScrollToPosition(binding.recyclerView, null, positionNext)
                    binding.indicatorTop.onPageSelected(positionNext)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            binding.recyclerView.postDelayed(
                this,
                5000
            )
        }
    }
    init {
        layoutManager = CustomLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter = RMHomeLiveBannerAdapter(activity, listener)
        adapter.items = data
        BaseAdapter.setupHorizontalRecycler(activity, binding.recyclerView, layoutManager, adapter, true)
        binding.indicatorTop.attachToRecyclerView(binding.recyclerView)
        SimpleSnapHelper(binding.indicatorTop).attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.postDelayed(
            runnableAutoScroll,
            5000
        )
        binding.recyclerView.setOnTouchListener(View.OnTouchListener { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                binding.recyclerView.removeCallbacks(runnableAutoScroll)
            } else if (event.action == MotionEvent.ACTION_UP) {
                binding.recyclerView.removeCallbacks(runnableAutoScroll)
                binding.recyclerView.postDelayed(
                    runnableAutoScroll,
                    5000
                )
            }
            false
        })
    }
    override fun bindData(item: Any?, position: Int) {
        if(item is RMHomeLivestream){
            data.clear()
            data.addAll(item.listBanner)
            adapter.notifyDataSetChanged()
        }
    }
}