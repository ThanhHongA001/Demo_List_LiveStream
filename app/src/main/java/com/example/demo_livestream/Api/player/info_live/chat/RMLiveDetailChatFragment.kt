package com.viettel.mocha.rmlivestream.player.info_live.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.viettel.mocha.adapter.BaseAdapter
import com.viettel.mocha.app.ApplicationController
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentLiveDetailChatBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.ConfirmListener
import com.viettel.mocha.rmlivestream.RMConstants
import com.viettel.mocha.rmlivestream.RMDialogConfirm
import com.viettel.mocha.rmlivestream.RMLivestreamActivity
import com.viettel.mocha.rmlivestream.model.LiveStreamBlockNotification
import com.viettel.mocha.rmlivestream.model.LiveStreamChatMessage
import com.viettel.mocha.rmlivestream.model.LiveStreamGiftMessage
import com.viettel.mocha.rmlivestream.model.LivestreamChatModel
import com.viettel.mocha.rmlivestream.model.LivestreamReloadSurveyNotification
import com.viettel.mocha.rmlivestream.model.LivestreamVoteModel
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.model.ReactCommentNotification
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.ListVoteLivestreamResponse
import com.viettel.mocha.rmlivestream.player.RMLivestreamDetailViewModel
import com.viettel.mocha.rmlivestream.player.info_live.info.TopDonateDialog
import com.viettel.mocha.rmlivestream.player.info_live.vote.VoteLiveDialog
import com.viettel.mocha.util.Log
import com.viettel.mocha.v5.utils.ToastUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RMLiveDetailChatFragment : Fragment(), RMLiveDetailChatListener {
    companion object {
        fun newInstance(): RMLiveDetailChatFragment {
            val fragment = RMLiveDetailChatFragment()
            return fragment
        }
    }

    private lateinit var binding: RmFragmentLiveDetailChatBinding
    private lateinit var viewModel: RMLivestreamDetailViewModel
    private var currentVideo: RMLivestream? = null
    private var adapter: RMLiveDetailChatAdapter? = null
    private var data: ArrayList<LivestreamChatModel> = ArrayList()
    private var currentVote: LivestreamVoteModel? = null
    private var isVoted = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmFragmentLiveDetailChatBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[RMLivestreamDetailViewModel::class.java]
        EventBus.getDefault().register(this)
        initEvent()

        return binding.root
    }

    private fun initEvent() {
        viewModel.responseLive.observe(requireActivity()) {
            if (it != null) {
                currentVideo = it
                initData()
                adapter = RMLiveDetailChatAdapter(requireActivity(), this)
                adapter?.setVideoLiveStream(it)
                adapter!!.items = data
                BaseAdapter.setupVerticalRecycler(
                    requireActivity(),
                    binding.recyclerView,
                    null,
                    adapter,
                    true
                )
            }
        }
        binding.layoutVote.setOnClickListener {
            if (currentVideo != null && currentVote != null) {
                val vote = VoteLiveDialog(currentVideo!!, currentVote!!, isVoted)
                vote.show(requireActivity().supportFragmentManager, " Vote")
            }
        }
    }

    private fun initData() {
        if (currentVideo == null) return
        checkViewComment()
        if (currentVideo!!.listVote.size > 0) {
            currentVote = currentVideo!!.listVote.get(0)
            binding.layoutVote.visibility = View.VISIBLE
            for (item in currentVote!!.voteDtos) {
                if (item.isSelect()) {
                    isVoted = true
                    break
                }
            }
        } else {
            binding.layoutVote.visibility = View.GONE
        }
    }

    private fun checkViewComment() {
        if (data.size > 0) {
            binding.layoutEmpty.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.layoutEmpty.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onReceiveGift(liveStreamGiftMessage: LiveStreamGiftMessage?) {
        val chatModel = LivestreamChatModel()
        chatModel.type = RMConstants.WebSocket.typeGift
        chatModel.streamGift = liveStreamGiftMessage
        data.add(chatModel)
        adapter!!.notifyDataSetChanged()
        checkViewComment()
        binding.recyclerView.smoothScrollToPosition(adapter!!.getItemCount() - 1)
        EventBus.getDefault().removeStickyEvent(liveStreamGiftMessage)
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onReceiveChatMessage(chatContent: LiveStreamChatMessage) {
        val chatModel = LivestreamChatModel()
        chatModel.type = RMConstants.WebSocket.typeMessage
        chatModel.chatMessage = chatContent
//        if (listReactionUser != null && listReactionUser.size > 0 && adapterV2.getChatModels()
//                .size() <= 9
//        ) {
//            for (react in listReactionUser) {
//                var chatId = ""
//                if (!TextUtils.isEmpty(chatContent.getsIdMessage())) {
//                    chatId = chatContent.getsIdMessage()
//                } else if (!TextUtils.isEmpty(chatContent.getSmsgId())) {
//                    chatId = chatContent.getSmsgId()
//                }
//                if (chatId == react.getSidMessage()) {
//                    chatModel.reactionType = react.getType().toInt()
//                }
//            }
//        }
        data.add(chatModel)
        adapter!!.notifyDataSetChanged()
        binding.recyclerView.smoothScrollToPosition(adapter!!.getItemCount() - 1)
        checkViewComment()
        EventBus.getDefault().removeStickyEvent(chatContent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public fun onReceiveBlockNotification(event: LiveStreamBlockNotification) {
        if (event.getBlockId().equals("3") && currentVideo?.isOwner!=true) {
            var deletedComment: LivestreamChatModel? = LivestreamChatModel()
            for (model in data) {
                if (model.chatMessage.getsIdMessage() != null && model.chatMessage.getsIdMessage()
                        .equals(event.chatId)
                ) {
                    deletedComment = model
                }

                if (model.chatMessage.smsgId != null && model.chatMessage.smsgId.equals(event.chatId)) {
                    deletedComment = model
                }
            }
            data.remove(deletedComment)
            adapter!!.notifyDataSetChanged()
        }
        EventBus.getDefault().removeStickyEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onReceiveReactionComment(event: ReactCommentNotification) {
        for (item in data) {
            if ((!TextUtils.isEmpty(item.chatMessage.smsgId) && item.chatMessage.smsgId.equals(event.getsIdMessage()))
                || (!TextUtils.isEmpty(item.chatMessage.getsIdMessage()) && item.chatMessage.getsIdMessage()
                    .equals(event.getsIdMessage()))
            ) {
                item.chatMessage.reactions = event.reacts
                item.chatMessage.numberReaction = event.numberReaction
                adapter!!.notifyDataSetChanged()
                break
            }
        }
        EventBus.getDefault().removeStickyEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onReceiveReloadSurvey(event: LivestreamReloadSurveyNotification) {
        getListVote()
        EventBus.getDefault().removeStickyEvent(event)
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

    override fun onClickReactionComment(
        type: Int,
        smsgid: String?,
        action: String?,
        position: Int
    ) {
        LivestreamApi.getInstance().reactionComment(type, smsgid, action, object : HttpCallBack() {
            override fun onSuccess(result: String?) {
                if (action == "LIKE") {
                    data.get(position).setReactionType(type)
                    adapter!!.notifyItemChanged(position)
                } else {
                    data.get(position).setReactionType(0)
                    adapter!!.notifyItemChanged(position)
                }
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
                if (!activity!!.isFinishing) {
                    ToastUtils.showToast(activity, R.string.error_message_default)
                }
            }
        })
    }

    override fun onClickMore(model: LivestreamChatModel, ) {
        val rmBlockOrDeleteCmtBottomSheetDialog = RmBlockOrDeleteCmtBottomSheetDialog(
            requireActivity() as RMLivestreamActivity,
            model,
            currentVideo!!,
            object : RmBlockOrDeleteCmtListener {
                override fun onClickBlock(model: LivestreamChatModel) {
                    processBlockUser(model)
                }

                override fun onClickDelete(model: LivestreamChatModel) {
                    processDeleteComment(model)
                }

            }
        )
        rmBlockOrDeleteCmtBottomSheetDialog!!.show()
    }

    private fun processDeleteComment(model: LivestreamChatModel) {
        var chatId = ""
        if (!TextUtils.isEmpty(model.getChatMessage().getSmsgId())) {
            chatId = model.getChatMessage().getSmsgId()
        } else if (!TextUtils.isEmpty(model.getChatMessage().getsIdMessage())) {
            chatId = model.getChatMessage().getsIdMessage()
        }
        var dialogConfirm = RMDialogConfirm(
            getString(R.string.rm_delete),
            getString(R.string.rm_message_delete_comment),
            object : ConfirmListener {
                override fun onClickLeft() {

                }

                override fun onClickRight() {
                    LivestreamApi.getInstance().deleteCommentLivestream(
                        chatId,
                        currentVideo!!.getId(),
                        currentVideo!!.channel.id,
                        model.chatMessage.avatar,
                        model.chatMessage.userName,
                        object : HttpCallBack() {
                            override fun onSuccess(dataStr: String?) {
                                ToastUtils.showToast(
                                    requireContext(),
                                    R.string.deleted_successfully
                                )

                                data.remove(model)
                                adapter!!.notifyDataSetChanged()


                            }

                            override fun onFailure(message: String?) {
                                super.onFailure(message)
                                ToastUtils.showToast(
                                    requireContext(),
                                    R.string.error_message_default
                                )
                            }
                        })
                }

            })
        dialogConfirm.show(requireActivity().supportFragmentManager, "")
    }

    private fun processBlockUser(model: LivestreamChatModel) {
        var chatId = ""
        if (!TextUtils.isEmpty(model.getChatMessage().getSmsgId())) {
            chatId = model.getChatMessage().getSmsgId()
        } else if (!TextUtils.isEmpty(model.getChatMessage().getsIdMessage())) {
            chatId = model.getChatMessage().getsIdMessage()
        }
        var dialogConfirm = RMDialogConfirm(
            getString(R.string.rm_confirm),
            getString(R.string.rm_message_block_user),
            object : ConfirmListener {
                override fun onClickLeft() {

                }

                override fun onClickRight() {
                    LivestreamApi.getInstance().blockUserCommentLivestream(
                        chatId,
                        currentVideo!!.getId(),
                        currentVideo!!.channel.id,
                        model.chatMessage.avatar,
                        model.chatMessage.userName,
                        model.chatMessage.userId,
                        object : HttpCallBack() {
                            override fun onSuccess(data: String?) {
                                ToastUtils.showToast(requireContext(), R.string.rm_block_succes)
                            }

                            override fun onFailure(message: String?) {
                                super.onFailure(message)
                                ToastUtils.showToast(
                                    requireContext(),
                                    R.string.error_message_default
                                )
                            }
                        })
                }

            })
        dialogConfirm.show(requireActivity().supportFragmentManager, "")
    }

    private fun getListVote() {
        LivestreamApi.getInstance().getListVoteLivestream(currentVideo!!.id, object :
            HttpCallBack() {
            override fun onSuccess(data: String?) {
                var response = ApplicationController.self().gson.fromJson(
                    data,
                    ListVoteLivestreamResponse::class.java
                )
                if (response.data.size > 0) {
                    currentVote = response.data.get(0)
                    binding.layoutVote.visibility = View.VISIBLE
                    for (item in currentVote!!.voteDtos) {
                        if (item.isSelect) {
                            isVoted = true
                            break
                        }
                    }

                } else {
                    binding.layoutVote.visibility = View.GONE
                }
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
                binding.layoutVote.visibility = View.GONE
            }

        })
    }
}