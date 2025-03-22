package com.viettel.mocha.rmlivestream.player.info_live.donate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentStarBinding
import com.viettel.mocha.common.api.http.HttpCallBack
import com.viettel.mocha.rmlivestream.RMLivestreamActivity
import com.viettel.mocha.rmlivestream.model.RmHistoryDonate
import com.viettel.mocha.rmlivestream.network.LivestreamApi
import com.viettel.mocha.rmlivestream.network.response.CurrentStarNumberResponse
import com.viettel.mocha.rmlivestream.network.response.RmHistoryDonateResponse
import com.viettel.mocha.rmlivestream.network.response.RmVideoInChannelResponse
import com.viettel.mocha.rmlivestream.search.adapter.RmSearchPagerAdapter
import com.viettel.mocha.ui.view.load_more.OnEndlessScrollListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.DecimalFormat

class RmStarFragment: Fragment() {

    private lateinit var binding: RmFragmentStarBinding
    private lateinit var listHistoryDonate: ArrayList<RmHistoryDonate>
    private lateinit var adapter: RMHistoryDonateAdapter
    private var page=0
    var isLeftData: Boolean = true

    companion object{
        fun newInstance(): RmStarFragment {
            val args = Bundle()
            val fragment = RmStarFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=RmFragmentStarBinding.inflate(layoutInflater)
        binding.rmLayoutHeader.lineBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.loadingViewExtra.showLoading()
        binding.rmLayoutHeader.tvTitle.text=requireContext().getString(R.string.rm_star)
        binding.layoutTopup.setOnClickListener {
            val rechargeStar = RechargeStarDialog(requireActivity() as RMLivestreamActivity)
            rechargeStar.show()
        }

        adapter=RMHistoryDonateAdapter(requireContext())
        binding.recHistoryDonate.adapter=adapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getHistoryDonate()
        getCurrentStar()
    }


    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateStar(event: BuyPackageStarEvent) {
        getCurrentStar()
        getHistoryDonate()
        EventBus.getDefault().removeStickyEvent(event)
    }

    private fun getCurrentStar() {
        LivestreamApi.getInstance().getUserStarNumber(object : HttpCallBack() {
            override fun onSuccess(data: String?) {
                val gson = Gson()
                val response: CurrentStarNumberResponse =
                    gson.fromJson(data, CurrentStarNumberResponse::class.java)
                binding.tvCurrentStar.text = shortenNumber(response.data.totalStar)
                binding.loadingViewExtra.showLoadedSuccess()
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
                binding.loadingViewExtra.showLoadedError()
            }

            override fun onCompleted() {
                super.onCompleted()

            }
        })
    }

    private fun getHistoryDonate() {
        LivestreamApi.getInstance().getHistoryDonate(0, 10,object : HttpCallBack() {
            override fun onSuccess(data: String?) {
                val gson = Gson()
                val response: RmHistoryDonateResponse =
                    gson.fromJson(data, RmHistoryDonateResponse::class.java)
                if(response.data.size>0){
                    listHistoryDonate=response.data
                    adapter.setList(listHistoryDonate)

                    binding.recHistoryDonate.addOnScrollListener(object :
                        OnEndlessScrollListener(3) {
                        override fun onLoadNextPage(view: View?) {
                            super.onLoadNextPage(view)
                            if (isLeftData &&listHistoryDonate.size >= 10) {
                                loadMoreHistory(listHistoryDonate.size / 10)

                            }
                        }
                    })
                }

            }
        })
    }

    private fun loadMoreHistory(page: Int){
        LivestreamApi.getInstance().getHistoryDonate(page, 10,object : HttpCallBack() {
            override fun onSuccess(data: String?) {
                try {
                    val gson = Gson()
                    val response: RmHistoryDonateResponse =
                        gson.fromJson(data, RmHistoryDonateResponse::class.java)

                    listHistoryDonate.addAll(response.data)
                    adapter.notifyDataSetChanged()
                    if (response.data.size < 10) {
                        isLeftData = false
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })
    }

    private fun shortenNumber(value: Long): String {
        var shortenValue = ""
        shortenValue = if (value < 1000) {
            value.toString()
        } else if (value < 999999) {
            DecimalFormat("#.##").format(value.toDouble() / 1000) + "K"
        } else if (value < 999999999) {
            DecimalFormat("#.##").format(value.toDouble() / 1000000) + "M"
        } else if (value < "999999999999".toLong()) {
            DecimalFormat("#.##").format(value.toDouble() / 1000000000) + "B"
        } else if (value < "999999999999999".toLong()) {
            DecimalFormat("#.##").format(value.toDouble() / "1000000000000".toLong()) + "T"
        } else {
            DecimalFormat("#.##").format(value.toDouble() / "1000000000000000".toLong()) + "Q"
        }
        return shortenValue
    }


}