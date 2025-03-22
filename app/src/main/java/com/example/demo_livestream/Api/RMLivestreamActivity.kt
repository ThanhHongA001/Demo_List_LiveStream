package com.viettel.mocha.rmlivestream

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.viettel.mocha.activity.BaseSlidingFragmentActivity
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmActivityLivestreamBinding
import com.viettel.mocha.rmlivestream.channel.RMAllChannelFragment
import com.viettel.mocha.rmlivestream.channel.RmAllLivestreamFragment
import com.viettel.mocha.rmlivestream.channel.RmChannelDetailFragment
import com.viettel.mocha.rmlivestream.comment.RmCommentVideoFragment
import com.viettel.mocha.rmlivestream.home.RMHomeLivestreamFragment
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.model.RMLivestream
import com.viettel.mocha.rmlivestream.player.RMLivestreamDetailFragment
import com.viettel.mocha.rmlivestream.player.info_live.donate.RmStarFragment
import com.viettel.mocha.rmlivestream.search.RmSearchFragment


class RMLivestreamActivity : BaseSlidingFragmentActivity() {
    private lateinit var binding: RmActivityLivestreamBinding
    private lateinit var mFragmentManager: FragmentManager

    lateinit var currentVideo: RMLivestream
    lateinit var currentChannel: RMChannel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RmActivityLivestreamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mFragmentManager = supportFragmentManager
        val screen = intent.getStringExtra(RMConstants.KeyScreen)
        if (screen == RMConstants.Screen.HOME) {
            executeAddFragmentTransaction(
                RMHomeLivestreamFragment.newInstance(),
                binding.frameLayout.id,
                false,
                true
            )
        } else if (screen == RMConstants.Screen.CHANNEL_DETAIL) {
            val id = intent.getStringExtra(RMConstants.KeyId)
            if (id != null && id.isNotEmpty()) {
                showDetailChannel(id, true)
            } else {
                executeAddFragmentTransaction(
                    RMHomeLivestreamFragment.newInstance(),
                    binding.frameLayout.id,
                    false,
                    true
                )
            }
        } else if (screen == RMConstants.Screen.LIVESTREAM_DETAIL) {
            val id = intent.getStringExtra(RMConstants.KeyId)
            if (id != null && id.isNotEmpty()) {
                showLiveDetail(id, true)
            } else {
                executeAddFragmentTransaction(
                    RMHomeLivestreamFragment.newInstance(),
                    binding.frameLayout.id,
                    false,
                    true
                )
            }
        } else {
            executeAddFragmentTransaction(
                RMHomeLivestreamFragment.newInstance(),
                binding.frameLayout.id,
                false,
                true
            )
        }
    }

    fun showLiveDetail(id: String, fromVTM: Boolean) {
        val existingFragment: Fragment? =
            supportFragmentManager.findFragmentByTag(RMConstants.RM_LIVESTREAM_FRG)

//        if (existingFragment !=null){
//            if(existingFragment is RMLivestreamDetailFragment){
//                supportFragmentManager.popBackStack()
//                existingFragment.setIdVideo(id)
//                existingFragment.getData()
//            }
//        }else{
//            rmExecuteAddFragmentTransaction(RMLivestreamDetailFragment.newInstance(id, fromVTM), binding.frameLayout.id, "RMLivestreamDetailFragment")
//        }

        if (existingFragment != null) {


            if (existingFragment is RMLivestreamDetailFragment) {
//                supportFragmentManager.popBackStack(
//                    RMConstants.RM_LIVESTREAM_FRG,
//                    0
//                )

                val mFragmentTransactionV1= mFragmentManager.beginTransaction()
                mFragmentTransactionV1.remove(existingFragment)
                mFragmentTransactionV1.commit()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            rmExecuteAddFragmentTransaction(
                RMLivestreamDetailFragment.newInstance(id, fromVTM),
                binding.frameLayout.id,
                RMConstants.RM_LIVESTREAM_FRG
            )
        }, 150)


    }

    fun showDetailChannel(id: String, fromVTM: Boolean) {
        val existingFragment: Fragment? =
            supportFragmentManager.findFragmentByTag(RMConstants.RM_CHANNEL_FRG)

//        if(existingFragment!=null){
//            if(existingFragment is RmChannelDetailFragment){
//                existingFragment.setIdChannel(id)
//                existingFragment.getChannelInfo()
//                supportFragmentManager.popBackStack()
//            }
//        }else{
//            rmExecuteAddFragmentTransaction(RmChannelDetailFragment.newInstance(id, fromVTM), binding.frameLayout.id, "RmChannelDetailFragment")
//        }

        if (existingFragment != null) {
            if (existingFragment is RmChannelDetailFragment) {
                val mFragmentTransactionV2 = mFragmentManager.beginTransaction()
                mFragmentTransactionV2.remove(existingFragment)
                mFragmentTransactionV2.commit()
//                supportFragmentManager.popBackStack(
//                    RMConstants.RM_CHANNEL_FRG,
//                    0
//                )
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            rmExecuteAddFragmentTransaction(
                RmChannelDetailFragment.newInstance(id, fromVTM),
                binding.frameLayout.id,
                RMConstants.RM_CHANNEL_FRG
            )
        }, 150)
    }

    fun showAllLiveStream() {
        executeAddFragmentTransaction(
            RmAllLivestreamFragment.newInstance(),
            binding.frameLayout.id,
            true,
            true
        )
    }

    fun showAllChannel() {
        executeAddFragmentTransaction(
            RMAllChannelFragment.newInstance(),
            binding.frameLayout.id,
            true,
            true
        )
    }

    fun showSearchLive() {
        executeAddFragmentTransaction(
            RmSearchFragment.newInstance(),
            binding.frameLayout.id,
            true,
            true
        )
    }

    fun showComment(rmLivestream: RMLivestream) {
        executeAddFragmentTransaction(
            RmCommentVideoFragment.newInstance(rmLivestream),
            binding.frameLayout.id,
            true,
            true
        )
    }

    fun showCurrentStar() {
        executeAddFragmentTransaction(
            RmStarFragment.newInstance(),
            binding.frameLayout.id,
            true,
            true
        )
    }


    private fun rmExecuteAddFragmentTransaction(
        fragment: Fragment?,
        fragmentParentId: Int,
        tag: String?
    ) {
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.add(fragmentParentId, fragment!!, tag)
        mFragmentTransaction.addToBackStack(tag)
        mFragmentTransaction.setCustomAnimations(
            R.anim.slide_in_from_right,
            R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right
        )
        mFragmentTransaction.commit()
    }
}