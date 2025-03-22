package com.viettel.mocha.rmlivestream.player.info_live.donate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.viettel.mocha.app.R;
import com.viettel.mocha.app.databinding.RmHolderDonateBinding;
import com.viettel.mocha.rmlivestream.model.Donate;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LivestreamDonateAdapter extends RecyclerView.Adapter<LivestreamDonateAdapter.DonateViewHolder> {
    private final Context context;
    private ArrayList<Donate> list;
    private ClickDonateListener clickStarPackageListener;

    public LivestreamDonateAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(ArrayList<Donate> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setClickStarPackageListener(ClickDonateListener clickStarPackageListener) {
        this.clickStarPackageListener = clickStarPackageListener;
    }

    @NonNull
    @Override
    public DonateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonateViewHolder(RmHolderDonateBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DonateViewHolder holder, int position) {
        Donate item = list.get(position);
        Glide.with(context).load(item.getImage()).into(holder.binding.ivCover);
        holder.binding.tvPrice.setText("$" + shortenNumber((long) item.getAmountStar()));
        holder.binding.getRoot().setOnClickListener(view -> {
            for (Donate obj : list) {
                obj.setSelected(false);
                notifyItemChanged(list.indexOf(obj));
            }
            item.setSelected(true);
            notifyItemChanged(position);
            clickStarPackageListener.onClickStarPackage(item);
        });
        if (item.isSelected()) {
            holder.binding.layoutFrame.setStroke(ContextCompat.getColor(context, R.color.rm_color_FF70), 1);
            holder.binding.layoutFrame.setBackgroundColorRound(ContextCompat.getColor(context, R.color.color_FF70_10));
        } else {
            holder.binding.layoutFrame.setStroke(ContextCompat.getColor(context, R.color.color_2F30), 1);
            holder.binding.layoutFrame.setBackgroundColorRound(ContextCompat.getColor(context, R.color.color_2F30));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class DonateViewHolder extends RecyclerView.ViewHolder {
        private final RmHolderDonateBinding binding;
        public DonateViewHolder(@NonNull RmHolderDonateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private String shortenNumber(long value) {
        String shortenValue = "";
        if (value < 1000) {
            shortenValue = String.valueOf(value);
        } else if (value < 999999) {
            shortenValue = new DecimalFormat("#.##").format((double) value / 1000) + "K";
        } else if (value < 999999999) {
            shortenValue = new DecimalFormat("#.##").format((double) value / 1000000) + "M";
        } else if (value < Long.parseLong("999999999999")) {
            shortenValue = new DecimalFormat("#.##").format((double) value / 1000000000) + "B";
        } else if (value < Long.parseLong("999999999999999")) {
            shortenValue = new DecimalFormat("#.##").format((double) value / Long.parseLong("1000000000000")) + "T";
        } else {
            shortenValue = new DecimalFormat("#.##").format((double) value / Long.parseLong("1000000000000000")) + "Q";
        }
        return shortenValue;
    }
}
