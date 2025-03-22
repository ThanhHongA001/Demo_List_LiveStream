package com.viettel.mocha.rmlivestream.channel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentAllChannelBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.RmUtils
import com.viettel.mocha.rmlivestream.channel.adapter.RMChannelAdapter
import com.viettel.mocha.rmlivestream.channel.listner.RmCallApiFollowChannelListener
import com.viettel.mocha.rmlivestream.channel.listner.RmOnClickChannel
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.RmChannelResponse
import com.viettel.mocha.ui.view.load_more.OnEndlessScrollListener
import com.viettel.mocha.v5.dialog.DialogConfirm
import com.viettel.mocha.v5.home.base.BaseDialogFragment

class RMAllChannelFragment : Fragment() {

    private lateinit var binding: RmFragmentAllChannelBinding
    private lateinit var listChannel: ArrayList<RMChannel>
    private lateinit var channelListAdapter: RMChannelAdapter

    var isLoading: Boolean = false
    var isLeftData: Boolean = true

    companion object {
        fun newInstance(): RMAllChannelFragment {
            val args = Bundle()
            val fragment = RMAllChannelFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmFragmentAllChannelBinding.inflate(layoutInflater)
        binding.loadingViewExtra.showLoading()

        setupAdapter()

        binding.rmLayoutHeader.lineBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(), R.color.v5_main_color
            )
        )
        binding.swipeRefreshLayout.setOnRefreshListener {
            getListChannel()
        }

        binding.loadingViewExtra.setOnClickRetryListener { view ->
            binding.loadingViewExtra.showLoading()
            getListChannel()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getListChannel()
    }

    private fun setupAdapter() {
        channelListAdapter = RMChannelAdapter(requireContext(), object : RmOnClickChannel {
            override fun onItemChannelClick(rmChannel: RMChannel) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.frame_layout,
                        RmChannelDetailFragment.newInstance(rmChannel.id, false)
                    )
                    .addToBackStack(null)
                    .commit()
            }

            override fun onClickFollowChannel(
                rmChannel: RMChannel,
                isFollow: Boolean,
                rmCallApiFollowChannelListener: RmCallApiFollowChannelListener
            ) {
                if (isFollow) {
                    LivestreamApi.getInstance()
                        .followChannel(rmChannel.id, object : HttpCallBack() {
                            @Throws(Exception::class)
                            override fun onSuccess(data: String?) {
                                rmCallApiFollowChannelListener.onSusses(true, rmChannel)
                            }

                            override fun onFailure(message: String?) {
                                super.onFailure(message)
                                rmCallApiFollowChannelListener.onFall()
                            }
                        })
                } else {

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
                                callApiUnfollow(rmChannel, rmCallApiFollowChannelListener)
                                dialogConfirm.dismiss()
                            }

                            override fun dialogLeftClick() {
                                dialogConfirm.dismiss()
                            }
                        })
                    dialogConfirm.show(childFragmentManager, "")
                }
            }

        })

        binding.recAllChannel.adapter = channelListAdapter
    }

    private fun callApiUnfollow(
        rmChannel: RMChannel,
        rmCallApiFollowChannelListener: RmCallApiFollowChannelListener
    ) {
        LivestreamApi.getInstance()
            .unFollowChannel(rmChannel.id, object : HttpCallBack() {
                @Throws(Exception::class)
                override fun onSuccess(data: String?) {
                    rmCallApiFollowChannelListener.onSusses(false, rmChannel)
                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    rmCallApiFollowChannelListener.onFall()
                }
            })
    }

    private fun getListChannel() {
        LivestreamApi.getInstance().getListChannelLivestream(0, 20, object : HttpCallBack() {
            @Throws(Exception::class)
            override fun onSuccess(data: String?) {
                try {
                    val gson = Gson()
                    val response: RmChannelResponse =
                        gson.fromJson(data, RmChannelResponse::class.java)
                    if (response.data != null && response.data.size > 0) {
                        listChannel = response.data as ArrayList<RMChannel>
                        channelListAdapter.setList(listChannel)
                        binding.recAllChannel.addOnScrollListener(object :
                            OnEndlessScrollListener(3) {
                            override fun onLoadNextPage(view: View?) {
                                super.onLoadNextPage(view)
                                if (isLeftData && !isLoading && listChannel.size >= 20) {
                                    loadMoreChannel(listChannel.size / 20)
                                    isLoading = true
                                }
                            }
                        })
                        binding.loadingViewExtra.showLoadedSuccess()
                    } else binding.loadingViewExtra.showLoadedError()
                    binding.swipeRefreshLayout.isRefreshing = false
                } catch (e: Exception) {
                    binding.loadingViewExtra.showLoadedError()
                    binding.swipeRefreshLayout.isRefreshing = false
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


    private fun loadMoreChannel(page: Int) {
        LivestreamApi.getInstance().getListChannelLivestream(page, 20, object : HttpCallBack() {
            @SuppressLint("NotifyDataSetChanged")
            @Throws(java.lang.Exception::class)
            override fun onSuccess(data: String?) {
                try {
                    val gson = Gson()
                    val response: RmChannelResponse =
                        gson.fromJson(data, RmChannelResponse::class.java)
                    listChannel.addAll(response.data)
                    channelListAdapter.notifyDataSetChanged()
                    isLoading = false
                    if (response.data.size < 20) {
                        isLeftData = false
                    }
                } catch (e: java.lang.Exception) {
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

}