package com.example.demo_livestream.Api;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.viettel.mocha.app.databinding.RmActivityDonateTermsBinding;


public class RMDonateTermsActivity extends AppCompatActivity {

    private RmActivityDonateTermsBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RmActivityDonateTermsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        binding.webView.getSettings().setAllowContentAccess(true);
        binding.webView.loadUrl("https://mytel.com.mm");

        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }
}