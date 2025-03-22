package com.viettel.mocha.rmlivestream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.viettel.mocha.app.R
import com.viettel.mocha.app.databinding.RmDialogInputBinding

class RMDialogInput(var title: String, var listener: InputListener): DialogFragment() {
    private lateinit var binding: RmDialogInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.V5DialogRadioButton)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RmDialogInputBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = title
        binding.btnCancel.setOnClickListener {
            dismiss()
            listener.onClickLeft()
        }
        binding.btnConfirm.setOnClickListener {
            listener.onClickRight(binding.edtOtp.text.toString().trim())
            dismiss()
        }
        binding.tvResend.setOnClickListener {
            listener.onClickResend()
        }
    }
}

interface InputListener{
    fun onClickLeft()
    fun onClickRight(otp: String)
    fun onClickResend()
}