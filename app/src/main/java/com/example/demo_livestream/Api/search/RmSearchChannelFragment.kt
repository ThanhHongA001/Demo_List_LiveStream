package com.viettel.mocha.rmlivestream.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentTabSearchBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.RmUtils
import com.viettel.mocha.rmlivestream.channel.RmChannelDetailFragment
import com.viettel.mocha.rmlivestream.channel.adapter.RMChannelAdapter
import com.viettel.mocha.rmlivestream.channel.listner.RmCallApiFollowChannelListener
import com.viettel.mocha.rmlivestream.channel.listner.RmOnClickChannel
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.RmChannelResponse
import com.viettel.mocha.v5.dialog.DialogConfirm
import com.viettel.mocha.v5.home.base.BaseDialogFragment

class RmSearchChannelFragment : Fragment() {

    private lateinit var binding: RmFragmentTabSearchBinding
    private lateinit var adapter: RMChannelAdapter
    private var lastHashId = ""
    private val offset = 0
    private val LIMIT: Int = 20
    private lateinit var listChannel: ArrayList<RMChannel>
    private lateinit var viewModel: RmSearchViewModel
    private var keySearch = ""


    companion object {
        fun newInstance(): RmSearchChannelFragment {
            val args = Bundle()
            val fragment = RmSearchChannelFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmFragmentTabSearchBinding.inflate(layoutInflater)
        initView()
        initData()
        return binding.root
    }


    private fun initView() {

        listChannel = arrayListOf()
        adapter = RMChannelAdapter(requireContext(), object : RmOnClickChannel {
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
        binding.rec.adapter = adapter

        viewModel = ViewModelProvider(requireActivity())[RmSearchViewModel::class.java]
        viewModel.keySearch.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                keySearch = it
                getListChannelByKey(it)
            } else {
                listChannel.clear()
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun initData() {


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

    fun getListChannelByKey(key: String) {
        LivestreamApi.getInstance().searchChannel(0, 10, key, object : HttpCallBack() {
            override fun onSuccess(data: String?) {
                val gson = Gson()
                val response: RmChannelResponse =
                    gson.fromJson(data, RmChannelResponse::class.java)
                if (response.data.size > 0) {
                    binding.lineNoData.visibility = View.GONE
                    listChannel = response.data
                    adapter.setList(listChannel)
                } else {
                    listChannel.clear()
                    adapter.notifyDataSetChanged()
                    binding.lineNoData.visibility = View.VISIBLE
                }

            }

        })
    }


}