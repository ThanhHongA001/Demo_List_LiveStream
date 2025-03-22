package com.viettel.mocha.rmlivestream.home

import com.viettel.mocha.rmlivestream.channel.listner.RmCallApiGetNotyListener
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.model.RMLivestream

interface RMHomeLiveListener {
    fun onClickVideo(model: RMLivestream)
    fun onClickChannel(model: RMChannel)
    fun onClickVideoAll()
    fun onClickChannelAll()
    fun onClickGetNoty(rmLivestream: RMLivestream, rmCallApiGetNotyListener: RmCallApiGetNotyListener)
}