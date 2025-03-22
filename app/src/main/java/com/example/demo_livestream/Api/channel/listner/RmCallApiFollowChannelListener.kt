package com.viettel.mocha.rmlivestream.channel.listner

import com.viettel.mocha.rmlivestream.model.RMChannel

interface RmCallApiFollowChannelListener {
    fun onSusses(isFollow: Boolean, rmChannel: RMChannel)
    fun onFall()
}