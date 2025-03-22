package com.viettel.mocha.rmlivestream.player.info_live.donate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.viettel.mocha.app.R;
import com.viettel.mocha.app.databinding.RmHolderStarPackageBinding;
import com.viettel.mocha.rmlivestream.model.PackageStar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LivestreamPackageStarAdapter extends RecyclerView.Adapter<LivestreamPackageStarAdapter.PackageStarViewHolder> {
    private final Context context;
    private ArrayList<PackageStar> list;
    private ClickStarPackageListener clickStarPackageListener;

    public LivestreamPackageStarAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(ArrayList<PackageStar> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setClickStarPackageListener(ClickStarPackageListener clickStarPackageListener) {
        this.clickStarPackageListener = clickStarPackageListener;
    }

    @NonNull
    @Override
    public PackageStarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackageStarViewHolder(RmHolderStarPackageBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PackageStarViewHolder holder, int position) {
        PackageStar item = list.get(position);
//        if (item.getNumber() != 0) {
            holder.binding.tvNumberStar.setText(shortenNumber((long) item.getValue()));
//        } else {
//            holder.binding.tvAmount.setText(context.getString(R.string.text_other));
//        }
        holder.binding.tvNumberPrice.setText("$" + shortenNumber((long) item.getPrice()));
        holder.binding.getRoot().setOnClickListener(view -> {
            for (PackageStar obj : list) {
                obj.setSelected(false);
                notifyItemChanged(list.indexOf(obj));
            }
            item.setSelected(true);
            notifyItemChanged(position);
            clickStarPackageListener.onClickStarPackage(item);
        });
        if (item.isSelected()) {
            holder.binding.ivCheckStar.setVisibility(View.VISIBLE);
            holder.binding.layoutFrame.setStroke(ContextCompat.getColor(context, R.color.rm_color_FF70), 1);
            holder.binding.tvNumberPrice.setBackgroundColorRound(ContextCompat.getColor(context, R.color.color_FF70_20));
        } else {
            holder.binding.ivCheckStar.setVisibility(View.GONE);
            holder.binding.layoutFrame.setStroke(ContextCompat.getColor(context, R.color.color_2E30), 1);
            holder.binding.tvNumberPrice.setBackgroundColorRound(ContextCompat.getColor(context, R.color.color_2E30));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class PackageStarViewHolder extends RecyclerView.ViewHolder {
        private final RmHolderStarPackageBinding binding;
        public PackageStarViewHolder(@NonNull RmHolderStarPackageBinding binding) {
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

