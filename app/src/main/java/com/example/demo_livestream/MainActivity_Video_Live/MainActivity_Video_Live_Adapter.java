package com.example.demo_livestream.MainActivity_Video_Live;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demo_livestream.R;
import java.util.List;

public class MainActivity_Video_Live_Adapter extends RecyclerView.Adapter<MainActivity_Video_Live_Adapter.ViewHolder> {
    private List<MainActivity_Video_Live_Model> itemList;

    public MainActivity_Video_Live_Adapter(List<MainActivity_Video_Live_Model> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rm_activity_item10, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_Video_Live_Model item = itemList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvUsername.setText(item.getUsername());
        holder.tvViews.setText(item.getViews() + " Views");
        holder.tvTime.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvUsername, tvViews, tvTime;
        ImageView imgThumbnail, imgLiveIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.rm_activity_item10_tv_01);
            tvUsername = itemView.findViewById(R.id.rm_activity_item10_tv_02);
            tvViews = itemView.findViewById(R.id.rm_activity_item10_tv_03);
            tvTime = itemView.findViewById(R.id.rm_activity_item10_tv_05);
            imgThumbnail = itemView.findViewById(R.id.rm_activity_item10_img_01);
            imgLiveIcon = itemView.findViewById(R.id.rm_activity_item10_img_02);
        }
    }
}
