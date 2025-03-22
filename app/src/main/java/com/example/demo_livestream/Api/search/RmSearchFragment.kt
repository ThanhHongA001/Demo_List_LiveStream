package com.viettel.mocha.rmlivestream.search

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmFragmentSearchBinding
import com.viettel.mocha.rmlivestream.channel.adapter.RMChannelAdapter
import com.viettel.mocha.rmlivestream.home.RMHomeLivestreamViewModel
import com.viettel.mocha.rmlivestream.model.RMChannel
import com.viettel.mocha.rmlivestream.search.adapter.RmSearchPagerAdapter
import com.viettel.mocha.util.Log

class RmSearchFragment : Fragment() {

    private lateinit var binding: RmFragmentSearchBinding
    private lateinit var pagerAdapter: RmSearchPagerAdapter

    private var keySearch = ""
    private var oldKeySearch = ""
    private var runnableSearch: Runnable? = null
    private val handlerSearch = Handler()
    private lateinit var viewModel: RmSearchViewModel

    companion object {
        fun newInstance(): RmSearchFragment {
            val args = Bundle()
            val fragment = RmSearchFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmFragmentSearchBinding.inflate(layoutInflater)
        binding.rmLayoutHeader.lineBack.setOnClickListener {
            requireActivity().onBackPressed()
            viewModel.keySearch.value=""
        }

        initView()
        initAction()


        return binding.root
    }


    private fun initView() {
        viewModel = ViewModelProvider(requireActivity())[RmSearchViewModel::class.java]

        binding.ivClearText.setOnClickListener {
            binding.etSearch.setText("")
            viewModel.updateData("")
        }


        pagerAdapter = RmSearchPagerAdapter(this)
        binding.rmViewPager.setAdapter(pagerAdapter)
        TabLayoutMediator(
            binding.rmTabLayout, binding.rmViewPager,
            (TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                when (position) {
                    0 -> tab.setText(R.string.rm_channel)
                    1 -> tab.setText(R.string.rm_video)
                }
            })
        ).attach()

        binding.rmLayoutHeader.tvTitle.text =
            requireContext().resources.getString(R.string.rm_search)

        runnableSearch = Runnable { this.searchData() }
    }


    private fun searchData() {
        viewModel.updateData(keySearch)
    }

    private fun initAction() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                keySearch = binding.etSearch.getText().toString()

                if (TextUtils.isEmpty(keySearch)) {
                    binding.ivClearText.visibility = View.GONE
                    oldKeySearch = ""
                } else if (keySearch != oldKeySearch) {
                    binding.ivClearText.visibility = View.VISIBLE
                    handlerSearch.removeCallbacks(runnableSearch!!)
                    handlerSearch.postDelayed(runnableSearch!!, 800)
                }
                oldKeySearch = keySearch
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }


}