package com.viettel.mocha.rmlivestream.channel.listner

import com.example.demo_livestream.Api_Support.RMChannel

interface RmOnClickChannel {
    fun onItemChannelClick(rmChannel: RMChannel)
    fun onClickFollowChannel(rmChannel: RMChannel, isFollow: Boolean, rmCallApiFollowChannelListener: RmCallApiFollowChannelListener)
}