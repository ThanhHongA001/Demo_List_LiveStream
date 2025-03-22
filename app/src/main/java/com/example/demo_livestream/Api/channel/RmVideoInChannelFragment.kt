package com.viettel.mocha.rmlivestream.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentVideoInChannelBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.RMLivestreamActivity
import com.viettel.mocha.rmlivestream.channel.adapter.RmVideoInChannelAdapter
import com.viettel.mocha.rmlivestream.channel.listner.RmOnClickVideoInChannel
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.RmVideoInChannelResponse
import com.viettel.mocha.ui.view.load_more.OnEndlessScrollListener

class RmVideoInChannelFragment : Fragment() {

    private lateinit var binding: RmFragmentVideoInChannelBinding
    private lateinit var rmChannel: RMChannel
    private lateinit var adapter: RmVideoInChannelAdapter
    private var lastHashId = ""
    private var offset = 0
    private val LIMIT: Int = 20
    private lateinit var listVideoInChannel: ArrayList<RMLivestream>
    var isLeftData: Boolean = true

    companion object {
        fun newInstance(rmChannel: RMChannel): RmVideoInChannelFragment {
            val args = Bundle()
            val fragment = RmVideoInChannelFragment()
            fragment.rmChannel = rmChannel
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmFragmentVideoInChannelBinding.inflate(layoutInflater)
        setupAdapter()
        getListVideoByChannel()
        return binding.root
    }


    private fun setupAdapter() {
        adapter = RmVideoInChannelAdapter(requireContext(), object : RmOnClickVideoInChannel {
            override fun onClickItemVideo(rmLivestream: RMLivestream) {
                if (activity is RMLivestreamActivity) {
                    (activity as RMLivestreamActivity).showLiveDetail(rmLivestream.id, false)
                }
            }

        })
        binding.rec.adapter = adapter


    }

    private fun getListVideoByChannel() {
        LivestreamApi.getInstance()
            .getVideoByChannel(rmChannel.id, lastHashId, offset, LIMIT, object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    try {
                        val gson = Gson()
                        val response: RmVideoInChannelResponse =
                            gson.fromJson(data, RmVideoInChannelResponse::class.java)
                        if (response.data != null && response.data.size > 0) {
                            binding.rec.visibility=View.VISIBLE
                            binding.lineEmpty.visibility=View.GONE
                            lastHashId = response.data[response.data.size - 1].lastId;
                            binding.rec.addOnScrollListener(object :
                                OnEndlessScrollListener(3) {
                                override fun onLoadNextPage(view: View?) {
                                    super.onLoadNextPage(view)
                                    if (isLeftData && listVideoInChannel.size >= LIMIT) {
                                        loadMore()
                                    }
                                }
                            })
                            listVideoInChannel = response.data as ArrayList<RMLivestream>
                            adapter.setList(listVideoInChannel)
                        }else{
                            binding.rec.visibility=View.INVISIBLE
                            binding.lineEmpty.visibility=View.VISIBLE
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    binding.rec.visibility=View.INVISIBLE
                    binding.lineEmpty.visibility=View.VISIBLE
                    binding.tvEmpty.text= requireContext().getString(R.string.rm_error_404)
                    Glide.with(requireContext())
                        .load(R.drawable.rm_ic_404)
                        .into(binding.ivEmpty)
                }

            })

    }

    private fun loadMore() {
        offset += LIMIT
        LivestreamApi.getInstance()
            .getVideoByChannel(rmChannel.id, lastHashId, offset, LIMIT, object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    try {
                        val gson = Gson()
                        val response: RmVideoInChannelResponse =
                            gson.fromJson(data, RmVideoInChannelResponse::class.java)
                        if (response.data != null && response.data.size > 0) {
                            if (response.data.size < LIMIT) {
                                isLeftData = false
                            }
                            lastHashId = response.data[response.data.size - 1].lastId;
                            listVideoInChannel.addAll(response.data as ArrayList<RMLivestream>)
                            adapter.notifyDataSetChanged()
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

}