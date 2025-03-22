package com.viettel.mocha.rmlivestream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmDialogConfirmBinding

class RMDialogConfirm(var title: String, var desc: String, var listener: ConfirmListener): DialogFragment() {
    private lateinit var binding: RmDialogConfirmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.V5DialogRadioButton)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmDialogConfirmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = title
        binding.tvDesc.text = desc
        binding.btnCancel.setOnClickListener {
            dismiss()
            listener.onClickLeft()
        }
        binding.btnConfirm.setOnClickListener {
            listener.onClickRight()
            dismiss()
        }
    }
}

interface ConfirmListener{
    fun onClickLeft()
    fun onClickRight()
}