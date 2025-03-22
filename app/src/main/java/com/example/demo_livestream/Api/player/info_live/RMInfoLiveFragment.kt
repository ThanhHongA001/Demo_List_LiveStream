package com.viettel.mocha.rmlivestream.player.info_live

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Transition
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.viettel.mocha.app.ApplicationController
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentInfoLiveBinding
import com.viettel.mocha.database.model.ReengAccount
import com.viettel.mocha.helper.UrlConfigHelper
import com.viettel.mocha.helper.httprequest.HttpHelper
import com.viettel.mocha.rmlivestream.RMConstants
import com.viettel.mocha.rmlivestream.RMLivestreamActivity
import com.viettel.mocha.rmlivestream.model.LiveStreamBlockNotification
import com.viettel.mocha.rmlivestream.model.LiveStreamLikeNotification
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.player.RMLivestreamDetailViewModel
import com.viettel.mocha.rmlivestream.player.info_live.chat.RMLiveDetailChatFragment
import com.viettel.mocha.rmlivestream.player.info_live.donate.RechargeStarDialog
import com.viettel.mocha.rmlivestream.player.info_live.donate.SendStarDialog
import com.viettel.mocha.rmlivestream.player.info_live.info.RMLiveDetailInfoFragment
import com.viettel.mocha.rmlivestream.player.info_live.reaction.RMLiveReactionAdapter
import com.viettel.mocha.rmlivestream.player.info_live.reaction.RMLiveReactionListener
import com.viettel.mocha.rmlivestream.player.info_live.reaction.reactionamination.Direction
import com.viettel.mocha.rmlivestream.player.info_live.reaction.reactionamination.ZeroGravityAnimation
import com.viettel.mocha.rmlivestream.socket.SocketManagerV2
import com.viettel.mocha.rmlivestream.socket.stomp.dto.StompHeader
import com.viettel.mocha.v5.utils.ToastUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class RMInfoLiveFragment : Fragment(), RMLiveReactionListener {
    companion object{
        fun newInstance(): RMInfoLiveFragment {
            val fragment = RMInfoLiveFragment()
            return fragment
        }
    }
    private lateinit var binding: RmFragmentInfoLiveBinding
    private lateinit var viewModel: RMLivestreamDetailViewModel
    private var currentVideo: RMLivestream? = null
    private var adapterPage: RMLivestreamDetailPagerAdapter? = null
    private var adapterReaction: RMLiveReactionAdapter? = null
    private var listReaction = ArrayList<RMConstants.ChatFunction>()
    private var urlAvatar = ""
    private var userNumber = ""
    private var lastChange = ""
    private lateinit var animation: ZeroGravityAnimation
    private var isBlock=false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RmFragmentInfoLiveBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[RMLivestreamDetailViewModel::class.java]
        EventBus.getDefault().register(this)
        initData()
        initEvent()
        return binding.root
    }

    private fun initEvent() {
        viewModel.responseLive.observe(requireActivity()) {
            if (it != null) {
                currentVideo = it
                loadData()
            }
        }
        viewModel.responseReact.observe(requireActivity()){
            if(it >= 0){
                for (function in listReaction) {
                    function.setChosen(false)
                }
                listReaction[it].setChosen(true)
                adapterReaction!!.notifyDataSetChanged()
            }
        }
        KeyboardUtils.registerSoftInputChangedListener(requireActivity().getWindow()) { height ->
            binding.layoutTextBox.visibility = if (height > 0) View.VISIBLE else View.GONE
            binding.handlerContainer.visibility = if (height > 0) View.GONE else View.VISIBLE
            binding.layoutTab.visibility = if (height > 0) View.GONE else View.VISIBLE
        }
        binding.buttonSend.setOnClickListener {
            if (TextUtils.isEmpty(binding.chatContent.getText().toString().trim())) {

            } else {
                if (currentVideo!!.getEnableChat() == 2) { //todo chat only follow
//                    if(isFollow) {
//                        if (SocketManagerV2.getInstance().isConnected()) {
//                            SocketManagerV2.getInstance().sendMessage(chatMessage.getText().toString().trim());
//                        } else {
//                            ToastUtils.showToast(requireActivity(), R.string.error_message_default);
//                        }
//                    } else {
//                        ToastUtils.showToast(requireActivity(), getString(R.string.only_fan));
//                    }
                } else {
                    if (currentVideo!!.getEnableChat() == 1) { //todo chat all
                        if (SocketManagerV2.getInstance().isConnected()) {
                            SocketManagerV2.getInstance().sendMessage(binding.chatContent.getText().toString().trim());
                        } else {
                            ToastUtils.showToast(requireActivity(), R.string.error_message_default)
                        }
                    }
                }
            }
            binding.chatContent.setText("");
            binding.edtPlaceholder.setText(R.string.enter_message);
        }
        binding.chatContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val content = binding.chatContent.getText().toString().trim()
                val hasContent = !TextUtils.isEmpty(content)
                if (hasContent) {
                    binding.buttonSend.setVisibility(View.VISIBLE)
                    binding.edtPlaceholder.setText(content)
                } else {

                    binding.buttonSend.setVisibility(View.GONE)
                    binding.edtPlaceholder.setText(R.string.enter_message)
                }
            }

        })
        binding.ivDonate.setOnClickListener {
            val sendStarDialog = SendStarDialog(requireActivity() as RMLivestreamActivity, currentVideo!!)
            sendStarDialog.show()
        }
        binding.ivStar.setOnClickListener {
            val rechargeStar = RechargeStarDialog(requireActivity() as RMLivestreamActivity)
            rechargeStar.show()
        }
    }

    private fun initData() {
        val account: ReengAccount = ApplicationController.self().reengAccountBusiness.currentAccount
        userNumber = ApplicationController.self().reengAccountBusiness.phoneNumberLogin
        if(!TextUtils.isEmpty(account.lastChangeAvatar)){
            lastChange = account.lastChangeAvatar
        }
        urlAvatar = ApplicationController.self().avatarBusiness.getAvatarUrl(
            lastChange,
            userNumber, 200
        )

        initReactAnimation()

        adapterPage = RMLivestreamDetailPagerAdapter(childFragmentManager)
        adapterPage!!.addFragment(RMLiveDetailInfoFragment.newInstance(), getString(R.string.rm_overview))
        adapterPage!!.addFragment(RMLiveDetailChatFragment.newInstance(), getString(R.string.rm_live_chat))
        binding.viewPager.setAdapter(adapterPage)
        binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.setCurrentItem(1)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        listReaction.add(RMConstants.ChatFunction(RMConstants.ChatFunction.TYPE_LIKE))
        listReaction.add(RMConstants.ChatFunction(RMConstants.ChatFunction.TYPE_HEART))
        listReaction.add(RMConstants.ChatFunction(RMConstants.ChatFunction.TYPE_HAHA))
        listReaction.add(RMConstants.ChatFunction(RMConstants.ChatFunction.TYPE_WOW))
        listReaction.add(RMConstants.ChatFunction(RMConstants.ChatFunction.TYPE_SAD))
        listReaction.add(RMConstants.ChatFunction(RMConstants.ChatFunction.TYPE_ANGRY))
        adapterReaction = RMLiveReactionAdapter(listReaction)
        adapterReaction!!.setListener(this)
        binding.recyclerViewReaction.setLayoutManager(LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false))
        binding.recyclerViewReaction.setAdapter(adapterReaction)


    }


    private fun initConnection() {
        if (!SocketManagerV2.getInstance().isConnected) {
            val headerList: MutableList<StompHeader>
            val account = ApplicationController.self().reengAccountBusiness.currentAccount
            headerList =
                java.util.ArrayList<StompHeader>()
            headerList.add(
                StompHeader(
                    RMConstants.WebSocket.connectorUser,
                    ApplicationController.self().reengAccountBusiness.phoneNumberLogin.replace("\\s+", "")
                )
            )
            headerList.add(
                StompHeader(
                    RMConstants.WebSocket.connectorToken,
                    account.token.replace("\\s+", "")
                )
            )
            headerList.add(
                StompHeader(
                    RMConstants.WebSocket.connectorUsername,
                    account.name.replace("\\s+", "")
                )
            )
            headerList.add(
                StompHeader(
                    RMConstants.WebSocket.connectorContentType,
                    RMConstants.WebSocket.connectorContentTypeValue
                )
            )
            SocketManagerV2.getInstance().connect(
                RMConstants.WebSocket.domain,
                headerList,
                currentVideo!!.getId(),
                currentVideo!!.channel.id
            )
        }
    }

    private fun initReactAnimation() {
        animation = ZeroGravityAnimation(requireActivity())
        animation.setCount(1)
        animation.setScalingFactor(0.25f)
        animation.setOriginationDirection(Direction.BOTTOM)
        animation.setDestinationDirection(Direction.TOP)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        }
        )
        animation.execute(urlAvatar)

        Glide.with(requireContext())
            .asBitmap()
            .load(urlAvatar)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition:  com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                    // Bitmap sau khi tải xong
                    val bitmap: Bitmap = resource
                    animation.setAvatar(bitmap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
    private fun loadData(){
        if(currentVideo == null) return
        initConnection()
        if(currentVideo!!.isEnableDonate) {
            binding.ivDonate.visibility = View.VISIBLE
            binding.ivStar.visibility = View.VISIBLE
        } else {
            binding.ivDonate.visibility = View.GONE
            binding.ivStar.visibility = View.GONE
        }



        if(currentVideo!!.isBlockComment && currentVideo?.isOwner!=true){
            isBlock=true
            binding.edtPlaceholder.setText(getString(R.string.rm_you_are_block))

            val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view: View? = requireActivity().getCurrentFocus()
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(requireActivity())
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }else{
            isBlock=false
            binding.edtPlaceholder.setOnClickListener {
                if (binding.viewPager.currentItem != 1) {
                    binding.viewPager.setCurrentItem(1)
                }
                binding.chatContent.requestFocus()
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.chatContent, InputMethodManager.SHOW_IMPLICIT)
            }

        }

        binding.edtPlaceholder.setOnClickListener { view ->
            if(isBlock){
                ToastUtils.showToast(requireActivity(), getString(R.string.rm_you_are_block_full))
            }else{
                if (binding.viewPager.currentItem != 1) {
                    binding.viewPager.setCurrentItem(1)
                }
                binding.chatContent.requestFocus()
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.chatContent, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    override fun onFunctionClick(functionType: Int) {
        try {
            var scale = 0.5f
            if (StringUtils.isEmpty(lastChange)) {
                scale = 0.35f
            }
            if (functionType == RMConstants.ChatFunction.TYPE_LIKE || functionType == RMConstants.ChatFunction.TYPE_LIKED) {
                doActionLike(1)
                animation.setScalingFactor(scale)
                onClickFlyEmojiV2(R.drawable.ic_react_like_png)
            }
            if (functionType == RMConstants.ChatFunction.TYPE_HEART) {
                doActionLike(2)
                animation.setScalingFactor(scale)
                onClickFlyEmojiV2(R.drawable.ic_react_heart_png)
            }
            if (functionType == RMConstants.ChatFunction.TYPE_HAHA) {
                doActionLike(3)
                animation.setScalingFactor(scale)
                onClickFlyEmojiV2(R.drawable.ic_react_haha_png)
            }
            if (functionType == RMConstants.ChatFunction.TYPE_WOW) {
                doActionLike(4)
                animation.setScalingFactor(scale)
                onClickFlyEmojiV2(R.drawable.ic_react_wow_png)
            }
            if (functionType == RMConstants.ChatFunction.TYPE_SAD) {
                doActionLike(5)
                animation.setScalingFactor(scale)
                onClickFlyEmojiV2(R.drawable.ic_react_sad_png)
            }
            if (functionType == RMConstants.ChatFunction.TYPE_ANGRY) {
                doActionLike(6)
                animation.setScalingFactor(scale)
                onClickFlyEmojiV2(R.drawable.ic_react_angry_png)
            }

//            if (functionType == RMConstants.ChatFunction.TYPE_DONATE) {
//                openDialogSendStars()
//            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("reactError", "onFunctionClick: $e")
        }
    }

    fun onClickFlyEmoji(resId: Int) {
        animation.play(requireActivity(), binding.animationHolder, false)
        Handler().postDelayed({ animation.setImage(resId) }, 500)
    }

    @Throws(IOException::class)
    fun onClickFlyEmojiV2(resId: Int) {
        animation.play(requireActivity(), binding.animationHolder, true)
        Handler().postDelayed({ animation.setImage(resId) }, 500)
    }
    private fun doActionLike(reatId: Int) {
        if(currentVideo == null) return
        viewModel.reactionLive(currentVideo!!.id, currentVideo!!.channel.id, reatId)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public fun onReceiveBlockNotification(event: LiveStreamBlockNotification) {
        if (event.userId.equals(ApplicationController.self().getReengAccountBusiness().phoneNumberLogin)) {
            if (event.blockId.equals("5")) {
                isBlock=true
                binding.edtPlaceholder.setText(getString(R.string.rm_you_are_block))
                binding.edtPlaceholder.setOnClickListener { view ->
                    ToastUtils.showToast(requireActivity(), getString(R.string.rm_you_are_block_full))
                }
                val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                //Find the currently focused view, so we can grab the correct window token from it.
                var view: View? = requireActivity().getCurrentFocus()
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view == null) {
                    view = View(requireActivity())
                }
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } else if (event.blockId.equals("6")) {
                isBlock=false
                binding.edtPlaceholder.setText(R.string.enter_message)
                binding.edtPlaceholder.setOnClickListener { view ->
                    binding.chatContent.requestFocus()
                    val imm =
                        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(
                        binding.chatContent,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
                ToastUtils.showToast(requireActivity(), getString(R.string.rm_unblock_live_chat_notify_message))
            }
        }
        EventBus.getDefault().removeStickyEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    @Throws(
        IOException::class
    )
    fun onReceiveLikeNumber(liveStreamLikeNotification: LiveStreamLikeNotification) {
//        tvLike.setText(String.valueOf(liveStreamLikeNotification.getTotalLike()));
        if (liveStreamLikeNotification.getUserId() == ApplicationController.self().getReengAccountBusiness()
                .phoneNumberLogin
        ) {
            return
        }
        if (liveStreamLikeNotification.getType() == 1) {
            animation.setScalingFactor(0.25f)
            onClickFlyEmoji(R.drawable.ic_react_like_png)
        } else if (liveStreamLikeNotification.getType() == 2) {
            animation.setScalingFactor(0.25f)
            onClickFlyEmoji(R.drawable.ic_react_heart_png)
        } else if (liveStreamLikeNotification.getType() == 3) {
            animation.setScalingFactor(0.25f)
            onClickFlyEmoji(R.drawable.ic_react_haha_png)
        } else if (liveStreamLikeNotification.getType() == 4) {
            animation.setScalingFactor(0.25f)
            onClickFlyEmoji(R.drawable.ic_react_wow_png)
        } else if (liveStreamLikeNotification.getType() == 5) {
            animation.setScalingFactor(0.25f)
            onClickFlyEmoji(R.drawable.ic_react_sad_png)
        } else if (liveStreamLikeNotification.getType() == 6) {
            animation.setScalingFactor(0.25f)
            onClickFlyEmoji(R.drawable.ic_react_angry_png)
        }

        EventBus.getDefault().removeStickyEvent(liveStreamLikeNotification)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        SocketManagerV2.getInstance().disconnect()
        EventBus.getDefault().unregister(this)
    }
}