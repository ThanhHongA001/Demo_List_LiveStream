package com.viettel.mocha.rmlivestream.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.google.gson.Gson
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentChannelDetailBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.RmUtils
import com.viettel.mocha.rmlivestream.channel.adapter.RmChannelPagerAdapter
import com.viettel.mocha.rmlivestream.channel.event_bus.FollowInChannel
import com.viettel.mocha.rmlivestream.channel.event_bus.FollowInLivestream
import com.viettel.mocha.rmlivestream.channel.listner.RmCallApiFollowChannelListener
import com.viettel.mocha.rmlivestream.comment.event_bus.DeleteReplyEvent
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.RRestChannelInfoModel
import com.viettel.mocha.util.Utilities
import com.viettel.mocha.v5.dialog.DialogConfirm
import com.viettel.mocha.v5.home.base.BaseDialogFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RmChannelDetailFragment : Fragment() {

    private lateinit var binding: RmFragmentChannelDetailBinding
    private lateinit var pagerAdapter: RmChannelPagerAdapter
    private lateinit var rmChannel: RMChannel
    private lateinit var rmChannelId: String
    private var fromVTM = false

    companion object {
        fun newInstance(rmChannelId: String, fromVTM: Boolean): RmChannelDetailFragment {
            val args = Bundle()
            val fragment = RmChannelDetailFragment()
            fragment.arguments = args
            fragment.fromVTM = fromVTM
            fragment.setIdChannel(rmChannelId)
            return fragment
        }
    }

    fun setIdChannel(rmChannelId: String) {
        this.rmChannelId = rmChannelId
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmFragmentChannelDetailBinding.inflate(layoutInflater)

        binding.lineBack.setOnClickListener {
            if (fromVTM) {
                requireActivity().finish()
            } else {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        initView()
        getChannelInfo()
        return binding.root
    }


    private fun initView() {
        hideError()
        showLoading()
    }

    fun getChannelInfo() {
        LivestreamApi.getInstance().getChannelInfo(rmChannelId, object : HttpCallBack() {
            @Throws(Exception::class)
            override fun onSuccess(data: String?) {
                val gson = Gson()
                val response: RRestChannelInfoModel =
                    gson.fromJson(data, RRestChannelInfoModel::class.java)
                if (response.data != null) {
                    val channel: RMChannel = response.data
                    rmChannel = channel
                    getChannelInfoSuccess()
                    hideLoading()
                }
            }


            override fun onFailure(message: String?) {
                binding.rmConsChannelInfo.visibility = View.GONE
                hideLoading()
                showError()
                super.onFailure(message)
            }
        })

    }

    private fun getChannelInfoSuccess() {
        pagerAdapter = RmChannelPagerAdapter(this, rmChannel)
        binding.rmViewPager.setAdapter(pagerAdapter)
        TabLayoutMediator(
            binding.rmTabLayout, binding.rmViewPager,
            (TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                when (position) {
                    0 -> tab.setText(R.string.rm_video)
                    1 -> tab.setText(R.string.rm_info)
                }
            })
        ).attach()

        binding.rmTvChannelName.text = RmUtils.getInstance().truncateText(rmChannel.name, 30)
        context?.let {
            Glide.with(it)
                .load(rmChannel.imageCoverUrl)
                .placeholder(R.drawable.bg_coin)
                .into(binding.rmIvBanner)

        }

        val numberVideoStr: String = Utilities.shortenLongNumber(rmChannel.numVideo.toLong())
        if (rmChannel.numVideo > 1) {
            binding.tvTotalVideo.text =
                numberVideoStr + " " + requireContext().getString(R.string.rm_videos)
        } else {
            binding.tvTotalVideo.text =
                numberVideoStr + " " + requireContext().getString(R.string.rm_video)
        }

        binding.tvTotalVideo.setCompoundDrawablesRelativeWithIntrinsicBounds(
            requireContext().resources
                .getDrawable(R.drawable.rm_ic_v5_dos_video), null, null, null
        )

        context?.let {
            Glide.with(it)
                .load(rmChannel.imageUrl)
                .placeholder(R.drawable.bg_place_holder)
                .into(binding.ivAvatarChannel)
        }

        if (rmChannel.isFollow == 1) {
            setViewIsFollow(true, false)
        } else {
            setViewIsFollow(false, false)
        }

        actionFollow()
    }


    fun setViewFollower(rmChannel: RMChannel) {
        val totalFollower: String = Utilities.shortenLongNumber(rmChannel.numfollow)

        if (rmChannel.numfollow > 1) {
            binding.tvTotalFollower.text =
                totalFollower + " " + requireContext().getString(R.string.rm_follower)
        } else {
            binding.tvTotalFollower.text =
                totalFollower + " " + requireContext().getString(R.string.rm_followers)
        }
    }

    private fun actionFollow() {
        binding.rmLayoutBtnFollow.root.setOnClickListener {
            LivestreamApi.getInstance().followChannel(rmChannel.id, object : HttpCallBack() {
                @Throws(Exception::class)
                override fun onSuccess(data: String?) {
                    setViewIsFollow(true, true)
                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)

                }
            })
        }

        binding.rmLayoutBtnUnfollow.root.setOnClickListener {

            val dialogConfirm = DialogConfirm.newInstance(
                getString(R.string.rm_unfollow_channel) + " \"" + RmUtils.getInstance()
                    .truncateText(rmChannel.name, 30)+"\"",
                getString(R.string.rm_check_unfollow),
                DialogConfirm.CONFIRM_TYPE,
                R.string.rm_cancel,
                R.string.rm_unfollow
            )
            dialogConfirm.setSelectListener(
                object : BaseDialogFragment.DialogListener {
                    override fun dialogRightClick(value: Int) {
                        callApiUnfollow(rmChannel)
                        dialogConfirm.dismiss()
                    }

                    override fun dialogLeftClick() {
                        dialogConfirm.dismiss()
                    }
                })
            dialogConfirm.show(childFragmentManager, "")
        }
    }

    private fun callApiUnfollow(
        rmChannel: RMChannel,
    ) {
        LivestreamApi.getInstance().unFollowChannel(rmChannel.id, object : HttpCallBack() {
            @Throws(Exception::class)
            override fun onSuccess(data: String?) {
                setViewIsFollow(false, true)
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)

            }
        })
    }

    private fun setViewIsFollow(isFollow: Boolean, isChange: Boolean) {
        if (rmChannel.isOwner) {
            binding.rmLayoutBtnFollow.root.visibility = View.GONE
            binding.rmLayoutBtnUnfollow.root.visibility = View.GONE
        } else {
            if (isFollow) {
                rmChannel.setFollow(true)
                if (isChange) {
                    rmChannel.numfollow += 1
                    EventBus.getDefault().postSticky(FollowInChannel(isFollow))
                }
                setViewFollower(rmChannel)
                binding.rmLayoutBtnFollow.root.visibility = View.INVISIBLE
                binding.rmLayoutBtnUnfollow.root.visibility = View.VISIBLE
            } else {
                rmChannel.setFollow(false)
                if (isChange) {
                    rmChannel.numfollow -= 1
                    EventBus.getDefault().postSticky(FollowInChannel(isFollow))
                }
                setViewFollower(rmChannel)
                binding.rmLayoutBtnFollow.root.visibility = View.VISIBLE
                binding.rmLayoutBtnUnfollow.root.visibility = View.GONE
            }
        }

    }


    private fun showError() {
        binding.frameEmpty.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.frameEmpty.visibility = View.GONE
    }


    private fun showLoading() {
        binding.frameLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.frameLoading.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateFollow(event: FollowInLivestream) {
        setViewIsFollow(event.follow, true)
        EventBus.getDefault().removeStickyEvent(event)
    }
}