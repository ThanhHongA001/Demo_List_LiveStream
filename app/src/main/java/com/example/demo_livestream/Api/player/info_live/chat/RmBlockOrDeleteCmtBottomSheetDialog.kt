package com.viettel.mocha.rmlivestream.player.info_live.chat

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.viettel.mocha.app.ApplicationController
import com.viettel.mocha.app.databinding.RmLayoutBlockDeleteBinding
import com.viettel.mocha.rmlivestream.model.LivestreamChatModel
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.ui.dialog.BottomSheetDialog
import com.viettel.mocha.util.Log

class RmBlockOrDeleteCmtBottomSheetDialog(
    context: Context,
    val livestreamChatModel: LivestreamChatModel,
    val rmLivestream: RMLivestream,
    val listener: RmBlockOrDeleteCmtListener
) : BottomSheetDialog(context) {

    private lateinit var binding: RmLayoutBlockDeleteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RmLayoutBlockDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val layoutParams = window.attributes
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = layoutParams

        binding.lineCancel.setOnClickListener {
            dismiss()
        }

        //todo nếu là sdt cua streamer dang live hien thi them block vs xoa
        if (rmLivestream.isOwner) {
            binding.lineDelete.visibility = View.VISIBLE

            if(livestreamChatModel.chatMessage.userId != ApplicationController.self().reengAccountBusiness.phoneNumberLogin) {
                binding.lineBlock.visibility = View.VISIBLE
            }
        } else {
            binding.lineBlock.setVisibility(View.GONE)
            binding.lineDelete.setVisibility(View.VISIBLE)
        }


        binding.lineBlock.setOnClickListener {
            listener.onClickBlock(livestreamChatModel)
            dismiss()
        }


        binding.lineDelete.setOnClickListener {
            listener.onClickDelete(livestreamChatModel)
            dismiss()
        }


        binding.imgClose.setOnClickListener {
            dismiss()
        }
    }



}