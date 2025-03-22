package com.viettel.mocha.rmlivestream.player.info_upcoming

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentInfoUpcomingBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.channel.event_bus.GetNotyEvent
import com.viettel.mocha.rmlivestream.channel.adapter.RmVideoInChannelAdapter
import com.viettel.mocha.rmlivestream.channel.listner.RmOnClickVideoInChannel
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.player.RMLivestreamDetailViewModel
import com.viettel.mocha.v5.utils.ToastUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

class RMInfoUpcomingFragment : Fragment(), RmOnClickVideoInChannel {
    companion object {
        fun newInstance(): RMInfoUpcomingFragment {
            val fragment = RMInfoUpcomingFragment()
            return fragment
        }
    }

    private lateinit var binding: RmFragmentInfoUpcomingBinding
    private lateinit var viewModel: RMLivestreamDetailViewModel
    private var currentVideo: RMLivestream? = null
    private lateinit var adapterVideo: RmVideoInChannelAdapter
    private var data: ArrayList<RMLivestream> = ArrayList()
    private var isPlay = false //check den gio live hay chua

    private val handler = Handler()
    private var runnable: Runnable? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmFragmentInfoUpcomingBinding.inflate(inflater, container, false)
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

        binding.llNotifyLive.root.setOnClickListener {
            if (currentVideo!!.isNotified) {
                callApiNotify(1)
            } else {
                callApiNotify(0)
            }
        }

        binding.llRemoveLive.root.setOnClickListener {
            if (currentVideo!!.isNotified) {
                callApiNotify(1)
            } else {
                callApiNotify(0)
            }
        }
    }

    private fun callApiNotify(type: Int) {
        LivestreamApi.getInstance().registerNotifyLivestream(
            currentVideo!!.id,
            currentVideo!!.timeEventStart,
            type,
            object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    val jsonObject = JSONObject(data)
                    val code = jsonObject.getInt("code")
                    if (code == 200) {
                        if (type == 0) {
                            currentVideo!!.isNotified = true
                            currentVideo!!.numberPeopleWait += 1
                            binding.tvNumberNotify.text =
                                context?.getString(
                                    R.string.rm_number_people_watting,
                                    currentVideo?.numberPeopleWait.toString()
                                )
                            setViewNotified()
                        } else {
                            currentVideo!!.isNotified = false
                            currentVideo!!.numberPeopleWait -= 1
                            binding.tvNumberNotify.text =
                                context?.getString(
                                    R.string.rm_number_people_watting,
                                    currentVideo?.numberPeopleWait.toString()
                                )
                            setViewNoty()
                        }
                        EventBus.getDefault().postSticky(GetNotyEvent())
                        ToastUtils.showToast(
                            context,
                            context!!.getString(R.string.sc_successful)
                        )
                    } else {
                        ToastUtils.showToast(
                            context,
                            context!!.getString(R.string.error_message_default)
                        )
                    }
                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    ToastUtils.showToast(
                        context,
                        context!!.getString(R.string.error_message_default)
                    )
                }

            }
        )
    }

    private fun initData() {
        if (currentVideo == null) return
        binding.tvTitle.text = currentVideo?.title
        binding.tvDesc.text = currentVideo?.description
        binding.tvNumberNotify.text =
            context?.getString(
                R.string.rm_number_people_watting,
                currentVideo?.numberPeopleWait.toString()
            )
        viewModel.getListVideoByChannel(currentVideo!!.channel.id)
        if (currentVideo!!.isNotified) {
            setViewNotified()
        } else {
            setViewNoty()
        }
        checkStartTimeEvent()
    }

    private fun checkStartTimeEvent() {
        if (currentVideo!!.timeEventStart <= System.currentTimeMillis()) {
            navigateToLiveStreamDetailActivity()
        } else {
            handler.postDelayed(Runnable { navigateToLiveStreamDetailActivity() }
                .also { runnable = it }, currentVideo!!.timeEventStart - System.currentTimeMillis())
        }
    }


    private fun navigateToLiveStreamDetailActivity() {
//        if(!isPlay){
//            viewModel.getLiveDetail(currentVideo!!.id)
//            isPlay=true
//        }
    }

    override fun onClickItemVideo(rmLivestream: RMLivestream) {
        viewModel.getLiveDetail(rmLivestream.id)
    }

    fun setViewNoty() {
        binding.llNotifyLive.root.visibility = View.VISIBLE
        binding.llRemoveLive.root.visibility = View.GONE
    }

    private fun setViewNotified() {
        binding.llNotifyLive.root.visibility = View.GONE
        binding.llRemoveLive.root.visibility = View.VISIBLE
    }
}