package com.viettel.mocha.rmlivestream.player.info_vod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.ApplicationController
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentInfoVodBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.RMLivestreamActivity
import com.viettel.mocha.rmlivestream.RmUtils
import com.viettel.mocha.rmlivestream.channel.adapter.RmVideoInChannelAdapter
import com.viettel.mocha.rmlivestream.channel.event_bus.FollowInChannel
import com.viettel.mocha.rmlivestream.channel.event_bus.FollowInLivestream
import com.viettel.mocha.rmlivestream.channel.listner.RmOnClickVideoInChannel
import com.viettel.mocha.rmlivestream.comment.event_bus.UpdateTotalCommentEvent
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.RMReactionResponse
import com.viettel.mocha.rmlivestream.player.RMLivestreamDetailViewModel
import com.viettel.mocha.util.Utilities
import com.viettel.mocha.v5.dialog.DialogConfirm
import com.viettel.mocha.v5.home.base.BaseDialogFragment
import com.viettel.mocha.v5.utils.ToastUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RMInfoVODFragment : Fragment(), RmOnClickVideoInChannel {
    companion object {
        fun newInstance(): RMInfoVODFragment {
            val fragment = RMInfoVODFragment()
            return fragment
        }
    }

    private lateinit var binding: RmFragmentInfoVodBinding
    private lateinit var viewModel: RMLivestreamDetailViewModel
    private var currentVideo: RMLivestream? = null
    private lateinit var adapterVideo: RmVideoInChannelAdapter
    private var data: ArrayList<RMLivestream> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmFragmentInfoVodBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[RMLivestreamDetailViewModel::class.java]
        adapterVideo = RmVideoInChannelAdapter(requireContext(), this)
        BaseAdapter.setupVerticalRecycler(
            requireActivity(),
            binding.recyclerView,
            null,
            adapterVideo,
            false
        )
        initEvent()
        return binding.root
    }

    private fun initEvent() {
        viewModel.responseLive.observe(requireActivity()) {
            if (it != null) {
                currentVideo = it
                initData()
            }
        }
        viewModel.responseVideoByChannel.observe(requireActivity()) {
            it.remove(currentVideo!!)
            adapterVideo.setList(it)
        }

        binding.layoutChannel.setOnClickListener {
            if (activity is RMLivestreamActivity && currentVideo!!.channel.id != null) {
                (activity as RMLivestreamActivity).showDetailChannel(
                    currentVideo!!.channel.id,
                    false
                )
            }
        }

        binding.layoutFollow.root.setOnClickListener {
            LivestreamApi.getInstance().followChannel(currentVideo!!.channel.id, object :
                HttpCallBack() {
                override fun onSuccess(data: String?) {
                    EventBus.getDefault().postSticky(FollowInLivestream(true))
                    setViewIsFollow(true, false)
                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    ToastUtils.showToast(
                        context,
                        context!!.getString(R.string.error_message_default)
                    )
                }

            })
        }
        binding.layoutUnfollow.root.setOnClickListener {
            val dialogConfirm = DialogConfirm.newInstance(
                getString(R.string.rm_unfollow_channel) + " \"" + RmUtils.getInstance()
                    .truncateText(currentVideo!!.channel.name, 30)+"\"",
                getString(R.string.rm_check_unfollow),
                DialogConfirm.CONFIRM_TYPE,
                R.string.rm_cancel,
                R.string.rm_unfollow
            )
            dialogConfirm.setSelectListener(
                object : BaseDialogFragment.DialogListener {
                    override fun dialogRightClick(value: Int) {
                        callApiUnfollow()
                        dialogConfirm.dismiss()
                    }

                    override fun dialogLeftClick() {
                        dialogConfirm.dismiss()
                    }
                })
            dialogConfirm.show(childFragmentManager, "")
        }
        binding.llComment.setOnClickListener {
            if (activity is RMLivestreamActivity) {
                (activity as RMLivestreamActivity).showComment(currentVideo!!)
            }
        }

        binding.llLike.setOnClickListener {
            if (!currentVideo!!.isLike) {
                likeVideo(currentVideo!!.id, "LIKE")
            } else {
                likeVideo(currentVideo!!.id, "UNLIKE")
            }
        }


    }

    private fun callApiUnfollow() {
        LivestreamApi.getInstance()
            .unFollowChannel(currentVideo!!.channel.id, object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    EventBus.getDefault().postSticky(FollowInLivestream(false))
                    setViewIsFollow(false, false)
                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    ToastUtils.showToast(
                        context,
                        context!!.getString(R.string.error_message_default)
                    )
                }

            })
    }

    private fun initData() {
        if (currentVideo == null) return
        binding.tvTitle.text = RmUtils.getInstance().truncateText(currentVideo!!.title, 30)
//        binding.tvDesc.text = currentVideo!!.description
        RmUtils.getInstance().setupExpandableTextView(
            requireContext(),
            binding.tvDesc,
            currentVideo!!.description,
            50,
            getString(R.string.rm_see_more),
            getString(R.string.rm_see_less),
            ContextCompat.getColor(requireContext(), R.color.rm_color_FF70),
            R.font.quicksand_semi_bold
        )
        binding.tvName.text = currentVideo!!.channel.name
        binding.tvNumberLike.text =
            getString(R.string.rm_number_like, currentVideo!!.totalLike.toString())
        binding.tvNumberComment.text =
            getString(
                R.string.rm_number_comment,
                Utilities.shortenLongNumber(currentVideo!!.totalComment.toLong())
            )

        if (currentVideo!!.isLike) {
            binding.ivLike.setImageResource(R.drawable.rm_ic_like_orange)
        } else {
            binding.ivLike.setImageResource(R.drawable.rm_ic_like)
        }
        Glide.with(binding.root.getContext())
            .load(currentVideo!!.channel.imageUrl).centerCrop()
            .placeholder(R.drawable.ic_avatar_default)
            .into(binding.ivAvatar)
        viewModel.getListVideoByChannel(currentVideo!!.channel.id)
        setViewIsFollow(currentVideo!!.channel.isFollow == 1, currentVideo?.isOwner == true)
    }

    fun likeVideo(videoId: String, action: String) {
        LivestreamApi.getInstance().likeVideo(videoId, action, object : HttpCallBack() {
            override fun onSuccess(data: String?) {
                val result: RMReactionResponse =
                    ApplicationController.self().gson.fromJson(data, RMReactionResponse::class.java)
                if (result.code == 200) {
                    if (action == "LIKE") {
                        currentVideo!!.isLike = true
                        binding.ivLike.setImageResource(R.drawable.rm_ic_like_orange)
                        currentVideo!!.totalLike += 1
                        binding.tvNumberLike.text =
                            getString(R.string.rm_number_like, currentVideo!!.totalLike.toString())
                    } else {
                        currentVideo!!.isLike = false
                        binding.ivLike.setImageResource(R.drawable.rm_ic_like)
                        currentVideo!!.totalLike -= 1
                        binding.tvNumberLike.text =
                            getString(R.string.rm_number_like, currentVideo!!.totalLike.toString())
                    }
                } else {

                }
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
            }
        })
    }

    private fun setViewIsFollow(isFollow: Boolean, isOwner: Boolean) {
        if (isOwner) {
            binding.layoutUnfollow.root.visibility = View.GONE
            binding.layoutFollow.root.visibility = View.GONE
        } else {
            if (isFollow) {
                currentVideo!!.channel.isFollow = 1
                binding.layoutUnfollow.root.visibility = View.VISIBLE
                binding.layoutFollow.root.visibility = View.GONE
            } else {
                currentVideo!!.channel.isFollow = 0
                binding.layoutUnfollow.root.visibility = View.GONE
                binding.layoutFollow.root.visibility = View.VISIBLE
            }
        }
    }

    override fun onClickItemVideo(rmLivestream: RMLivestream) {
        viewModel.getLiveDetail(rmLivestream.id)
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateTotalCmt(event: UpdateTotalCommentEvent) {
        currentVideo!!.totalComment = event.totalCmt.toInt();
        binding.tvNumberComment.text =
            getString(R.string.rm_number_comment, currentVideo!!.totalComment.toString())
        EventBus.getDefault().removeStickyEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateFollow(event: FollowInChannel) {
        setViewIsFollow(event.follow, false )
        EventBus.getDefault().removeStickyEvent(event)
    }

}