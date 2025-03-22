package com.viettel.mocha.rmlivestream.channel


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.viettel.mocha.app.databinding.RmFragmentChannelInfoBinding
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.util.Utilities
import java.text.SimpleDateFormat

class RmChannelInfoFragment: Fragment() {

    private lateinit var binding: RmFragmentChannelInfoBinding
    private lateinit var rmChannel: RMChannel


    companion object{
        fun newInstance(rmChannel: RMChannel): RmChannelInfoFragment {
            val args = Bundle()
            val fragment = RmChannelInfoFragment()
            fragment.rmChannel=rmChannel
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=RmFragmentChannelInfoBinding.inflate(layoutInflater)

        initView()
        initData()
        return binding.root
    }

    private fun initView() {
        if (Utilities.notEmpty(rmChannel.description)) {
            binding.tvIntroduction.visibility = View.VISIBLE
            binding.tvIntroduction.text = Html.fromHtml(rmChannel.description)
        } else {
            binding.tvIntroduction.visibility = View.GONE
        }

        if (rmChannel.createdDate.toInt() == 0) {
            binding.tvCreateTime.visibility = View.GONE
        } else {
            binding.tvCreateTime.text = Html.fromHtml(calculateTime(rmChannel.createdDate))
            binding.tvCreateTime.visibility = View.VISIBLE
        }

    }

    private fun initData() {

    }

    private fun calculateTime(time: Long): String {
        @SuppressLint("SimpleDateFormat") val sdfDate = SimpleDateFormat("dd/MM/yyyy")
        return sdfDate.format(time)
    }


}