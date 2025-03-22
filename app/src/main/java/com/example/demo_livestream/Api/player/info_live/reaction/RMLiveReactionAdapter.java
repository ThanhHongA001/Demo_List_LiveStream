package com.viettel.mocha.rmlivestream.player.info_live.reaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.viettel.mocha.app.R;
import com.viettel.mocha.app.databinding.RmHolderLiveReactionBinding;
import com.viettel.mocha.rmlivestream.RMConstants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class RMLiveReactionAdapter extends RecyclerView.Adapter<RMLiveReactionAdapter.RMLiveReactionHolder> {

    private final List<RMConstants.ChatFunction> functions;
    private RMLiveReactionListener listener;

    public void setListener(RMLiveReactionListener listener) {
        this.listener = listener;
    }

    public RMLiveReactionAdapter(ArrayList<RMConstants.ChatFunction> functions) {
        this.functions = functions;
    }

    @NonNull
    @androidx.annotation.NonNull
    @Override
    public RMLiveReactionHolder onCreateViewHolder(@NonNull @androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        return new RMLiveReactionHolder(RmHolderLiveReactionBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    @Override
    public int getItemViewType(int position) {
        return functions.get(position).getFunctionType();
    }

    @Override
    public void onBindViewHolder(@NonNull @androidx.annotation.NonNull RMLiveReactionHolder livestreamFunctionHolder, int position) {
        switch (getItemViewType(position)) {
            case RMConstants.ChatFunction.TYPE_LIKE:
                Glide.with(livestreamFunctionHolder.itemView.getContext()).load(R.drawable.rm_ic_reaction_like).into(livestreamFunctionHolder.binding.imgFunction);
                break;
            case RMConstants.ChatFunction.TYPE_LIKED:
                Glide.with(livestreamFunctionHolder.itemView.getContext()).load(R.drawable.rm_ic_reaction_like).into(livestreamFunctionHolder.binding.imgFunction);
                livestreamFunctionHolder.binding.imgLiked.setVisibility(View.VISIBLE);
                break;
            case RMConstants.ChatFunction.TYPE_DONATE:
                Glide.with(livestreamFunctionHolder.itemView.getContext()).load(R.drawable.rm_ic_star).into(livestreamFunctionHolder.binding.imgFunction);
                 livestreamFunctionHolder.binding.imgLiked.setVisibility(View.VISIBLE);
                break;
            case RMConstants.ChatFunction.TYPE_HEART:
                Glide.with(livestreamFunctionHolder.itemView.getContext()).load(R.drawable.rm_ic_reaction_heart).into(livestreamFunctionHolder.binding.imgFunction);
                break;
            case RMConstants.ChatFunction.TYPE_WOW:
                Glide.with(livestreamFunctionHolder.itemView.getContext()).load(R.drawable.rm_ic_reaction_wow).into(livestreamFunctionHolder.binding.imgFunction);
                break;
            case RMConstants.ChatFunction.TYPE_HAHA:
                Glide.with(livestreamFunctionHolder.itemView.getContext()).load(R.drawable.rm_ic_reaction_haha).into(livestreamFunctionHolder.binding.imgFunction);
                break;
            case RMConstants.ChatFunction.TYPE_SAD:
                Glide.with(livestreamFunctionHolder.itemView.getContext()).load(R.drawable.rm_ic_reaction_sad).into(livestreamFunctionHolder.binding.imgFunction);
                break;
            case RMConstants.ChatFunction.TYPE_ANGRY:
                Glide.with(livestreamFunctionHolder.itemView.getContext()).load(R.drawable.rm_ic_reaction_angry).into(livestreamFunctionHolder.binding.imgFunction);
                break;
        }

        if (functions.get(position).isChosen()) {
            livestreamFunctionHolder.binding.imgLiked.setVisibility(View.VISIBLE);
        } else {
            livestreamFunctionHolder.binding.imgLiked.setVisibility(View.GONE);
        }
        livestreamFunctionHolder.itemView.setOnClickListener(v -> listener.onFunctionClick(getItemViewType(position)));
    }

    @Override
    public int getItemCount() {
        return functions == null ? 0 : functions.size();
    }
    
    
    public class RMLiveReactionHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView imgFunction, imgLiked;
        RmHolderLiveReactionBinding binding;

        public RMLiveReactionHolder(RmHolderLiveReactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        
    }
}
