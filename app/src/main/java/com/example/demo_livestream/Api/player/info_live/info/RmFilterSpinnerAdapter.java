package com.viettel.mocha.rmlivestream.player.info_live.info;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.core.content.ContextCompat;

import com.viettel.mocha.app.databinding.RmItemFilterDonateBinding;
import com.viettel.mocha.app.databinding.RmItemFilterDonateDropdownBinding;

import java.util.List;
import com.viettel.mocha.app.R;

public class RmFilterSpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> items;
    private Integer selectedPosition=0;

    public RmFilterSpinnerAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") RmItemFilterDonateBinding binding = RmItemFilterDonateBinding.inflate(LayoutInflater.from(context), parent, false);
        binding.tvTitle.setText(items.get(position));
        return binding.getRoot();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        RmItemFilterDonateDropdownBinding binding = RmItemFilterDonateDropdownBinding.inflate(LayoutInflater.from(context), parent, false);
        binding.getRoot().setText(items.get(position));
        if (position == selectedPosition) {
            binding.getRoot().setTextColor(ContextCompat.getColor(context, R.color.rm_color_FF70));
        } else {
            binding.getRoot().setTextColor(ContextCompat.getColor(context, R.color.white));
        }
        return binding.getRoot();
    }


    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }
}
