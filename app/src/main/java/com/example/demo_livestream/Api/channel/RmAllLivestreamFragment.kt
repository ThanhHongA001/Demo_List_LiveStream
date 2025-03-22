package com.viettel.mocha.rmlivestream.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentAllLivestreamBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.RMLivestreamActivity
import com.viettel.mocha.rmlivestream.channel.event_bus.GetNotyEvent
import com.viettel.mocha.rmlivestream.channel.adapter.RmLivestreamAdapter
import com.viettel.mocha.rmlivestream.channel.listner.RmCallApiGetNotyListener
import com.viettel.mocha.rmlivestream.channel.listner.RmOnClickLivestream
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.RmVideoInChannelResponse
import com.viettel.mocha.ui.view.load_more.OnEndlessScrollListener
import org.greenrobot.eventbus.EventBus

class RmAllLivestreamFragment : Fragment() {

    private lateinit var binding: RmFragmentAllLivestreamBinding
    private lateinit var adapter: RmLivestreamAdapter
    private lateinit var listLivestream: ArrayList<RMLivestream>
    var isLoading: Boolean = false
    var isLeftData: Boolean = true

    companion object {
        fun newInstance(): RmAllLivestreamFragment {
            val args = Bundle()
            val fragment = RmAllLivestreamFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmFragmentAllLivestreamBinding.inflate(layoutInflater)
        binding.rmLayoutHeader.lineBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.loadingViewExtra.showLoading()
        initView()

        binding.swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(), R.color.v5_main_color
            )
        )
        binding.swipeRefreshLayout.setOnRefreshListener {
            initView()
        }

        binding.loadingViewExtra.setOnClickRetryListener { view ->
            binding.loadingViewExtra.showLoading()
            initView()
        }
        return binding.root
    }


    private fun initView() {
        binding.rmLayoutHeader.tvTitle.text =
            requireContext().resources.getString(R.string.rm_all_livestream)
        adapter = RmLivestreamAdapter(requireContext(), object : RmOnClickLivestream {
            override fun onClickItemVideo(rmLivestream: RMLivestream) {
                if (activity is RMLivestreamActivity) {
                    (activity as RMLivestreamActivity).showLiveDetail(rmLivestream.id, false)
                }
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


        })
        binding.recAllLivestream.adapter = adapter
        getListLivestream()
    }

    private fun getListLivestream() {
        LivestreamApi.getInstance()
            .getListLiveStream(1, 0, 10, object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    try {
                        val gson = Gson()
                        val response: RmVideoInChannelResponse =
                            gson.fromJson(data, RmVideoInChannelResponse::class.java)

                        if (response.data != null && response.data.size != 0) {
                            listLivestream = response.data as ArrayList<RMLivestream>
                            adapter.setList(listLivestream)

                            binding.recAllLivestream.addOnScrollListener(object :
                                OnEndlessScrollListener(3) {
                                override fun onLoadNextPage(view: View?) {
                                    super.onLoadNextPage(view)
                                    if (isLeftData && !isLoading && listLivestream.size >= 10) {
                                        loadMoreLivestream(listLivestream.size / 10)
                                        isLoading = true
                                    }
                                }
                            })
                            binding.loadingViewExtra.showLoadedSuccess()
                        } else binding.loadingViewExtra.showLoadedError()
                        binding.swipeRefreshLayout.isRefreshing = false
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    binding.loadingViewExtra.showLoadedError()
                    binding.swipeRefreshLayout.isRefreshing = false
                }

            })
    }

    private fun loadMoreLivestream(page: Int) {
        LivestreamApi.getInstance()
            .getListLiveStream(1, page, 10, object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    try {
                        val gson = Gson()
                        val response: RmVideoInChannelResponse =
                            gson.fromJson(data, RmVideoInChannelResponse::class.java)

                        listLivestream.addAll(response.data)
                        adapter.notifyDataSetChanged()
                        isLoading = false
                        if (response.data.size < 10) {
                            isLeftData = false
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                }

            })
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
                    EventBus.getDefault().postSticky(GetNotyEvent())
                    rmCallApiGetNotyListener.onSusses(type == 0)
                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                }

            }
        )
    }


}