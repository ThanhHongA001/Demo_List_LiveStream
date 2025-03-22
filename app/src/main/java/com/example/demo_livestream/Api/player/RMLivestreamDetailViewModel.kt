package com.viettel.mocha.rmlivestream.player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.viettel.mocha.app.ApplicationController
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.model.TopDonate
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.RMLivestreamDetailResponse
import com.viettel.mocha.rmlivestream.network.response.RMReactionResponse
import com.viettel.mocha.rmlivestream.network.response.RmVideoInChannelResponse
import com.viettel.mocha.rmlivestream.network.response.TopDonateResponse
import com.viettel.xgaming.utils.Log

class RMLivestreamDetailViewModel: ViewModel() {
    var responseLive = MutableLiveData<RMLivestream>()
    var responseVideo = MutableLiveData<RMLivestream>()
    var responseVideoByChannel = MutableLiveData<ArrayList<RMLivestream>>()
    var responseTopDonate = MutableLiveData<ArrayList<TopDonate>>()
    var responseReact = MutableLiveData<Int>()
    init {

    }
    fun getLiveDetail(id: String) {
        LivestreamApi.getInstance().getLivestreamDetail(id,object : HttpCallBack() {
            override fun onSuccess(data: String?) {
                val result: RMLivestreamDetailResponse =
                    ApplicationController.self().gson.fromJson(data, RMLivestreamDetailResponse::class.java)
                if(result.model != null) {
                    responseLive.postValue(result.model)
                } else {
                    responseLive.postValue(null)
                }
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
                responseLive.postValue(null)
            }
        })
    }
    fun reactionLive(videoId: String, streamerId: String, reactId: Int){
        LivestreamApi.getInstance().reactionLive(videoId,streamerId,reactId,object : HttpCallBack() {
            override fun onSuccess(data: String?) {
                val result: RMReactionResponse =
                    ApplicationController.self().gson.fromJson(data, RMReactionResponse::class.java)
                if(result.data != null) {
                    responseReact.postValue(reactId-1)
                } else {
                    responseReact.postValue(-1)
                }
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
                responseReact.postValue(-1)
            }
        })
    }

    fun getListVideoByChannel(idChannel: String){
        LivestreamApi.getInstance()
            .getVideoByChannel(idChannel, "", 0, 20, object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    val gson = Gson()
                    val response: RmVideoInChannelResponse =
                        gson.fromJson(data, RmVideoInChannelResponse::class.java)
                    if(response.data.size > 0){
                        responseVideoByChannel.postValue(response.data)
                    } else{
                        responseVideoByChannel.postValue(ArrayList())
                    }
                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    responseVideoByChannel.postValue(ArrayList())
                }

            })
    }

    fun getListTopDonate(streamerId: String, videoId: String){
        LivestreamApi.getInstance()
            .getTopDonateLivestream(streamerId, videoId, object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    val gson = Gson()
                    val response: TopDonateResponse =
                        gson.fromJson(data, TopDonateResponse::class.java)
                    if(response.data.size > 0){
                        responseTopDonate.postValue(response.data)
                    } else{
                        responseTopDonate.postValue(ArrayList())
                    }
                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    responseTopDonate.postValue(ArrayList())
                }

            })
    }
    override fun onCleared() {
        super.onCleared()
    }
}