package com.viettel.mocha.rmlivestream.player.info_live.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.viettel.mocha.app.R;
import com.viettel.mocha.app.databinding.RmHolderTopDonateBinding;
import com.viettel.mocha.rmlivestream.model.TopDonate;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TopDonateAdapter extends RecyclerView.Adapter<TopDonateAdapter.TopDonateViewHolder> {
    private final Context context;
    private ArrayList<TopDonate> list;

    public TopDonateAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(ArrayList<TopDonate> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopDonateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopDonateViewHolder(RmHolderTopDonateBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopDonateViewHolder holder, int position) {
        TopDonate item = list.get(position);
        item.setRank(position + 1);
        holder.binding.tvRank.setText(String.valueOf(item.getRank()));
        holder.binding.userName.setText(item.getName());
        holder.binding.starNumber.setText(shortenNumber(item.getStarNumber()));
        Glide.with(context)
                .load(item.getAvatar())
                .placeholder(R.drawable.ic_avatar_default)
                .into(holder.binding.avatar);

        if (item.getRank() <= 3) {
            holder.binding.tvRank.setVisibility(View.GONE);
            holder.binding.ivTopRank.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(R.drawable.rm_ic_star)
                    .into(holder.binding.imvStar);
            if (item.getRank() == 1) {
                Glide.with(context)
                        .load(R.drawable.rm_ic_top1)
                        .into(holder.binding.ivTopRank);
                holder.binding.avatar.setBackgroundColor(context.getResources().getColor(R.color.color_0A80));
            } else if (item.getRank() == 2) {
                Glide.with(context)
                        .load(R.drawable.rm_ic_top2)
                        .into(holder.binding.ivTopRank);
                holder.binding.avatar.setBackgroundColor(context.getResources().getColor(R.color.color_007A));
            } else {
                Glide.with(context)
                        .load(R.drawable.rm_ic_top3)
                        .into(holder.binding.ivTopRank);
                holder.binding.avatar.setBackgroundColor(context.getResources().getColor(R.color.color_FD5E));
            }
        } else {
            holder.binding.tvRank.setVisibility(View.VISIBLE);
            holder.binding.ivTopRank.setVisibility(View.GONE);
            Glide.with(context)
                    .load(R.drawable.rm_ic_star_white)
                    .into(holder.binding.imvStar);
            holder.binding.tvRank.setTextColor(context.getResources().getColor(R.color.white));
            holder.binding.avatar.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class TopDonateViewHolder extends RecyclerView.ViewHolder {
        RmHolderTopDonateBinding binding;
        public TopDonateViewHolder(@NonNull RmHolderTopDonateBinding binding) {
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
