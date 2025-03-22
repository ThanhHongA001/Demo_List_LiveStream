package com.viettel.mocha.rmlivestream.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.viettel.mocha.app.databinding.RmFragmentTabSearchBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.RMLivestreamActivity
import com.viettel.mocha.rmlivestream.channel.adapter.RmVideoInChannelAdapter
import com.viettel.mocha.rmlivestream.channel.listner.RmOnClickVideoInChannel
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.RMLivestreamResponse

class RmSearchVideoFragment : Fragment() {

    private lateinit var binding: RmFragmentTabSearchBinding
    private lateinit var adapter: RmVideoInChannelAdapter
    private lateinit var listVideo: ArrayList<RMLivestream>
    private lateinit var viewModel: RmSearchViewModel
    private var keySearch=""



    companion object {
        fun newInstance(): RmSearchVideoFragment {
            val args = Bundle()
            val fragment = RmSearchVideoFragment()
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
        return binding.root
    }

    private fun initView() {
        listVideo= arrayListOf()

        adapter = RmVideoInChannelAdapter(requireContext(), object : RmOnClickVideoInChannel {
            override fun onClickItemVideo(rmLivestream: RMLivestream) {
                if (activity is RMLivestreamActivity) {
                    (activity as RMLivestreamActivity).showLiveDetail(rmLivestream.id, false)
                }
            }


        })
        binding.rec.adapter = adapter

        viewModel = ViewModelProvider(requireActivity())[RmSearchViewModel::class.java]

        viewModel.keySearch.observe(viewLifecycleOwner) { newData ->
            if(newData.isNotEmpty()){
                keySearch=newData
                getListVideoByKey(newData)
            }else{
                listVideo.clear()
                adapter.notifyDataSetChanged()
            }
        }
    }



    fun getListVideoByKey(key: String){
        LivestreamApi.getInstance().searchVideo(0, 10, key , object : HttpCallBack(){
            override fun onSuccess(data: String?) {
                val gson = Gson()
                val response: RMLivestreamResponse =
                    gson.fromJson(data, RMLivestreamResponse::class.java)
                if(response.listLivStream.size>0){
                    binding.lineNoData.visibility=View.GONE
                    listVideo=response.listLivStream
                    adapter.setList(listVideo)
                }else{
                    listVideo.clear()
                    adapter.notifyDataSetChanged()
                    binding.lineNoData.visibility=View.VISIBLE
                }

            }

        })
    }


}