package com.viettel.mocha.rmlivestream.player.info_live.chat

import com.viettel.mocha.rmlivestream.model.LivestreamChatModel

interface RMLiveDetailChatListener {
    fun onClickReactionComment(type: Int, smsgid: String?, action: String?, position: Int)
    fun onClickMore(model: LivestreamChatModel)
}