package com.viettel.mocha.rmlivestream.comment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.viettel.mocha.app.ApplicationController
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentCommentVideoBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.database.model.ReengAccount
import com.viettel.mocha.helper.InputMethodUtils
import com.viettel.mocha.listeners.OnSingleClickListener
import com.viettel.mocha.module.keeng.utils.DateTimeUtils
import com.viettel.mocha.module.newdetails.utils.ToastUtils
import com.viettel.mocha.rmlivestream.comment.adapter.CommentVideoListAdapter
import com.viettel.mocha.rmlivestream.comment.adapter.ReplyVideoListAdapter
import com.viettel.mocha.rmlivestream.comment.event_bus.DeleteCommentEvent
import com.viettel.mocha.rmlivestream.comment.event_bus.DeleteReplyEvent
import com.viettel.mocha.rmlivestream.comment.event_bus.LikeCommentEvent
import com.viettel.mocha.rmlivestream.comment.event_bus.PostCommentEvent
import com.viettel.mocha.rmlivestream.comment.event_bus.ShowLayoutReplyEvent
import com.viettel.mocha.rmlivestream.comment.event_bus.UpdateTotalCommentEvent
import com.viettel.mocha.rmlivestream.comment.listener.IClickLikeAndDisLike
import com.viettel.mocha.rmlivestream.comment.listener.IClickMoreComment
import com.viettel.mocha.rmlivestream.model.Actions
import com.viettel.mocha.rmlivestream.model.ChannelInfo
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.model.RRestCommentModel
import com.viettel.mocha.rmlivestream.model.RmCommentModel
import com.viettel.mocha.rmlivestream.model.VideoInfo
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.LikeResponse
import com.viettel.mocha.rmlivestream.network.response.RReplyModel
import com.viettel.mocha.rmlivestream.network.response.RRestCommentListModel
import com.viettel.mocha.ui.view.load_more.OnEndlessScrollListener
import com.viettel.mocha.util.Utilities
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONException
import org.json.JSONObject

class RmCommentVideoFragment : Fragment(), IClickMoreComment, IClickLikeAndDisLike {

    private lateinit var binding: RmFragmentCommentVideoBinding

    private var crVideo: RMLivestream? = null
    private var commentList: MutableList<RmCommentModel> = ArrayList<RmCommentModel>()
    private var replyList: MutableList<RmCommentModel> = ArrayList<RmCommentModel>()
    var commentListAdapter: CommentVideoListAdapter? = null
    var listReplyAdapter: ReplyVideoListAdapter? = null
    private var sheetBehaviorV2: BottomSheetBehavior<*>? = null
    var page: Int = 0
    private var isAbleToloadMore = false
    var levelComment: Int = 0
    private var numReply: Long = 0
    private var myName: String? = null
    private var myNumber: String? = null
    private var account: ReengAccount? = null
    private var like = false
    private var dislike = false
    private var nLike: Long = 0
    private var nDislike: Long = 0
    private val TAG: String = "RmCommentVideoFragment"
    private var mApp: ApplicationController? = null
    private var totalCommentVod: Long = 0


