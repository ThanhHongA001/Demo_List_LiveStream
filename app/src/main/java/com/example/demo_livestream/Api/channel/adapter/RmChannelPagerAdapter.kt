package com.viettel.mocha.rmlivestream.channel.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.viettel.mocha.rmlivestream.channel.RmChannelInfoFragment
import com.viettel.mocha.rmlivestream.channel.RmVideoInChannelFragment
import com.viettel.mocha.rmlivestream.model.RMChannel

class RmChannelPagerAdapter(fragment: Fragment,private val rmChannel: RMChannel) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RmVideoInChannelFragment.newInstance(rmChannel)
            else -> RmChannelInfoFragment.newInstance(rmChannel)
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
