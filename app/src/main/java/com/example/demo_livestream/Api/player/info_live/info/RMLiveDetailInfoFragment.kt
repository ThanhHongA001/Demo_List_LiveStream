package com.viettel.mocha.rmlivestream.player.info_live.info

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.ApplicationController
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentLiveDetailInfoBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.RMLivestreamActivity
import com.viettel.mocha.rmlivestream.RmUtils
import com.viettel.mocha.rmlivestream.channel.event_bus.FollowInChannel
import com.viettel.mocha.rmlivestream.channel.event_bus.FollowInLivestream
import com.viettel.mocha.rmlivestream.model.LivestreamReloadSurveyNotification
import com.viettel.mocha.rmlivestream.model.LivestreamVoteModel
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.model.TopDonate
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.ListVoteLivestreamResponse
import com.viettel.mocha.rmlivestream.player.RMLivestreamDetailViewModel
import com.viettel.mocha.rmlivestream.player.info_live.donate.ReloadTopDonateEvent
import com.viettel.mocha.rmlivestream.player.info_live.donate.SendStarDialog
import com.viettel.mocha.rmlivestream.player.info_live.vote.VoteLiveDialog
import com.viettel.mocha.util.Log
import com.viettel.mocha.v5.dialog.DialogConfirm
import com.viettel.mocha.v5.home.base.BaseDialogFragment
import com.viettel.mocha.v5.utils.ToastUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RMLiveDetailInfoFragment : Fragment() {
    companion object {
        fun newInstance(): RMLiveDetailInfoFragment {
            val fragment = RMLiveDetailInfoFragment()
            return fragment
        }
    }

    private lateinit var binding: RmFragmentLiveDetailInfoBinding
    private lateinit var viewModel: RMLivestreamDetailViewModel
    private var currentVideo: RMLivestream? = null
    private var listTop = ArrayList<TopDonate>()
    private var topDonateDialog: TopDonateDialog? = null
    private var currentVote: LivestreamVoteModel? = null
    private var isVoted = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmFragmentLiveDetailInfoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[RMLivestreamDetailViewModel::class.java]
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
        viewModel.responseTopDonate.observe(requireActivity()) {
            if (it.size > 0) {
                binding.tvLeaderbroad.visibility = View.VISIBLE
                binding.layoutTopDonate.visibility = View.VISIBLE
                listTop = it
                val adapter = TopWeeklyDonateAdapter(requireActivity())
                val listTop = it.take(3)
                adapter.setList(ArrayList<TopDonate>(listTop))
                BaseAdapter.setupVerticalRecycler(
                    requireActivity(),
                    binding.recyclerView,
                    null,
                    adapter,
                    false
                )
            } else {
                binding.tvLeaderbroad.visibility = View.GONE
                binding.layoutTopDonate.visibility = View.GONE
            }
        }
        binding.tvSendStar.setOnClickListener {
            val sendStarDialog =
                SendStarDialog(requireActivity() as RMLivestreamActivity, currentVideo!!)
            sendStarDialog.show()
        }
        binding.tvSeeMore.setOnClickListener {
            topDonateDialog = TopDonateDialog(
                requireActivity() as RMLivestreamActivity,
                currentVideo!!.channel,
                currentVideo
            )
            topDonateDialog!!.behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            topDonateDialog!!.show()
        }

        binding.layoutVote.setOnClickListener {
            if (currentVideo != null && currentVote != null) {
                val vote = VoteLiveDialog(currentVideo!!, currentVote!!, isVoted)
                vote.show(requireActivity().supportFragmentManager, " Vote")
            }
        }
        binding.layoutFollow.root.setOnClickListener {
            LivestreamApi.getInstance().followChannel(currentVideo!!.channel.id, object :
                HttpCallBack() {
                override fun onSuccess(data: String?) {
                    EventBus.getDefault().postSticky(FollowInLivestream(true))
                    setViewIsFollow(true)
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

        binding.layoutChannel.setOnClickListener {
            if (activity is RMLivestreamActivity && currentVideo!!.channel.id != null) {
                (activity as RMLivestreamActivity).showDetailChannel(
                    currentVideo!!.channel.id,
                    false
                )
            }
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
    }

    private fun callApiUnfollow() {
        LivestreamApi.getInstance()
            .unFollowChannel(currentVideo!!.channel.id, object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    EventBus.getDefault().postSticky(FollowInLivestream(false))
                    setViewIsFollow(false)
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


    private fun setViewIsFollow(isFollow: Boolean) {
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

    private fun initData() {
        if (currentVideo == null) return
        binding.tvTitle.text = RmUtils.getInstance().truncateText(currentVideo!!.title, 30)
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
        viewModel.getListTopDonate(currentVideo!!.channel.id, currentVideo!!.id)
        if (currentVideo!!.listVote.size > 0) {
            currentVote = currentVideo!!.listVote.get(0)
            binding.layoutVote.visibility = View.VISIBLE
            for (item in currentVote!!.voteDtos) {
                if (item.isSelect()) {
                    isVoted = true
                    break
                }
            }
        } else {
            binding.layoutVote.visibility = View.GONE
        }
        Glide.with(requireActivity())
            .load(currentVideo!!.linkAvatar).centerCrop()
            .placeholder(R.drawable.ic_avatar_default)
            .into(binding.ivAvatar)
        if (currentVideo!!.isOwner) {
            binding.layoutFollow.root.visibility = View.GONE
            binding.layoutUnfollow.root.visibility = View.GONE
        } else {
            setViewIsFollow(currentVideo!!.channel.isFollow == 1)
        }
    }


    private fun getListVote() {
        LivestreamApi.getInstance().getListVoteLivestream(currentVideo!!.id, object :
            HttpCallBack() {
            override fun onSuccess(data: String?) {
                var response = ApplicationController.self().gson.fromJson(
                    data,
                    ListVoteLivestreamResponse::class.java
                )
                if (response.data.size > 0) {
                    currentVote = response.data.get(0)
                    binding.layoutVote.visibility = View.VISIBLE
                    for (item in currentVote!!.voteDtos) {
                        if (item.isSelect()) {
                            isVoted = true
                            break
                        }
                    }
                } else {
                    binding.layoutVote.visibility = View.GONE
                }
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
                binding.layoutVote.visibility = View.GONE
            }

        })
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onReceiveReloadSurvey(event: LivestreamReloadSurveyNotification) {
        getListVote()
        EventBus.getDefault().removeStickyEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onReloadTopDOnate(event: ReloadTopDonateEvent) {
        viewModel.getListTopDonate(currentVideo!!.channel.id, currentVideo!!.id)
        EventBus.getDefault().removeStickyEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateFollow(event: FollowInChannel) {
        setViewIsFollow(event.follow)
        EventBus.getDefault().removeStickyEvent(event)
    }
}