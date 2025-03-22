package com.viettel.mocha.rmlivestream.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentHomeLivestreamBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.RMLivestreamActivity
import com.viettel.mocha.rmlivestream.channel.event_bus.GetNotyEvent
import com.viettel.mocha.rmlivestream.channel.listner.RmCallApiGetNotyListener
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.model.RMHomeLivestream
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class RMHomeLivestreamFragment : Fragment(), RMHomeLiveListener {
    companion object {
        fun newInstance(): RMHomeLivestreamFragment {
            val args = Bundle()
            val fragment = RMHomeLivestreamFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: RmFragmentHomeLivestreamBinding
    private lateinit var viewModel: RMHomeLivestreamViewModel
    private var data: ArrayList<RMHomeLivestream> = ArrayList()
    private lateinit var adapter: RMHomeLivestreamAdapter
    private var isLoading=0 // loading =0 , moi lan goi 1 api+ 1, neu =3 thi ms cho load tiep
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RmFragmentHomeLivestreamBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[RMHomeLivestreamViewModel::class.java]
        adapter = RMHomeLivestreamAdapter(requireActivity(), this)
        data.add(RMHomeLivestream())
        data.add(RMHomeLivestream())
        data.add(RMHomeLivestream())
        adapter.items = data
        BaseAdapter.setupVerticalRecycler(activity, binding.recyclerView, null, adapter, true)
        binding.loadingView.showLoading()
        initEvent()
        return binding.root
    }

    private fun initEvent() {
        viewModel.responseLive.observe(requireActivity(), Observer {
            if (it.size > 0) {
                val liveModel = RMHomeLivestream()
                liveModel.type = RMHomeLivestream.TYPE_LIVESTREAM
                liveModel.title = getString(R.string.rm_video_livestream)
                liveModel.listVideo = it
//                if (data.size > 0) {
//                    data.add(1, liveModel)
//                } else {
//                    data.add(liveModel)
//                }
                data[1]=liveModel
                adapter.notifyDataSetChanged()

                if (liveModel.listVideo[0].isEnableDonate) {
                    binding.ivStar.visibility = View.VISIBLE
                } else {
                    binding.ivStar.visibility = View.GONE
                }
                isLoading++
                if(isLoading==3){
                    binding.loadingView.showLoadedSuccess()
                }
            }
        })
        viewModel.responseBanner.observe(requireActivity(), Observer {
            if (it.size > 0) {
                val bannerModel = RMHomeLivestream()
                bannerModel.type = RMHomeLivestream.TYPE_BANNER
                bannerModel.listBanner = it
                data[0]=bannerModel
//                data.add(0, bannerModel)
                adapter.notifyDataSetChanged()
                isLoading++
                if(isLoading==3){
                    binding.loadingView.showLoadedSuccess()
                }
            }
        })
        viewModel.responseChannel.observe(requireActivity(), Observer {
            if (it.size > 0) {
                val channelModel = RMHomeLivestream()
                channelModel.type = RMHomeLivestream.TYPE_CHANNEL
                channelModel.title = getString(R.string.rm_channel)
                channelModel.listChannel = it
                data[2]=channelModel
//                data.add(channelModel)
                adapter.notifyDataSetChanged()
                isLoading++
                if(isLoading==3){
                    binding.loadingView.showLoadedSuccess()
                }
            }
        })

        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }
        binding.ivSearch.setOnClickListener {
            if (activity is RMLivestreamActivity) {
                (activity as RMLivestreamActivity).showSearchLive()
            }
        }


        binding.swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            refreshData()
        })


        binding.ivStar.setOnClickListener {
            if (activity is RMLivestreamActivity) {
                (activity as RMLivestreamActivity).showCurrentStar()
            }
        }

    }


    private fun refreshData() {
        isLoading=0
        data.clear()
        data.add(RMHomeLivestream())
        data.add(RMHomeLivestream())
        data.add(RMHomeLivestream())
        viewModel.getListLive()
        viewModel.getListBanner()
        viewModel.getListChannel()
    }

    override fun onClickVideo(model: RMLivestream) {
        if (activity is RMLivestreamActivity) {
            (activity as RMLivestreamActivity).showLiveDetail(model.id, false)
        }
    }

    override fun onClickChannel(model: RMChannel) {
        if (activity is RMLivestreamActivity) {
            (activity as RMLivestreamActivity).showDetailChannel(model.id,false)
        }
    }

    override fun onClickVideoAll() {
        if (activity is RMLivestreamActivity) {
            (activity as RMLivestreamActivity).showAllLiveStream()
        }
    }

    override fun onClickChannelAll() {
        if (activity is RMLivestreamActivity) {
            (activity as RMLivestreamActivity).showAllChannel()
        }
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
    fun getNoty(event: GetNotyEvent) {
        viewModel.getListLive()
        EventBus.getDefault().removeStickyEvent(event)
    }

    override fun onClickGetNoty(
        rmLivestream: RMLivestream,
        rmCallApiGetNotyListener: RmCallApiGetNotyListener
    ) {
        if (rmLivestream.isNotified) {
            registerNotyLivestream(rmLivestream, rmCallApiGetNotyListener, 1)
        } else {
            registerNotyLivestream(rmLivestream, rmCallApiGetNotyListener, 0);
        }
    }


    private fun registerNotyLivestream(
        rmLivestream: RMLivestream,
        rmCallApiGetNotyListener: RmCallApiGetNotyListener,
        type: Int
    ) {
        LivestreamApi.getInstance().registerNotifyLivestream(
            rmLivestream.id,
            rmLivestream.timeEventStart,
            type,
            object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    rmCallApiGetNotyListener.onSusses(type == 0)
                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    rmCallApiGetNotyListener.onFall()
                }

            }
        )
    }

}