package com.viettel.mocha.rmlivestream.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viettel.mocha.app.ApplicationController
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.network.response.RMLivestreamResponse
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.RmChannelResponse

class RMHomeLivestreamViewModel : ViewModel() {
    var responseBanner = MutableLiveData<ArrayList<RMLivestream>>()
    var responseLive = MutableLiveData<ArrayList<RMLivestream>>()
    var responseChannel = MutableLiveData<ArrayList<RMChannel>>()
    init {
        getListBanner()
        getListLive()
        getListChannel()
    }
    fun getListBanner(){
        responseLive.value?.clear()
        LivestreamApi.getInstance().getListLivestream(4,0,5,object : HttpCallBack() {
            override fun onSuccess(data: String?) {
                val result: RMLivestreamResponse =
                    ApplicationController.self().gson.fromJson(data, RMLivestreamResponse::class.java)
                if(result.listLivStream.size > 0) {
                    responseBanner.postValue(result.listLivStream)
                } else {
                    responseBanner.postValue(java.util.ArrayList())
                }
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
                responseBanner.postValue(java.util.ArrayList())
            }
        })
    }
    fun getListLive(){
        responseLive.value?.clear()
        LivestreamApi.getInstance().getListLivestream(1,0,10,object : HttpCallBack() {
            override fun onSuccess(data: String?) {
                val result: RMLivestreamResponse =
                    ApplicationController.self().gson.fromJson(data, RMLivestreamResponse::class.java)
                if(result.listLivStream.size > 0) {
                    responseLive.postValue(result.listLivStream)
                } else {
                    responseLive.postValue(java.util.ArrayList())
                }
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
                responseLive.postValue(java.util.ArrayList())
            }
        })
    }
    fun getListChannel(){
        responseLive.value?.clear()
        LivestreamApi.getInstance().getListChannel(0,5,object : HttpCallBack() {
            override fun onSuccess(data: String?) {
                val result: RmChannelResponse =
                    ApplicationController.self().gson.fromJson(data, RmChannelResponse::class.java)
                if(result.data.size > 0) {
                    responseChannel.postValue(result.data)
                } else {
                    responseChannel.postValue(java.util.ArrayList())
                }
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
                responseChannel.postValue(java.util.ArrayList())
            }
        })
    }
}