    companion object {
        fun newInstance(crVideo: RMLivestream): RmCommentVideoFragment {
            val args = Bundle()
            val fragment = RmCommentVideoFragment()
            fragment.crVideo = crVideo
            fragment.totalCommentVod = crVideo.totalComment.toLong()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmFragmentCommentVideoBinding.inflate(layoutInflater)
        initView()
        initComment(crVideo!!)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroyView() {
        EventBus.getDefault().postSticky(UpdateTotalCommentEvent(totalCommentVod))
        super.onDestroyView()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    private fun initView() {
        mApp = ApplicationController.self()
        binding.rmLayoutReply.rmLayoutChatBarRep.rmIvSendReply.visibility =
            View.GONE
        binding.rmLayoutChatBarCmt.rmIvSendCmt.visibility = View.GONE
        binding.ivCloseCmt.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                requireActivity().onBackPressed()
            }
        })
        binding.rmLayoutReply.rmIvBackReply.setOnClickListener(object :
            OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                if (sheetBehaviorV2 != null) {
                    sheetBehaviorV2!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
                    InputMethodUtils.hideSoftKeyboard(activity)
                    binding.rmLayoutReply.root.visibility = View.GONE
                    callApiGetListComment(crVideo!!.id)
                }
            }
        })
    }



    private fun initComment(mVideo: RMLivestream) {
        if (mVideo != null) {
            initCommentList(mVideo)
            updateHeader()

            binding.rmLayoutChatBarCmt.personChatDetailInputText.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (!TextUtils.isEmpty(s.toString())) {
                            binding.rmLayoutChatBarCmt.rmIvSendCmt.visibility =
                                View.VISIBLE
                        } else {
                            binding.rmLayoutChatBarCmt.rmIvSendCmt.visibility =
                                View.GONE
                        }
                    }

                    override fun afterTextChanged(s: Editable) {
                    }
                })
            binding.rmLayoutChatBarCmt.rmIvSendCmt.setOnClickListener {
                postComment(
                    binding.rmLayoutChatBarCmt.personChatDetailInputText.text.toString(),
                    mVideo
                )
                binding.rmLayoutChatBarCmt.personChatDetailInputText.setText(null)
            }
        }
    }

    private fun postComment(content: String, cVideo: RMLivestream) {
        val gson = Gson()
        val video: VideoInfo = VideoInfo()
        video.id = cVideo.id
        video.title = cVideo.title
        video.setDescription(cVideo.description)
        video.setOriginalPath(cVideo.hlsPlaylink)
        video.setImagePath(cVideo.linkAvatar)
        video.setDuration("")
        video.setPublishTime(0)

        val channel: ChannelInfo = ChannelInfo()
        channel.setId(cVideo.getChannel().getId())
        channel.setName(cVideo.getChannel().getName())
        channel.setImageUrl(cVideo.getChannel().imageUrl)

        val videoInfo = gson.toJson(video)
        val channelInfo = gson.toJson(channel)
        val jsonObject = JSONObject()
        val msisdn = ApplicationController.self().reengAccountBusiness.phoneNumberLogin
        val name = ApplicationController.self().reengAccountBusiness.userName
        try {
            jsonObject.put("username", msisdn)
            jsonObject.put("name", name)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val userInfo = jsonObject.toString()
        callApiPostComment(content, cVideo.getId(), userInfo, videoInfo, channelInfo)
    }

    private fun initCommentList(video: RMLivestream) {
        val checkComment = false
        binding.rmRecCmtVod.layoutManager = LinearLayoutManager(
            context, RecyclerView.VERTICAL, false
        )
        commentListAdapter = CommentVideoListAdapter(
            commentList,
            activity, mApp,
            this, video,
            this, checkComment
        )
        isAbleToloadMore = true
        page = 0
        callApiGetListComment(video!!.id)
        binding.rmRecCmtVod.adapter = commentListAdapter
    }

    private fun setAction(
        msisdn: String,
        listComment: ArrayList<RmCommentModel>?
    ): List<RmCommentModel>? {
        var isCheck = false
        if (listComment != null && listComment.size > 0) {
            for (i in listComment.indices) {
                val comment = listComment[i]
                if (comment.actions != null) {
                    for (j in 0 until comment.actions.size) {
                        val actions: Actions = comment.actions[j]
                        if (actions.msisdn.contains(msisdn)) {
                            comment.actions.clear()
                            val actions1: MutableList<Actions> = java.util.ArrayList<Actions>()
                            actions1.add(actions)
                            comment.actions = actions1
                            isCheck = true
                        }
                    }
                    if (!isCheck) {
                        comment.actions = null
                    }
                }
            }
            return listComment
        }
        return null
    }

    private fun clickLike(commentId: String, level: Int, status: Int, old_status: Int) {
        LivestreamApi.getInstance()
            .postLikeCmt(commentId,
                level, status, old_status, object : HttpCallBack() {
                    @Throws(Exception::class)
                    override fun onSuccess(data: String) {
                        try {
                            val gson = Gson()
                            val response = gson.fromJson(
                                data,
                                LikeResponse::class.java
                            )
                            if (response != null) {
                                Log.d(
                                    TAG,
                                    "onResponse: Like or Dislike Successful"
                                )
                            }


                        } catch (e: Exception) {
                            Log.d(
                                TAG,
                                "clickLike onResponse: ",
                                e
                            )
                        }
                    }

                    override fun onFailure(message: String) {
                        super.onFailure(message)
                        ToastUtils.makeText(
                            context,
                            context!!.resources
                                .getString(R.string.rm_tv_please_comment_late)
                        )
                        Log.d(
                            TAG, "clickLike onFailure: " + message
                        )
                    }
                })
    }

    private fun initReplyList(id: String, video: RMLivestream) {
        val checkReply = true
        binding.rmLayoutReply.listCommentReply.layoutManager = LinearLayoutManager(
            context, RecyclerView.VERTICAL, false
        )
        listReplyAdapter =
            ReplyVideoListAdapter(replyList, requireActivity(), mApp, video, this, this, checkReply)
        callApiGetListCommentReply(id)
        binding.rmLayoutReply.listCommentReply.adapter = listReplyAdapter
    }

    private fun sendReply(model: RmCommentModel) {
        val jsonObject = JSONObject()
        val msisdn = ApplicationController.self().reengAccountBusiness.phoneNumberLogin
        val name = ApplicationController.self().reengAccountBusiness.userName
        try {
            jsonObject.put("username", msisdn)
            jsonObject.put("name", name)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val userInfo = jsonObject.toString()
        binding.rmLayoutReply.rmLayoutChatBarRep.rmInputTextReply.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (!TextUtils.isEmpty(s.toString())) {
                        binding.rmLayoutReply.rmLayoutChatBarRep.rmIvSendReply.visibility =
                            View.VISIBLE
                    } else {
                        binding.rmLayoutReply.rmLayoutChatBarRep.rmIvSendReply.visibility =
                            View.GONE
                    }
                }

                override fun afterTextChanged(s: Editable) {
                }
            })
        binding.rmLayoutReply.rmLayoutChatBarRep.rmIvSendReply.setOnClickListener {
            callApiPostReply(
                model.id,
                binding.rmLayoutReply.rmLayoutChatBarRep.rmInputTextReply.text.toString(),
                model.contentId,
                userInfo
            )
            binding.rmLayoutReply.rmLayoutChatBarRep.rmInputTextReply.setText(
                null
            )
        }
    }

    private fun setAvatarReply(model: RmCommentModel) {
        val app = ApplicationController.self()
        account = app.reengAccountBusiness.currentAccount
        myName = account!!.getName()
        myNumber = app.reengAccountBusiness.phoneNumberLogin

        binding.rmLayoutReply.tvwCommentAvatarReply.setCompoundDrawables(
            null,
            null,
            null,
            null
        )

    }

    private fun getHeaderReply(item: RmCommentModel) {
        binding.rmLayoutReply.tvwCommentNameReply.text = item.name
        if (System.currentTimeMillis() - item.commentAt < 6000) {
            binding.rmLayoutReply.tvwCommentTimeReply.text =
                this.resources.getString(R.string.rm_text_view_just_now)
        } else binding.rmLayoutReply.tvwCommentTimeReply.setText(
            DateTimeUtils.calculateTime(
                this.resources,
                item.commentAt
            )
        )
        binding.rmLayoutReply.tvwCommentContentReply.setText(item.content)
        if (item.like <= 0) binding.rmLayoutReply.tvNumberLikeCommentReply.text = ""
        else binding.rmLayoutReply.tvNumberLikeCommentReply.text =
            java.lang.String.valueOf(item.like)
        if (item.dislike <= 0) binding.rmLayoutReply.tvNumberDisLikeCommentReply.text = ""
        else binding.rmLayoutReply.tvNumberDisLikeCommentReply.text =
            java.lang.String.valueOf(item.dislike)
        if (item.count <= 0) {
            binding.rmLayoutReply.tvNumberReply.text = ""
        } else binding.rmLayoutReply.tvNumberReply.text =
            Utilities.shortenLongNumber(item.count)
        clickToReply(item)
    }

    private fun clickToReply(model: RmCommentModel) {
        if (model.isStatusLike && model.isPhoneLike(myNumber)) {
            onLike()
            like = true
            dislike = false
        } else if (model.isStatusDislike && model.isPhoneLike(myNumber)) {
            onDislike()
            like = false
            dislike = true
        } else {
            unLikeDislike()
            like = false
            dislike = false
        }
        nLike = model.like
        nDislike = model.dislike
        binding.rmLayoutReply.llLikeCommentReply.setOnClickListener(object :
            OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                if (like) {
                    model.setStatusAction(0, myNumber)
                    like = false
                    nLike = nLike - 1
                    unLikeDislike()
                } else {
                    model.setStatusAction(1, myNumber)
                    onLike()
                    like = true
                    nLike = nLike + 1
                    if (dislike) {
                        dislike = false
                        nDislike = nDislike - 1
                    }
                }
                if (nLike <= 0) binding.rmLayoutReply.tvNumberLikeCommentReply.text = ""
                else binding.rmLayoutReply.tvNumberLikeCommentReply.text = nLike.toString()
                if (nDislike <= 0) binding.rmLayoutReply.tvNumberDisLikeCommentReply.text =
                    ""
                else binding.rmLayoutReply.tvNumberDisLikeCommentReply.text =
                    nDislike.toString()
                clickLike(model.id, 1, model.actions[0].status.toInt(), model.oldStatusLike)
            }
        })
        binding.rmLayoutReply.llDislikeCommentReply.setOnClickListener(object :
            OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                if (dislike) {
                    model.setStatusAction(0, myNumber)
                    dislike = false
                    nDislike = nDislike - 1
                    unLikeDislike()
                } else {
                    model.setStatusAction(2, myNumber)
                    dislike = true
                    onDislike()
                    nDislike = nDislike + 1
                    if (like) {
                        like = false
                        nLike = nLike - 1
                    }
                }
                if (nLike <= 0) binding.rmLayoutReply.tvNumberLikeCommentReply.text = ""
                else binding.rmLayoutReply.tvNumberLikeCommentReply.text = nLike.toString()
                if (nDislike <= 0) binding.rmLayoutReply.tvNumberDisLikeCommentReply.text =
                    ""
                else binding.rmLayoutReply.tvNumberDisLikeCommentReply.text =
                    nDislike.toString()
                clickLike(model.id, 1, model.actions[0].status.toInt(), model.oldStatusLike)
            }
        })
    }

    private fun onDislike() {
        binding.rmLayoutReply.ivDislikeCommentReply.setImageResource(R.drawable.rm_ic_dislike_new_blue)
        binding.rmLayoutReply.ivlikeCommentReply.setImageResource(R.drawable.rm_ic_like_movie_new)
    }

    private fun onLike() {
        binding.rmLayoutReply.ivDislikeCommentReply.setImageResource(R.drawable.rm_ic_dislike_movie_new)
        binding.rmLayoutReply.ivlikeCommentReply.setImageResource(R.drawable.rm_ic_like_new_blue)
    }

    private fun unLikeDislike() {
        binding.rmLayoutReply.ivDislikeCommentReply.setImageResource(R.drawable.rm_ic_dislike_movie_new)
        binding.rmLayoutReply.ivlikeCommentReply.setImageResource(R.drawable.rm_ic_like_movie_new)
    }

    private fun initLayoutReply() {
        sheetBehaviorV2 =
            BottomSheetBehavior.from<View>(binding.rmLayoutReply.root)
        if ((sheetBehaviorV2 as BottomSheetBehavior<View>).state != BottomSheetBehavior.STATE_EXPANDED) {
            (sheetBehaviorV2 as BottomSheetBehavior<View>).setState(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            (sheetBehaviorV2 as BottomSheetBehavior<View>).setState(BottomSheetBehavior.STATE_COLLAPSED)
            InputMethodUtils.hideSoftKeyboard(requireActivity())
        }
    }

    private fun updateHeader() {
        binding.tvTotalComment.text = Utilities.shortenLongNumber(
            totalCommentVod
        )
    }

    private fun callApiGetListComment(contentId: String) {
        val msisdn = ApplicationController.self().reengAccountBusiness.phoneNumberLogin
        val timestamp = System.currentTimeMillis().toString()
        val security: String = EncodeHelper.getSecurity("", timestamp.toLong())
        val page = 0
        val size = 10
        val lastHashId = ""
        LivestreamApi.getInstance()
            .getListComment(contentId,
                page,
                size,
                security, object : HttpCallBack() {
                    @Throws(java.lang.Exception::class)
                    override fun onSuccess(data: String) {
                        try {
                            val gson = Gson()
                            val response = gson.fromJson(
                                data,
                                RRestCommentListModel::class.java
                            )
                            if (response.data != null) {
                                commentList.clear()
                                if (response.data.size === 0) {
                                    binding.viewFeed.visibility = View.VISIBLE
                                } else {
                                    binding.viewFeed.visibility = View.GONE
                                }
                                setAction(
                                    msisdn,
                                    response.getData()
                                )?.let { commentList.addAll(it) }
                                commentListAdapter!!.setData(commentList)
                                val previousSize = intArrayOf(commentList.size)
                                binding.rmRecCmtVod.addOnScrollListener(object :
                                    OnEndlessScrollListener(5) {
                                    override fun onLoadNextPage(view: View?) {
                                        super.onLoadNextPage(view)
                                        if (isAbleToloadMore) {
                                            callApiCommentLoadMore(contentId)
                                            if (commentList.size < (previousSize[0])) {
                                                isAbleToloadMore = false
                                            } else {
                                                previousSize[0] += size
                                            }
                                        }
                                    }
                                })
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, "Exception", e)
                        }
                    }

                    override fun onFailure(message: String) {
                        super.onFailure(message)
                        Log.d(TAG, "get list cmt onFailure: $message")
                    }
                })
    }

    private fun callApiCommentLoadMore(contentId: String) {
        val msisdn = ApplicationController.self().reengAccountBusiness.phoneNumberLogin
        val timestamp = System.currentTimeMillis().toString()
        val security = EncodeHelper.getSecurity("", timestamp.toLong())
        val nextPage = ++this.page
        val size = 10


        LivestreamApi.getInstance()
            .getListComment(contentId,
                nextPage,
                size,
                security, object : HttpCallBack() {
                    @Throws(Exception::class)
                    override fun onSuccess(data: String) {
                        try {
                            val gson = Gson()
                            val response = gson.fromJson(
                                data,
                                RRestCommentListModel::class.java
                            )
                            if (response != null && response.data.size !== 0) {
                                commentList.addAll(setAction(msisdn, response.data)!!)
                                commentListAdapter!!.notifyDataSetChanged()
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "Exception load more get cmt", e)
                        }
                    }

                    override fun onFailure(message: String) {
                        super.onFailure(message)
                        Log.d(TAG, "load more get list cmt onFailure: $message")
                    }
                })

    }

    private fun callApiPostComment(
        content: String,
        videoId: String,
        userInfo: String,
        videoInfo: String,
        channelInfo: String
    ) {

        LivestreamApi.getInstance().postCommentVideo(
            content,
            videoId,
            userInfo,
            videoInfo,
            channelInfo,
            object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    try {
                        val gson = Gson()
                        val response = gson.fromJson(
                            data,
                            RRestCommentModel::class.java
                        )
                        if (response != null) {
                            binding.viewFeed.visibility = View.GONE
                            commentList.add(0, response.data)
                            commentListAdapter!!.setData(commentList)
                            commentListAdapter!!.notifyDataSetChanged()
                            totalCommentVod+=1
                            updateHeader()
                            EventBus.getDefault()
                                .postSticky(
                                    PostCommentEvent(
                                        crVideo?.totalComment.toString()
                                    )
                                )
                        } else {
                            ToastUtils.makeText(
                                context,
                                context!!.resources
                                    .getString(R.string.rm_tv_please_comment_late)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d(
                            TAG,
                            "callApiPostComment Exception",
                            e
                        )
                    }

                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    ToastUtils.makeText(
                        context,
                        context!!.resources
                            .getString(R.string.rm_tv_please_comment_late)
                    )
                }

            }
        )
    }


    private fun callApiGetListCommentReply(id: String) {
        val msisdn = ApplicationController.self().reengAccountBusiness.phoneNumberLogin
        val page: Int = 0
        val size: Int = 10

        LivestreamApi.getInstance().getListReply(id, page, size, object : HttpCallBack() {
            override fun onSuccess(data: String?) {
                try {
                    val gson = Gson()
                    val response = gson.fromJson(
                        data,
                        RRestCommentListModel::class.java
                    )

                    if (response != null) {
                        replyList.clear()
                        replyList.addAll(setAction(msisdn, response.data)!!)
                        listReplyAdapter!!.setData(replyList)
                    }
                } catch (e: java.lang.Exception) {
                    Log.d(
                        TAG, "onResponse: ", e
                    )
                }
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
                Log.d(
                    TAG, "callApiGetListCommentReply onFailure: " + message
                )
            }

        })
    }

    private fun callApiPostReply(id: String, content: String, contentId: String, userInfo: String) {
        LivestreamApi.getInstance()
            .postReply(id, content, contentId, userInfo, object : HttpCallBack() {
                override fun onSuccess(data: String?) {
                    try {
                        val gson = Gson()
                        val response = gson.fromJson(
                            data,
                            RReplyModel::class.java
                        )

                        if (response != null) {
                            replyList.add(0, response.getData())
                            listReplyAdapter!!.setData(replyList)
                            numReply += 1
                            if (numReply <= 0) {
                                binding.rmLayoutReply.tvNumberReply.text = ""
                            } else binding.rmLayoutReply.tvNumberReply.text =
                                numReply.toString()
                            binding.rmLayoutReply.tvNumberReply.text =
                                Utilities.shortenLongNumber(numReply)
                            totalCommentVod+=1
                            updateHeader()
                            EventBus.getDefault()
                                .postSticky(
                                    PostCommentEvent(
                                        crVideo?.totalComment.toString()
                                    )
                                )
                        }
                    } catch (e: Exception) {
                        Log.d(
                            TAG,
                            "callApiPostReply onResponse: ",
                            e
                        )
                    }
                }


                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    ToastUtils.makeText(
                        context,
                        context!!.getResources()
                            .getString(R.string.rm_tv_please_comment_late)
                    )
                    Log.d(
                        TAG,
                        "callApiPostReply onFailure: " + message
                    )
                }

            })
    }


    override fun onClickMoreComment(
        rmCommentModel: RmCommentModel,
        video: RMLivestream?,
    ) {
        val dialogReport =
            BottomSheetReportDialog(
                requireActivity(),
                rmCommentModel.contentId,
                rmCommentModel.msisdn,
                rmCommentModel.content
            )
        val dialogDelete = BottomSheetDeleteCommentDialog(
            requireActivity(),
            rmCommentModel,
            video
        )
        val myNumber = ApplicationController.self().reengAccountBusiness.phoneNumberLogin
        if (rmCommentModel.msisdn == myNumber) {
            dialogDelete.show()
        } else dialogReport.show()
    }

    override fun onClickMoreReply(
        rmCommentModel: RmCommentModel,
        video: RMLivestream?,
    ) {
        val dialogReport = BottomSheetReportDialog(
            requireActivity(),
            rmCommentModel.contentId,
            rmCommentModel.msisdn,
            rmCommentModel.content
        )
        val dialogDelete = BottomSheetDeleteCommentDialog(
            requireActivity(),
            rmCommentModel,
            video
        )
        val myNumber = ApplicationController.self().reengAccountBusiness.phoneNumberLogin
        if (rmCommentModel.msisdn == myNumber) {
            dialogDelete.show()
        } else dialogReport.show()
    }


    override fun onClickLike(model: RmCommentModel, position: Int) {
        commentList[position] = model
        commentListAdapter!!.setData(commentList)
        levelComment = if (model!!.level === 2) 2
        else 1
        clickLike(
            model!!.id, levelComment,
            model.actions[0].status.toInt(), model.oldStatusLike
        )
    }

    override fun onClickDisLike(model: RmCommentModel, position: Int) {
        commentList[position] = model
        commentListAdapter!!.setData(commentList)
        levelComment = if (model.level === 2) 2
        else 1
        clickLike(model.id, levelComment, model.actions[0].status.toInt(), model.oldStatusLike)
    }

    override fun onClickLikeReply(model: RmCommentModel, position: Int) {
        replyList.set(position, model)
        listReplyAdapter!!.setData(replyList)
        levelComment = if (model.level === 2) 2
        else 1
        clickLike(model.id, levelComment, model.actions[0].status.toInt(), model.oldStatusLike)
    }

    override fun onClickDisLikeReply(model: RmCommentModel, position: Int) {
        replyList[position] = model
        listReplyAdapter!!.setData(replyList)
        levelComment = if (model.level === 2) 2
        else 1
        clickLike(model.id, levelComment, model.actions[0].status.toInt(), model.oldStatusLike)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun deleteReply(event: DeleteReplyEvent) {
        listReplyAdapter!!.removeItem(event.getCommentId())
        listReplyAdapter!!.notifyDataSetChanged()
        numReply -= 1
        if (numReply <= 0) {
            binding.rmLayoutReply.tvNumberReply.text = ""
        } else binding.rmLayoutReply.tvNumberReply.text = numReply.toString()
        totalCommentVod-=1
        updateHeader()
        EventBus.getDefault().removeStickyEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun deleteComment(event: DeleteCommentEvent) {
        commentListAdapter!!.removeItem(event.rmCommentModel.commentId)
        commentListAdapter!!.notifyDataSetChanged()
        totalCommentVod-=(event.rmCommentModel.count+1)
        updateHeader()
        EventBus.getDefault().removeStickyEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showLayoutReply(event: ShowLayoutReplyEvent) {
        binding.rmLayoutReply.root.visibility = View.VISIBLE
        binding.rmLayoutReply.tvNumberReply.text = ""
        initLayoutReply()
        initReplyList(event.getModel().getId(), crVideo!!)
        getHeaderReply(event.getModel())
        setAvatarReply(event.getModel())
        numReply = event.getModel().getCount()
        sendReply(event.getModel())
        if (event.getCheckClick() === 1) {
            onLike()
        } else if (event.getCheckClick() === 2) onDislike()
        else unLikeDislike()
        EventBus.getDefault().removeStickyEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun likeComment(event: LikeCommentEvent) {
        EventBus.getDefault().removeStickyEvent(event)
    }

}