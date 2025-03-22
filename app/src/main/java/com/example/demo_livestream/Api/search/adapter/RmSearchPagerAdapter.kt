package com.viettel.mocha.rmlivestream.search.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.viettel.mocha.rmlivestream.search.RmSearchChannelFragment
import com.viettel.mocha.rmlivestream.search.RmSearchVideoFragment
import com.viettel.mocha.rmlivestream.search.RmSearchViewModel

class RmSearchPagerAdapter(
    fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> return RmSearchChannelFragment.newInstance();
            else -> return RmSearchVideoFragment.newInstance()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

}
