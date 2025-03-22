package com.viettel.mocha.rmlivestream.channel.listner

import com.viettel.mocha.rmlivestream.model.RMLivestream

interface RmOnClickLivestream {
    fun onClickItemVideo(rmLivestream: RMLivestream)
    fun onClickGetNoty(rmLivestream: RMLivestream, rmCallApiGetNotyListener: RmCallApiGetNotyListener)
}