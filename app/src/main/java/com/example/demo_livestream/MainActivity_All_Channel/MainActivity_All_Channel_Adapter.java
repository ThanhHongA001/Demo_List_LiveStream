package com.example.demo_livestream.MainActivity_All_Channel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_All_Channel_Adapter extends RecyclerView.Adapter<MainActivity_All_Channel_Adapter.ViewHolder> {

    private List<MainActivity_All_Channel_Model> channelList;

    // Constructor
    public MainActivity_All_Channel_Adapter(List<MainActivity_All_Channel_Model> channelList) {
        this.channelList = channelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rm_activity_item11, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_All_Channel_Model model = channelList.get(position);

        // Gán dữ liệu vào các thành phần giao diện
        holder.imgAvatar.setImageResource(model.getAvatarResId());
        holder.tvName.setText(model.getName());
        holder.tvFollowers.setText(model.getFollowers() + " Followers");
        holder.tvVideos.setText(model.getVideos() + " Videos");
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvName, tvFollowers, tvVideos;
        Button btnFollow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.rm_activity_item11_img_01);
            tvName = itemView.findViewById(R.id.rm_activity_item11_tv_01);
            tvFollowers = itemView.findViewById(R.id.rm_activity_item11_tv_02);
            tvVideos = itemView.findViewById(R.id.rm_activity_item11_tv_04);
            btnFollow = itemView.findViewById(R.id.rm_activity_item11_btn_01);
        }
    }
}
