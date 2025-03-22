package com.viettel.mocha.rmlivestream.channel.listner

import com.viettel.mocha.rmlivestream.model.RMLivestream

interface RmCallApiGetNotyListener {
    fun onSusses(isGetNoty: Boolean)
    fun onFall()
}