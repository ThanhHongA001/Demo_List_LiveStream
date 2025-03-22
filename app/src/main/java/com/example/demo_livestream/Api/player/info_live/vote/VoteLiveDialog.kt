package com.viettel.mocha.rmlivestream.player.info_live.vote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.viettel.mocha.app.ApplicationController
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmDialogVoteLiveBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.model.LivestreamReloadSurveyNotification
import com.viettel.mocha.rmlivestream.model.LivestreamVoteModel
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.VoteResultResponse
import com.viettel.mocha.v5.utils.ToastUtils
import org.greenrobot.eventbus.EventBus

class VoteLiveDialog(var livestream: RMLivestream, var voteModel: LivestreamVoteModel, var isVote: Boolean) : DialogFragment() {
    private lateinit var binding: RmDialogVoteLiveBinding
    private var optionAdapter: LivestreamVoteOptionAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.V5DialogRadioButton)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmDialogVoteLiveBinding.inflate(layoutInflater, container, false)
        binding.tvTitle.text = getString(R.string.rm_vote)
        binding.tvDesc.text = voteModel!!.title
        optionAdapter = LivestreamVoteOptionAdapter(activity)
        optionAdapter!!.setList(voteModel!!.voteDtos)
        optionAdapter!!.setListener { option ->

        }
        binding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        binding.recyclerView.setAdapter(optionAdapter)
        binding.ivClose.setOnClickListener {
            dismiss()
        }
        binding.btnVote.setOnClickListener {
            onConfirmClick()
        }
        return binding.root
    }
    private fun onConfirmClick() {
        var voteOptionId: Long = 0
        for (item in voteModel!!.voteDtos) {
            if (item.isSelect) {
                voteOptionId = item.id
                break
            }
        }
        if (voteOptionId < 1) {
            ToastUtils.showToast(activity, R.string.rm_select_vote_alert)
        } else {
            var type = if (isVote) {
                1
            }else{
                0
            }
            LivestreamApi.getInstance().applyVote(
                livestream.id,
                voteModel.id,
                voteOptionId,
                type,
                10,
                object : HttpCallBack() {
                    override fun onSuccess(data: String?) {
                        val response: VoteResultResponse =
                            ApplicationController.self().gson.fromJson(data, VoteResultResponse::class.java)
                        if (response.isData) {
                            ToastUtils.showToast(activity, R.string.rm_vote_success)
                            EventBus.getDefault().postSticky(LivestreamReloadSurveyNotification())
                        } else {
                            ToastUtils.showToast(activity, R.string.error_message_default)
                        }
                    }
                    override fun onFailure(throwable: Throwable?) {
                        super.onFailure(throwable)
                        ToastUtils.showToast(activity, R.string.error_message_default)
                    }
                    override fun onCompleted() {
                        super.onCompleted()
                        dismiss()
                    }
                })
        }
    }
}