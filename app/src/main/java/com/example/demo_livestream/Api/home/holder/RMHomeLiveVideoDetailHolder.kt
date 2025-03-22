package com.viettel.mocha.rmlivestream.home.holder

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.ApplicationController
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmHolderHomeLiveDetailBinding
import com.viettel.mocha.rmlivestream.RmUtils
import com.viettel.mocha.rmlivestream.channel.listner.RmCallApiGetNotyListener
import com.viettel.mocha.rmlivestream.home.RMHomeLiveListener
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.util.Utilities

class RMHomeLiveVideoDetailHolder(
    var binding: RmHolderHomeLiveDetailBinding,
    var listener: RMHomeLiveListener
) : BaseAdapter.ViewHolder(binding.root) {
    init {
        val width: Int = getWidthVideoLive()
        val layoutParams = binding.root.layoutParams
        layoutParams.width = width

        //            layoutParams.height = (int) (width/1.1);
        binding.root.layoutParams = layoutParams
        binding.root.requestLayout()
    }

    fun getWidthVideoLive(): Int {
        return ((ApplicationController.self().widthPixels) / 1.25).toInt()
    }

    @SuppressLint("ResourceAsColor")
    override fun bindData(item: Any?, position: Int) {
        if (item is RMLivestream) {
            if (item.getStatus() == RMLivestream.TYPE_LIVE) {
                binding.layoutStateLive.visibility = View.VISIBLE
                binding.lineGetNoty.root.visibility = View.GONE
                binding.lineRemoveNoty.root.visibility = View.GONE
            } else if (item.status == RMLivestream.TYPE_UPCOMING) {
                binding.layoutStateLive.visibility = View.VISIBLE
                binding.ivStateLive.setImageResource(R.drawable.rm_ic_notification)
                binding.tvStateLive.text = binding.root.context.getString(R.string.rm_coming_soon)
                binding.layoutStateLive.setBackgroundColorRound(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.color_DBA0_80
                    )
                )

                if (item.isNotified) {
                    setViewNotified()
                } else {
                    setViewNoty()
                }

            } else {
                binding.layoutStateLive.visibility = View.GONE
                binding.lineGetNoty.root.visibility = View.GONE
                binding.lineRemoveNoty.root.visibility = View.GONE
            }
            Glide.with(binding.getRoot())
                .load(item.linkAvatar)
                .centerCrop()
                .into(binding.ivCover)
            binding.tvTitle.text = RmUtils.getInstance().truncateText(item.title, 30)
            binding.tvViewer.text =
                binding.root.context.getString(R.string.rm_number_viewer, item.totalView.toString())
            binding.root.setOnClickListener { listener.onClickVideo(item) }

            binding.lineGetNoty.root.setOnClickListener {
                listener.onClickGetNoty(item, object : RmCallApiGetNotyListener {
                    override fun onSusses(isGetNoty: Boolean) {
                        if (isGetNoty) {
                            item.isNotified = true
                            setViewNotified()

                        } else {
                            item.isNotified = false
                            setViewNoty()

                        }
                    }

                    override fun onFall() {

                    }

                })
            }

            binding.lineRemoveNoty.root.setOnClickListener {
                listener.onClickGetNoty(item, object : RmCallApiGetNotyListener {
                    override fun onSusses(isGetNoty: Boolean) {
                        if (isGetNoty) {
                            item.isNotified = true
                            setViewNotified()

                        } else {
                            item.isNotified = false
                            setViewNoty()

                        }
                    }

                    override fun onFall() {
                    }
                })
            }
        }
    }

    fun setViewNoty() {
        binding.lineGetNoty.root.visibility = View.VISIBLE
        binding.lineRemoveNoty.root.visibility = View.GONE
    }

    private fun setViewNotified() {
        binding.lineGetNoty.root.visibility = View.GONE
        binding.lineRemoveNoty.root.visibility = View.VISIBLE
    }
}
