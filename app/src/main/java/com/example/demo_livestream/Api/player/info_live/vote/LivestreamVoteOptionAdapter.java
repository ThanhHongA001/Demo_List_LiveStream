package com.viettel.mocha.rmlivestream.player.info_live.vote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.mocha.app.R;
import com.viettel.mocha.app.databinding.RmHolderLiveVoteOptionBinding;
import com.viettel.mocha.rmlivestream.model.LivestreamVoteOptionModel;

import java.util.ArrayList;

public class LivestreamVoteOptionAdapter extends RecyclerView.Adapter<LivestreamVoteOptionAdapter.LivestreamVoteOptionViewHolder> {
    private Context context;
    private ArrayList<LivestreamVoteOptionModel> list;
    private OnClickVoteOptionListener listener;

    public LivestreamVoteOptionAdapter(Context context) {
        this.context = context;
    }

    public void setList(ArrayList<LivestreamVoteOptionModel> list) {
        this.list = list;
    }

    public void setListener(OnClickVoteOptionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LivestreamVoteOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LivestreamVoteOptionViewHolder(RmHolderLiveVoteOptionBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull LivestreamVoteOptionViewHolder holder, int position) {
        LivestreamVoteOptionModel item = list.get(position);
        holder.binding.tvVoteOptionName.setText(item.getTitle());
        if (item.isSelect()) {
            holder.binding.icRadio.setImageResource(R.drawable.rm_ic_radio_select);
            holder.binding.root.setStroke(ContextCompat.getColor(context, R.color.rm_color_FF70), 1);
            holder.binding.root.setBackgroundColorRound(ContextCompat.getColor(context, R.color.color_FF70_10));
        } else {
            holder.binding.icRadio.setImageResource(R.drawable.rm_ic_radio_default);
            holder.binding.root.setStroke(ContextCompat.getColor(context, R.color.color_3638), 1);
            holder.binding.root.setBackgroundColorRound(ContextCompat.getColor(context, R.color.color_3638));
        }
        holder.binding.root.setOnClickListener(view -> {
            for (LivestreamVoteOptionModel model : list) {
                model.setSelect(false);
            }
            item.setSelect(true);
            listener.onClickVoteOption(item);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class LivestreamVoteOptionViewHolder extends RecyclerView.ViewHolder {

        public final RmHolderLiveVoteOptionBinding binding;
        public LivestreamVoteOptionViewHolder(@NonNull RmHolderLiveVoteOptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnClickVoteOptionListener {
        void onClickVoteOption(LivestreamVoteOptionModel option);
    }
}
