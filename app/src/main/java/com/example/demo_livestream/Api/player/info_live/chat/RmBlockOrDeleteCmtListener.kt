package com.viettel.mocha.rmlivestream.player.info_live.chat

import com.viettel.mocha.rmlivestream.model.LivestreamChatModel

interface RmBlockOrDeleteCmtListener {
    fun onClickBlock(model: LivestreamChatModel)
    fun onClickDelete(model: LivestreamChatModel)
}