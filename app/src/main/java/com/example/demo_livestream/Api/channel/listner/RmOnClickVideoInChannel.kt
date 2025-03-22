package com.viettel.mocha.rmlivestream.channel.listner

import com.viettel.mocha.rmlivestream.model.RMLivestream

interface RmOnClickVideoInChannel {
    fun onClickItemVideo(rmLivestream: RMLivestream)
}